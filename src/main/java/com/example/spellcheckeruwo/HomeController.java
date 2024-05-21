package com.example.spellcheckeruwo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import SpellcheckerClasses.SpellChecker;
import SpellcheckerClasses.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.FileChooser;

/**
 * Controller class for the Home screen of the Spellchecker application.
 */
public class HomeController {

    private SpellChecker spellChecker;
    /**
     * Initializes the controller with a SpellChecker instance.
     *
     * @param spellChecker The SpellChecker instance to be used by this controller.
     */
    public void initData(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }
    /**
     * Displays a help dialog providing information about the Document Viewer application.
     */
    @FXML
    private void handleShowHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("helpScreen.fxml"));

            Parent root = loader.load();

            HelpScreenController helpController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Exit Screen");
            stage.show();

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Handles the action of opening a document. It sets up the main stage and document screen.
     *
     * @param ae The ActionEvent triggered by the user action.
     */
    @FXML
    private void handleOpenDocument(ActionEvent ae){
        try{
            Node source = (Node) ae.getSource();
            Stage mainStage = (Stage) source.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("DocumentScreen.fxml"));
            Parent root = loader.load();

            DocumentScreenController documentController = loader.getController();
            documentController.initData(spellChecker);

            mainStage.setTitle("Spell Checker 2000");
            Rectangle2D screenBounds = Screen.getPrimary().getBounds(); // i just need a screen size :sob:
            mainStage.setScene(new Scene(root, screenBounds.getWidth()*0.75, screenBounds.getHeight()*0.75));
            // Close request handling with custom exit confirmation
            mainStage.setOnCloseRequest(event -> {
                FXMLLoader exitConfirmLoader = new FXMLLoader(getClass().getResource("exitConfirmationScreen.fxml"));
                Parent exitConfirmRoot = null;

                try {
                    exitConfirmRoot = exitConfirmLoader.load();
                    ExitConfirmationController exitConfirmationController = exitConfirmLoader.getController();
                    exitConfirmationController.initData(spellChecker);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage exitConfirmStage = new Stage();
                exitConfirmStage.setTitle("UWO SpellChecker");
                exitConfirmStage.setScene(new Scene(exitConfirmRoot, 600, 400));
                exitConfirmStage.show();

                //this line cancel the close request
                event.consume();
            });

            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
//    @FXML
//    private void handleOpenDocument(ActionEvent ae){
//        try{
//            Stage fileChooserStage = new Stage();
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open Resource File");
//            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("Text file", "*.txt"),
//                    new FileChooser.ExtensionFilter("Any file", "*")
//            );
//            File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
//            if(selectedFile == null){
//                return;
//            }
//            Document selectedDoc = new Document(selectedFile.getName(),selectedFile.getParent()+"/");
//
//            // get the stage that triggered this function/event
//            Node source = (Node) ae.getSource();
//            Stage mainStage = (Stage) source.getScene().getWindow();
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("DocumentScreen.fxml"));
//            Parent root = loader.load();
//
//            DocumentScreenController documentController = loader.getController();
////            documentController.setText(fileContent);
//            documentController.initData(spellChecker);
//
//            documentController.setDocument(selectedDoc);
//
//
//            mainStage.setTitle("Spell Checker 2000");
//            Rectangle2D screenBounds = Screen.getPrimary().getBounds(); // i just need a screen size :sob:
//            mainStage.setScene(new Scene(root, screenBounds.getWidth()*0.75, screenBounds.getHeight()*0.75));
//
//            mainStage.setOnCloseRequest(event -> {
//                //thanks https://stackoverflow.com/questions/51811943/javafx-confirmation-before-exit-x-button
//                FXMLLoader exitConfirmLoader = new FXMLLoader(getClass().getResource("exitConfirmationScreen.fxml"));
//                Parent exitConfirmRoot = null;
//                try {
//                    exitConfirmRoot = exitConfirmLoader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                Stage exitConfirmStage = new Stage();
//                exitConfirmStage.setTitle("UWO SpellChecker");
//                exitConfirmStage.setScene(new Scene(exitConfirmRoot, 600, 400));
//                exitConfirmStage.show();
//
//                //this line cancel the close request
//                event.consume();
//            });
//
//            mainStage.show();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    /**
     * Handles the action of opening the options screen.
     */
    @FXML
    private void handleOpenOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsScreen.fxml"));
            Parent root = loader.load();
            
            // OptionsScreenController optionsController = loader.getController();
            // You can use optionsController to initialize values if needed
            
            Stage stage = new Stage();
            stage.setTitle("Options");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
}

