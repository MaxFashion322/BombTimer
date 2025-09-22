package org.example.timer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController{
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

    public void initialize(URL location, ResourceBundle resources){
        if (isWinter){

        }
    }

    @FXML
    private Label l;

    @FXML
    private TextField tf;

    @FXML
    protected void onHelloButtonClick() {

    }
}
