package com.wasabi_neko.nyanVenture.controller;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.wasabi_neko.nyanVenture.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainMenu implements Initializable {
    @FXML
    Pane rootPane;
    @FXML
    Button btPlay;
    @FXML
    Button btChara;
    @FXML
    Button btSetting;
    @FXML
    Button btExit;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        System.out.println("test");
    }

    @FXML
    private void onPlayPressed() throws IOException {
        // TODO: add loading
        App.sceneController.changeScene("gamePlay");
    }

    @FXML
    private void onExitPressed() throws IOException {
        App.sceneController.mainStage.close();
    }
}