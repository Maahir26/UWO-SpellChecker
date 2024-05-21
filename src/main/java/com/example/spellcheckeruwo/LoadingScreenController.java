package com.example.spellcheckeruwo;

import SpellcheckerClasses.Admin;
import SpellcheckerClasses.SpellChecker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller class for the loading screen of the Spellchecker application.
 */
public class LoadingScreenController implements Initializable {
    @FXML
    private Label myMainLabel;
    @FXML
    private ProgressBar myProgressBar;
    @FXML
    private ImageView myImageLogo;
    @FXML
    private Label myProgressLabel;

    private Image image;
    private BigDecimal progress = new BigDecimal(String.format("%.2f",0.0));
    private Timeline timeline;

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
     * Initializes the controller with a SpellChecker instance.
     *
     * @param spellChecker The SpellChecker instance to be used by this controller.
     */
    public void initData(SpellChecker spellChecker) {

        this.spellChecker = spellChecker;
        Admin admin = new Admin(true);
        spellChecker.setGlobalDictionary(admin.importWords("/documents/words_alpha.txt"));

    }
    public void carryData(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }
    /**
     * Initializes the controller and its components.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        myProgressBar.setStyle("-fx-accent: #4f2683;");
        initializeImage();
        initializeTimeline();
        timeline.play();

    }
    /**
     * Initializes the image to be displayed on the loading screen.
     */
    private void initializeImage() {

        Image image = new Image(getClass().getResourceAsStream("/images/uwoMustangsLogo.png"));
        myImageLogo.setImage(image);
    }
    /**
     * Initializes the timeline for the progress bar animation.
     */
    private void initializeTimeline() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.025), event -> {
            progress = progress.add(BigDecimal.valueOf(0.01));
            myProgressBar.setProgress(progress.doubleValue());
            myProgressLabel.setText(String.format("%d%%", Math.round(progress.doubleValue() * 100)));

            if (Math.round(progress.doubleValue()) >= 0.5) {
                myProgressLabel.setTextFill(Color.WHITE);
            }
            if (progress.doubleValue() >= 1) {
                timeline.stop();
                handleOpenMain();
            }
        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    /**
     * Handles the transition from the loading screen to the main screen of the application.
     */
    @FXML
    private void handleOpenMain() {
        Platform.runLater(() -> {
            try {
                // Load the FXML file and get the root
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
                Parent root = loader.load();

                // Get the controller for the new FXML file
                HomeController homeController = loader.getController();

                // Call initData() method on the HomeController with the appropriate argument

                homeController.initData(spellChecker);

                // Initialize and show the new stage
                Stage stage = new Stage();
                stage.setTitle("UWO SpellChecker");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();

                // Close the current stage, assuming 'currentStage' is a field in this controller
                if (currentStage != null) {
                    currentStage.close();
                }
                if (mainApp != null) {mainApp.closeRequest(stage);}
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("We have encountered an error:");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        });

    }

}