package org.example.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javafx.scene.text.Font;

public class Controller {
    /*
    User settings
     */
    int initialMinutes = 0;
    int initialSeconds = 5;
    boolean isWinter = true;

    /*
    Global variables
     */
    int minutes =  0;
    int seconds = 5;
    int flash = 0;
    boolean showTimer = true;

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

//    @FXML
//    protected void onRedButtonClick() {
//
//    }

    public void initialize() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/org/example/timer/style/font/digital_7_mono.ttf"), 42);

        if (customFont != null) {
            timerLabel.setFont(customFont);
        }else
            System.out.println("Font not found!");

        minutes = initialMinutes;
        seconds = initialSeconds;

        bombImage.setVisible(!isWinter);
        bombImageChristmas.setVisible(isWinter);
        explosion.setVisible(false);

        updateTimer();
        dragElement();
    }
    public void dragElement(){
        ImageView bombToDrag = isWinter ? bombImageChristmas : bombImage;

        Delta dragDelta = new Delta();

        bombToDrag.setOnMousePressed(event -> {
            dragDelta.x = event.getX();
            dragDelta.y = event.getY();
        });
        bombToDrag.setOnMouseDragged(event -> {
            bombToDrag.setLayoutX(event.getSceneX() - dragDelta.x);
            bombToDrag.setLayoutY(event.getSceneY() - dragDelta.y);
        });
    }

    public void startTimer() {
        this.minutes = initialMinutes;
        this.seconds = initialSeconds;
        flash = 3;

        updateTimer();
        playAudio("armbomb.wav");


        if(decrementTimeLine != null){
            decrementTimeLine.stop();
        }
        if(flashTimeLine != null){
            flashTimeLine.stop();
        }
        decrementTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> decreaseTimer()));
        decrementTimeLine.setCycleCount(Timeline.INDEFINITE);
        decrementTimeLine.play();
    }

    private void decreaseTimer() {
        updateTimer();
        if (minutes == 0 && seconds <= 10 && seconds > 0) {
            playAudio("beep.wav");
        }
        if (minutes == 0 && seconds == 0){
            setFlashTimer();
            playAudio("double_beep.wav");
            decrementTimeLine.stop();
        }
        
        if (seconds == 0 && minutes > 0){
            minutes--;
            seconds = 59;
        } else if (seconds > 0) {
            seconds--;
        } else if (minutes == 0 && seconds == 0){
            bombExplosion();
        }
    }

    private void playAudio(String fileName){
        try {
            AudioClip clip = new AudioClip("/sounds/");
            clip.play();
        } catch (Exception e){
            System.out.println("Error: " + fileName);
        }
    }

    private void updateTimer() {
        String text = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(text);
    }

    private void bombExplosion(){
        bombImage.setVisible(false);
        bombImageChristmas.setVisible(false);

        explosion.setVisible(true);
        playAudio("explode.wav");
    }

    private void setFlashTimer(){
        flashTimeLine = new Timeline(new KeyFrame(Duration.millis(500), e -> getFlashTimer()));
        flashTimeLine.setCycleCount(flash * 2);
        flashTimeLine.play();
    }

    private void getFlashTimer(){
        showTimer = !showTimer;
        timerLabel.setVisible(showTimer);
        flash--;
        if(flash <= 0){
            timerLabel.setVisible(true);
            if(flashTimeLine != null){flashTimeLine.stop();}
            if(timerLabel != null){timerLabel.setVisible(false);}
        }
    }
    private static class Delta {
        double x;
        double y;
    }
}

















