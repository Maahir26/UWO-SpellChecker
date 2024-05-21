package com.example.spellcheckeruwo;


import SpellcheckerClasses.Metrics;
import SpellcheckerClasses.SpellChecker;
import SpellcheckerClasses.Document;
import SpellcheckerClasses.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for managing the document screen in the Spellchecker application.
 * This class handles the interaction between the user interface and the spellchecker functionality.
 */
public class DocumentScreenController {
    @FXML
    private Label numberOfCharacters, numberOfLines, numberOfMisspelledWords, numberOfDoubleWords, numberOfWordsFixedByManual, numberOfWordsFixedByMisspelling, numberOfWordsDeleted;
    @FXML
    private TextArea spellingMistakeTextArea;
    @FXML

    private TextArea documentTextArea, suggestionsOne, suggestionsTwo, suggestionsThree, manualCorrection;
    
    @FXML
    private TabPane docTabs;
    @FXML
    private MenuBar docMenubar;
    SingleSelectionModel<Tab> selectionModel;
    
    private double currentFontSize = 12; // Default font size, adjust as needed
    private Text currentWord;
    private SpellChecker spellChecker;
    private int currentIndex = 0;
    
    @FXML
    public void initialize(){
        selectionModel = docTabs.getSelectionModel();
    }
    /**
     * Initializes the controller with a spell checker instance.
     * This method is used to set up the controller with necessary data.
     *
     * @param spellChecker The SpellChecker instance to be used by this controller.
     */
    public void initData(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
        List<Document> documents = spellChecker.getDocuments();
        populateDocumentTabs(documents);

    }

    private Document currentDocument;

    /**
     * Sets the current document and updates the text area with its content.
     *
     * @param doc The Document to be set as the current document.
     */
    public void setDocument(Document doc) {
        currentDocument = doc;
        documentTextArea.setText("");
        documentTextArea.setText(doc.toStringFile());
        currentDocument.ToStringDecompiled();

        for (Tab tab : docTabs.getTabs()) {
            if (tab.getText().equals(doc.getName())) {
                selectionModel.select(tab);
                break;
            }
        }

        updateDocMetrics();
    }

    /**
     * Calculates and updates the document metrics based on the current text in the document text area.
     */
    public void updateDocMetrics() {
        Metrics metrics = currentDocument.metrics;
        setNumberOfCharacters(metrics.getNumberOfCharacters());
        setNumberOfLines(metrics.getNumberOfLines());
        setNumberOfMisspelledWords(metrics.getNumberOfMisspelledWords());
        setNumberOfDoubleWords(metrics.getNumberOfDoubleWords());
        setNumberOfWordsFixedByManual(metrics.getNumberOfWordsFixedByManual());
        setNumberOfWordsFixedByMisspelling(metrics.getNumberOfWordsFixedByMisspelling());
        setNumberOfWordsDeleted(metrics.getNumberOfWordsDeleted());
    }
    
    /**
     * Resets the document metrics based on the current text in the document text area.
     */
    public void clearMetrics() {
        setNumberOfCharacters(0);
        setNumberOfLines(0);
        setNumberOfMisspelledWords(0);
        setNumberOfDoubleWords(0);
        setNumberOfWordsFixedByManual(0);
        setNumberOfWordsFixedByMisspelling(0);
        setNumberOfWordsDeleted(0);
    }

    public void setNumberOfCharacters(int n) {
        numberOfCharacters.setText(String.valueOf(n));
    }

    public void setNumberOfLines(int n) {
        numberOfLines.setText(String.valueOf(n));
    }

    public void setNumberOfMisspelledWords(int n) {
        numberOfMisspelledWords.setText(String.valueOf(n));
    }

    public void setNumberOfDoubleWords(int n) {
        numberOfDoubleWords.setText(String.valueOf(n));
    }

    public void setNumberOfWordsFixedByManual(int n) {
        numberOfWordsFixedByManual.setText(String.valueOf(n));
    }

    public void setNumberOfWordsFixedByMisspelling(int n) {
        numberOfWordsFixedByMisspelling.setText(String.valueOf(n));
    }

    public void setNumberOfWordsDeleted(int n) {
        numberOfWordsDeleted.setText(String.valueOf(n));
    }

    /**
     * Handles the action of opening a document. This includes file selection and loading the document content.
     */
    @FXML
    private void handleOpenDocument() {
        try {
            Stage fileChooserStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            if (currentDocument != null) {
                fileChooser.setInitialDirectory(new File(currentDocument.getPath()));
                fileChooser.setInitialFileName(currentDocument.getName());
            } else {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            }
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text file", "*.txt")
            );
            File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
            if (selectedFile == null) {
                return;
            }

            Document selectedDoc = new Document(selectedFile.getName(), selectedFile.getParent() + "/");
            addToSpellChecker(selectedDoc);
            addTabItem(selectedDoc);

            setDocument(selectedDoc);
            if (!currentDocument.getMispelledWords().isEmpty()) {
                updateTextAreaWithCurrentWord();
            }

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of saving the current document.
     */
    @FXML
    private void handleSaveDocument() {
        try {
            if (currentDocument != null) {
                currentDocument.saveFile(currentDocument.toStringFile());
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("Oops");
                errorAlert.setHeaderText("Oops. No files are open right now.");
                errorAlert.setContentText("Please open a file.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the action of saving the current document to a new location.
     */
    @FXML
    private void handleSaveAsDocument() {
        try {
            if (currentDocument != null) {
                Stage fileChooserStage = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File to...");
                fileChooser.setInitialDirectory(new File(currentDocument.getPath()));
                fileChooser.setInitialFileName(currentDocument.getName());
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text file", "*.txt")
                );
                File selectedFile = fileChooser.showSaveDialog(fileChooserStage);
                if (selectedFile == null) {
                    return;
                }
                
                for (Tab tab : docTabs.getTabs()) {
                    if (tab.getText().equals(currentDocument.getName())) {
                        tab.setText(selectedFile.getName());
                        break;
                    }
                }
                currentDocument.setName(selectedFile.getName());
                currentDocument.setPath(selectedFile.getParent() + "/");
                currentDocument.saveFile(currentDocument.toStringFile());
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("Oops");
                errorAlert.setHeaderText("Oops. No files are open right now.");
                errorAlert.setContentText("Please open a file.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the action of deleting the current document from the application.
     */
    @FXML
    private void handleDeleteDocument() {
        try {
            //docTabs.getTabs().
            docTabs.getTabs().removeIf(tab -> tab.getText().equals(currentDocument.getName()));
            spellChecker.deleteDocument(currentDocument);

            if (!selectionModel.isEmpty()) {
                selectionModel.selectPrevious();
            } else {
                currentDocument = null;
                documentTextArea.setText("");
                documentTextArea.setVisible(false);
            }

            clearMetrics();

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of reverting changes. This functionality is yet to be implemented.
     */
    @FXML
    private void handleRevertDocument() {
        try {
            if (currentDocument != null) {
                currentDocument.undo();
                documentTextArea.setText(currentDocument.toStringFile());
                spellChecker.reconfigureSpellingMistakes();
                updateTextAreaWithCurrentWord();
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("Oops");
                errorAlert.setHeaderText("Oops. No files are open right now.");
                errorAlert.setContentText("Please open a file.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setAlertType(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
        clearMetrics();
        updateDocMetrics();
    }

    /**
     * Increases the font size of the text in the document text area.
     */
    @FXML
    public void handleZoomIn() {
        currentFontSize += 1; // Increase font size by 1 each time
        documentTextArea.setStyle("-fx-font-size: " + currentFontSize + "pt;");
    }

    /**
     * Decreases the font size of the text in the document text area.
     */
    @FXML
    public void handleZoomOut() {
        currentFontSize -= 1; // Decrease font size by 1 each time
        if (currentFontSize < 1) {
            currentFontSize = 1; // Prevent font size from becoming too small
        }
        documentTextArea.setStyle("-fx-font-size: " + currentFontSize + "pt;");
    }

    /**
     * Displays a help dialog to assist the user with using the document viewer application.
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
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setAlertType(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles opening the options screen for additional settings.
     */
    @FXML
    private void handleOpenOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsScreen.fxml"));
            Parent root = loader.load();

            OptionsScreenController optionsController = loader.getController();
            optionsController.loadDictionary(spellChecker.user.getPrivateDictionary());


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

    /**
     * Ignores the current word under consideration.
     */
    @FXML
    public void handleIgnoreWord() {
        if (currentWord != null) {
            currentDocument.corrections.ignoreWord(currentWord);
            spellChecker.reconfigureSpellingMistakes();
            handleCorrection();
            updateDocMetrics();
        }
    }

    /**
     * Ignores all instances of the current word under consideration.
     */
    @FXML
    public void handleIgnoreAllInstanceWord() {
        if (currentWord != null) {
            currentDocument.corrections.ignoreRestOfApplication(currentWord);
            spellChecker.reconfigureSpellingMistakes();
            handleCorrection();
            updateDocMetrics();
        }
    }

    /**
     * Adds the current word to the user's private dictionary and updates the document.
     */
    @FXML
    public void handleAddToDict() {
        if (currentWord != null) {
            spellChecker.user.getPrivateDictionary().add(currentWord.getText());

            currentDocument.corrections.manuallyCorrect(currentWord.getText(), currentWord);
            spellChecker.reconfigureSpellingMistakes();

            //Move to next word
            handleCorrection();
            updateDocMetrics();
        }
    }

    /**
     * Deletes the current word from the document.
     */
    @FXML
    public void handleDeleteWord() {
        if (currentWord != null) {
            String wordToDel = currentWord.getText();
            int pos = currentWord.getOrder() - 1;
            currentDocument.corrections.deleteWord(currentWord);
            spellChecker.reconfigureSpellingMistakes();
            setDocument(currentDocument);
            currentDocument.modifyText(pos, wordToDel, "DELETE");
            currentDocument.metrics.incrementNumberOfWordsDeleted();
            handleCorrection();
            updateDocMetrics();
        }
    }

    /**
     * Manually corrects the current word in the document.
     */
    @FXML
    public void handleManualCorr() {
        if (currentWord != null && currentWord.getText() != "") {
            String textFromMan = manualCorrection.getText();

            int pos = currentWord.getOrder();

            currentDocument.corrections.manuallyCorrect(textFromMan, currentWord);
            spellChecker.reconfigureSpellingMistakes();
            setDocument(currentDocument);
            currentDocument.modifyText(pos, textFromMan, "MOD");
            currentDocument.metrics.incrementNumberOfWordsFixedByManual();
            handleCorrection();
            updateTextAreaWithCurrentWord();
            updateDocMetrics();
        }
    }

    @FXML
    private void handleClickSuggestionsOne() {
        if (currentWord != null) {
            String text = suggestionsOne.getText();
            int pos = currentWord.getOrder() - 1;
            currentDocument.modifyText(pos, currentWord.getText(), "MOD");
            currentDocument.corrections.manuallyCorrect(text, currentWord);
            spellChecker.reconfigureSpellingMistakes();
            setDocument(currentDocument);
            currentDocument.metrics.incrementNumberOfWordsFixedByMisspelling();
            handleCorrection();
            updateTextAreaWithCurrentWord();
            updateDocMetrics();
        }
    }

    @FXML
    private void handleClickSuggestionsTwo() {
        if (currentWord != null) {
            String text = suggestionsTwo.getText();
            int pos = currentWord.getOrder() - 1;
            currentDocument.modifyText(pos, currentWord.getText(), "MOD");
            currentDocument.corrections.manuallyCorrect(text, currentWord);
            spellChecker.reconfigureSpellingMistakes();
            setDocument(currentDocument);
            currentDocument.metrics.incrementNumberOfWordsFixedByMisspelling();
            handleCorrection();
            updateTextAreaWithCurrentWord();
            updateDocMetrics();
        }
    }

    @FXML
    private void handleClickSuggestionsThree() {
        if (currentWord != null) {
            String text = suggestionsThree.getText();
            int pos = currentWord.getOrder() - 1;
            currentDocument.modifyText(pos, currentWord.getText(), "MOD");
            currentDocument.corrections.manuallyCorrect(text, currentWord);
            spellChecker.reconfigureSpellingMistakes();
            setDocument(currentDocument);
            currentDocument.metrics.incrementNumberOfWordsFixedByMisspelling();
            handleCorrection();
            updateTextAreaWithCurrentWord();
            updateDocMetrics();
        }
    }

    public void handleCorrection() {
        //Move to next word
        if (currentIndex < currentDocument.getMispelledWords().size() - 1) {
            handleNext();
        } else if (currentIndex > 0) {
            handlePrevious();
        } else {
            setCurrentWord(null);
            suggestionsOne.setText("");
            suggestionsTwo.setText("");
            suggestionsThree.setText("");
            spellingMistakeTextArea.setText("");
        }
    }

    /**
     * Populates the document menu with available documents.
     *
     * @param documents A list of documents to add to the menu.
     */
    public void populateDocumentTabs(List<Document> documents) {
        for (Document doc : documents) {
            addTabItem(doc);
        }
    }

    /**
     * Loads a document's content into the text area.
     *
     * @param document The document to load.
     */
    public void loadDocumentIntoTextArea(Document document) {
        String content = document.toStringFile();
        //Here
        setDocument(document);  // If you need to set the current document
    }

    /**
     * Adds a new document to the document tabs.
     *
     * @param doc The document to be added.
     */
    public void addTabItem(Document doc) {
        Tab tabItem = new Tab(doc.getName());
        tabItem.setOnSelectionChanged(event -> {
            if (tabItem.isSelected()) loadDocumentIntoTextArea(doc);
        });
        tabItem.setOnCloseRequest(event -> {
            if (currentDocument.getName().equals(tabItem.getText())) {
                selectionModel.selectPrevious();
            }
            spellChecker.deleteDocument(spellChecker.getDocumentByName(tabItem.getText()));
        });
        tabItem.setOnClosed(event -> {
            if (selectionModel.isEmpty()) {
                currentDocument = null;
                documentTextArea.setText("");
                documentTextArea.setVisible(false);
            }
        });
        docTabs.getTabs().add(tabItem);
    }

    public void addToSpellChecker(Document document) {
        spellChecker.addDocument(document);
    }

    /**
     * Navigates to the previous misspelled word in the document.
     */
    @FXML
    private void handlePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
        }
        updateTextAreaWithCurrentWord();
    }

    /**
     * Navigates to the next misspelled word in the document.
     */
    @FXML
    private void handleNext() {
        if (currentIndex < currentDocument.getMispelledWords().size() - 1) {
            currentIndex++;
        }
        updateTextAreaWithCurrentWord();
    }

    /**
     * Sets the current word under consideration.
     *
     * @param word The text object representing the current word.
     */
    private void setCurrentWord(Text word) {
        currentWord = word;
    }

    private void updateTextAreaWithCurrentWord() {
        documentTextArea.setVisible(true);
        if(currentDocument.getMispelledWords().size() > 0){
            Text currentWord = currentDocument.getMispelledWords().get(currentIndex);
            setCurrentWord(currentWord);
            spellingMistakeTextArea.setText(currentWord.getText());

            List<String> textSuggestions = spellChecker.suggestions.getSuggestions(currentWord.getText());
            suggestionsOne.setText(textSuggestions.get(0));
            suggestionsTwo.setText(textSuggestions.get(1));
            suggestionsThree.setText(textSuggestions.get(2));
        }
    }

    /**
     * Handles the action of closing the options screen and returning to the dashboard.
     */
    @FXML
    public void backToDashHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
            Parent root = loader.load();

            // Get the controller for the new FXML file
            HomeController homeController = loader.getController();
            homeController.initData(spellChecker);

            // Initialize and show the new stage
            Stage stage = new Stage();
            stage.setTitle("UWO SpellChecker");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            
            ((Stage) docMenubar.getScene().getWindow()).close();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("We have encountered an error:");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Handles the transition from the loading screen to the main screen of the application.
     */
//    @FXML
//    private void handleOpenMain() {
//        Platform.runLater(() -> {
//            try {
//                // Load the FXML file and get the root
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
//                Parent root = loader.load();
//
//                // Get the controller for the new FXML file
//                HomeController homeController = loader.getController();
//                homeController.initData(spellChecker);
//
//                // Initialize and show the new stage
//                Stage stage = new Stage();
//                stage.setTitle("UWO SpellChecker");
//                stage.setScene(new Scene(root, 600, 400));
//                stage.show();
//
//                // Close the current stage, assuming 'currentStage' is a field in this controller
//                if (currentStage != null) {
//                    currentStage.close();
//                }
//                if (mainApp != null) {mainApp.closeRequest(stage);}
//            } catch (Exception e) {
//                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                errorAlert.setTitle("Error");
//                errorAlert.setHeaderText("We have encountered an error:");
//                errorAlert.setContentText(e.getMessage());
//                errorAlert.showAndWait();
//                e.printStackTrace();
//            }
//        });
//    }


}
