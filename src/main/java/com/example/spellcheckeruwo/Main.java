package com.example.spellcheckeruwo;

import SpellcheckerClasses.Admin;
import SpellcheckerClasses.SpellChecker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main class for the SpellChecker application.
 * This class is responsible for initializing and starting the primary stage of the application.
 */
public class Main extends Application {
    private Stage primaryStage;
    private SpellChecker spellChecker;

    /**
     * Starts the primary stage of the application.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception if an error occurs during loading.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage; // Assign the primary stage to your field
        SpellChecker spellCheck = new SpellChecker();
        this.spellChecker = spellCheck;

        // check if previous state exists
        if (prevStateExists()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("previousStateScreen.fxml"));

            // Load the FXML
            Parent root = loader.load();

            // Get the controller and set the primary stage
            PreviousStateScreenController prevStateController = loader.getController();

            prevStateController.setCurrentStage(primaryStage);
            prevStateController.initData(spellCheck);

            prevStateController.setMainApp(this);

            // Set up the primary stage
            primaryStage.setTitle("UWO SpellChecker");
            primaryStage.setScene(new Scene(root, 600, 200));

            primaryStage.show();
        }

        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));

            // Load the FXML
            Parent root = loader.load();

            // Get the controller and set the primary stage
            LoadingScreenController loadController = loader.getController();


            loadController.setCurrentStage(primaryStage);
            loadController.initData(spellCheck);

            loadController.setMainApp(this);

            // Set up the primary stage
            primaryStage.setTitle("UWO SpellChecker");
            primaryStage.setScene(new Scene(root, 600, 400));

            primaryStage.show();
        }

    }

    /**
     * Sets up a close request handler for the given stage.
     * This handler will open the exit confirmation screen when a close request is triggered.
     *
     * @param stage The stage for which the close request handler is to be set.
     */
    public void closeRequest(Stage stage) {
        stage.setOnCloseRequest(event -> {
            event.consume();
            handleOpenExitConf(stage);
        });
    }

    /**
     * Handles opening the exit confirmation screen.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void handleOpenExitConf(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("exitConfirmationScreen.fxml"));
            Parent root = loader.load();

            ExitConfirmationController exitConfirmationController = loader.getController();
            exitConfirmationController.initData(spellChecker);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new Scene(root, 600, 200));
            stage.setTitle("Exit Screen");
            stage.showAndWait();

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    private boolean prevStateExists() {
        PreviousStateScreenController checkCont = new PreviousStateScreenController();
        return checkCont.checkPrevState();
    }
    
    /**
     * The main method that launches the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
