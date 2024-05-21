package com.example.spellcheckeruwo;

import SpellcheckerClasses.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Controller class for the Previous State screen of the Spellchecker application.
 * This class manages the decision to restore the previous state or start fresh.
 */
public class PreviousStateScreenController {
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    private Stage currentStage;
    private Main mainApp;
    private SpellChecker spellChecker;

    /**
     * Sets the main application reference.
     *
     * @param mainApp The main application instance.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the current stage of the application.
     *
     * @param stage The current stage.
     */
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    /**
     * Initializes the controller with a SpellChecker instance and loads the global dictionary.
     *
     * @param spellChecker The SpellChecker instance to be used by this controller.
     *
     */
    public void initData(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
        Admin admin = new Admin(true);
        spellChecker.setGlobalDictionary(admin.importWords("/documents/words_alpha.txt"));
    }

    public boolean checkPrevState() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/state/previousState.xml"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("<document n")) {
                   return true;
                }
            }
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Handles the action when the 'Yes' button is clicked.
     * This method should implement restoring the previous state.
     */
    @FXML
    public void handleYes() {
        // TODO: Implement functionality for restoring previous state
        try{
            spellChecker.readFromPreviousStateFile();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
            Parent root = loader.load();

            LoadingScreenController loadController = loader.getController();
            loadController.carryData(spellChecker);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            loadController.setMainApp(mainApp);
            newStage.show();

            if (currentStage != null) {
                currentStage.close();
                loadController.setCurrentStage(newStage);
            }
            if (mainApp != null) {
                mainApp.closeRequest(newStage);
            }

        }catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the 'No' button is clicked.
     * This method proceeds to the loading screen, indicating the start of a new session.
     */
    @FXML
    public void handleNo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
            Parent root = loader.load();

            LoadingScreenController loadController = loader.getController();
            loadController.carryData(spellChecker);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            loadController.setMainApp(mainApp);
            newStage.show();

            if (currentStage != null) {
                currentStage.close();
                loadController.setCurrentStage(newStage);
            }
            if (mainApp != null) {
                mainApp.closeRequest(newStage);
            }
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
}
