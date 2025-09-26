package org.example.timer;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Controller {
    /*
    User settings
     */
    double initialMinutes = 0;
    double initialSeconds = 0;
    boolean isWinter = false;

    /*
    Global variables
     */
    double minutes =  0;
    double seconds = 0;
    double flash = 0;

    Timeline decrementTimeLine;
    Timeline flashTimeLine;

    @FXML
    private Label timerLabel;

    @FXML
    private ImageView bombImage;

    @FXML
    private ImageView bombImageChristmas;

    @FXML
    private ImageView explosion;

    @FXML
    protected void onRedButtonClick() {

    }

    public void initialize() {
        // Later create method dragElement
        if (isWinter){
            bombImage.setVisible(false);
            bombImageChristmas.setOnMouseDragged(event -> {
                bombImageChristmas.setLayoutX(event.getSceneX() - bombImageChristmas.getFitWidth() / 3);
                bombImageChristmas.setLayoutY(event.getSceneY() - bombImageChristmas.getFitHeight() / 3);
                bombImage.isDisable();
            });
        } else{
            bombImageChristmas.setVisible(false);
            bombImage.setOnMouseDragged(event -> {
                bombImage.setLayoutX(event.getSceneX() - bombImage.getFitWidth() / 3);
                bombImage.setLayoutY(event.getSceneY() - bombImage.getFitHeight() / 3);
            });
        }
    }

    public void startTimer() {
        this.minutes = initialMinutes;
        this.seconds = initialSeconds;

    }
    private void decreaseTimer() {
        var text = minutes;
        if (minutes < 10) {

        }
    }

    private void playAudio(String fileName){
        try {
            AudioClip clip = new AudioClip(getClass().getResource("/sounds/" + fileName).toString());
            clip.play();
        } catch (Exception e){
            System.out.println("Error: " + fileName);
        }
    }
}

















