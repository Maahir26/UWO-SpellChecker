package com.example.spellcheckeruwo;

import SpellcheckerClasses.PrivateDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for the Options screen of the Spellchecker application.
 * This class manages the user's private dictionary and provides functionality for editing it.
 */
public class OptionsScreenController {
    @FXML
    private Button backToDashButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;
    @FXML
    private ListView<String> wordListView;
    private PrivateDictionary personalDict;
    private String selectedWord;

    /**
     * Loads the user's private dictionary into the ListView for display and editing.
     *
     * @param dict The PrivateDictionary instance containing the user's words.
     */
    public void loadDictionary(PrivateDictionary dict) {
        this.personalDict = dict;
        loadWordsIntoListView();
        wordListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            selectedWord = newVal;
        });
    }

    /**
     * Loads words from the private dictionary into the ListView.
     */
    private void loadWordsIntoListView() {
        List<String> words = personalDict.getAllWords();
        ObservableList<String> items = FXCollections.observableArrayList(words);
        wordListView.setItems(items);
    }

    /**
     * Handles the action of editing a selected word in the private dictionary.
     */
    @FXML
    public void editHandler() {
        TextInputDialog dialog = new TextInputDialog(selectedWord);
        dialog.setTitle("Edit Word");
        dialog.setHeaderText("Editing Word");
        dialog.setContentText("Enter the new edited word: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::updateWord);
    }

    /**
     * Updates the selected word in the private dictionary.
     *
     * @param newWord The new word to replace the selected word.
     */
    private void updateWord(String newWord) {
        if (selectedWord != null && !newWord.equals(selectedWord)) {
            personalDict.removeWord(selectedWord);
            personalDict.add(newWord);

            loadWordsIntoListView();
        }
    }

    /**
     * Handles the action of removing a selected word from the private dictionary.
     */
    @FXML
    public void removeHandler() {
        personalDict.removeWord(selectedWord);
        loadWordsIntoListView();
    }

    /**
     * Handles the action of resetting the private dictionary to its default state.
     */
    @FXML
    public void resetHandler() {
        personalDict.reset();
        loadWordsIntoListView();
    }

    /**
     * Handles the action of closing the options screen and returning to the dashboard.
     */
    @FXML
    public void backToDashHandler() {
        Stage optStage = (Stage) backToDashButton.getScene().getWindow();
        optStage.close();
    }
}
