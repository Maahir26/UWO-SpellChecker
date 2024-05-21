package com.example.spellcheckeruwo;

import SpellcheckerClasses.Admin;
import SpellcheckerClasses.SpellChecker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for handling the exit confirmation process in the application.
 */
public class ExitConfirmationController {
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    private SpellChecker spellChecker;

    public void initData(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }


    /**
     * Handles the action when the 'Yes' button is clicked.
     * This method will close the current stage and terminate the application.
     */
    @FXML
    public void handleClose() {
        spellChecker.readToPreviousStateFile();
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    /**
     * Handles the action when the 'No' button is clicked.
     * This method will close the current confirmation window only.
     */
    @FXML
    public void handleNoButton() {
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }
}
