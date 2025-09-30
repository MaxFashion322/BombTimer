package org.example.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.text.Font;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.net.URL;

public class Controller {
    /*
    User settings
     */
    int initialMinutes = 0;
    int initialSeconds = 2;
    boolean isWinter = false;

    /*
    Global variables
     */
    int minutes =  0;
    int seconds = 0;
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

        playAudio("armbomb.wav");
        updateTimer();

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
            playAudio("doublebeep.wav");
            decrementTimeLine.stop();
        }

        if (seconds == 0 && minutes > 0){
            minutes--;
            seconds = 59;
        } else if (seconds > 0) {
            seconds--;
        }
        else if (minutes == 0 && seconds == 0){
            bombExplosion();
        }
    }

    private void updateTimer() {
        String text = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(text);
    }

    private void bombExplosion() {
//        explosion.setImage(new Image(getClass().getResource("timer/style/gifs/explosion.gif/").toExternalForm()));

        if (bombImage != null) bombImage.setVisible(false);
        if (bombImageChristmas != null) bombImageChristmas.setVisible(false);

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

    public void testResource(String fileName) {
        try {
            var url = getClass().getResource("/org/example/timer/style/sounds/" + fileName);
            if (url == null) {
                System.out.println("Файл не найден: " + fileName);
            } else {
                System.out.println("Файл найден: " + url.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAudio(String fileName){
        try {
            URL soundUrl = getClass().getResource("/org/example/timer/style/sounds/" + fileName);
            if (soundUrl == null) {
                throw new FileNotFoundException("Файл не найден: " + fileName);
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/org/example/timer/style/sounds/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

















