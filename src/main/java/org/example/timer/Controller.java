package org.example.timer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller {
    /*
    User settings
     */
    private final double minutes = 0;
    private final double seconds = 0;
    private final boolean isWinter = true;

    /*
    Global variables
     */
    private final double minute = 0;
    private final double second = 0;
    private final double firstInterval = 0;
    private final double secondInterval = 0;
    private final double flash = 0;

    @FXML
    private ImageView bombImage;

    @FXML
    private Label l;

    @FXML
    private TextField tf;

    @FXML
    protected void onHelloButtonClick() {

    }

    public void initialize(){
        bombImage.setOnMouseDragged(event -> {
            bombImage.setLayoutX(event.getSceneX() - bombImage.getFitWidth() / 3);
            bombImage.setLayoutY(event.getSceneY() - bombImage.getFitHeight() / 3);
        });
//        if (isWinter){
//
//        }
    }
    public void startTimer(){

    }
    public void decreaseTimer(){

    }
}
