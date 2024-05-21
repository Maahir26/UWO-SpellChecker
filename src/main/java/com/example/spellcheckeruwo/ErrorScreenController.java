package com.example.spellcheckeruwo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorScreenController {

    @FXML
    private Button okButton;
    @FXML
    public void closeScreen() {
        Stage errorScreen = (Stage) okButton.getScene().getWindow();
        errorScreen.close();
    }
}
