package org.example.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    int initialSeconds = 5;
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
    private Group bombGroup;

    @FXML
    private Label timerLabel;

    @FXML
    private ImageView bombImage;

    @FXML
    private ImageView bombImageChristmas;

    @FXML
    private ImageView explosion;

    public void initialize() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/org/example/timer/style/font/digital_7_mono.ttf"), 42);
        if (customFont != null) {
            timerLabel.setFont(customFont);
        }else
            System.out.println("Font is not found!");

        minutes = initialMinutes;
        seconds = initialSeconds;

        bombImage.setVisible(!isWinter);
        bombImageChristmas.setVisible(isWinter);

        explosion.setVisible(false);

        updateTimer();
        dragElement();
    }
    public void dragElement(){
        Delta dragDelta = new Delta();

        bombGroup.setOnMousePressed(event -> {
            dragDelta.x = event.getX();
            dragDelta.y = event.getY();
        });
        bombGroup.setOnMouseDragged(event -> {
            bombGroup.setLayoutX(event.getSceneX() - dragDelta.x);
            bombGroup.setLayoutY(event.getSceneY() - dragDelta.y);
        });
    }

    public void startTimer() {
        this.minutes = initialMinutes;
        this.seconds = initialSeconds;
        flash = 6;

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
            playAudio("doublebeep.wav");
            decrementTimeLine.stop();
            startFlashTimer();
            return;
        }

        if (seconds == 0 && minutes > 0){
            minutes--;
            seconds = 59;
        } else {
            seconds--;
        }
    }

    private void updateTimer() {
        String text = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(text);
    }

    private void bombExplosion() {
        if (bombImage != null) bombImage.setVisible(false);
        if (bombImageChristmas != null) bombImageChristmas.setVisible(false);

        explosion.setImage(null);
        explosion.setImage(new Image(getClass().getResource("/org/example/timer/style/gifs/explosion.gif").toExternalForm()));

        explosion.setVisible(true);
        playAudio("explode.wav");
    }

    private void startFlashTimer(){
        flashTimeLine = new Timeline(new KeyFrame(Duration.millis(50), e -> getFlashTimer()));
        flashTimeLine.setCycleCount(flash * 2);
        flashTimeLine.play();
    }

    private void getFlashTimer(){
        showTimer = !showTimer;
        timerLabel.setVisible(showTimer);
        flash--;

        if (flash <= 0) {
            timerLabel.setVisible(false);
            flashTimeLine.stop();
            bombExplosion();
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
                System.out.println("File is not found: " + fileName);
            } else {
                System.out.println("File is found: " + url.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAudio(String fileName){
        try {
            URL soundUrl = getClass().getResource("/org/example/timer/style/sounds/" + fileName);
            if (soundUrl == null) {
                throw new FileNotFoundException("File is not found: " + fileName);
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

















