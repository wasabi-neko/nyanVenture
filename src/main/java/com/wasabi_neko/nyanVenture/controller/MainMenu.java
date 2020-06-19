package com.wasabi_neko.nyanVenture.controller;

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
        System.out.println("test");
    }

    @FXML
    private void onPlay1() throws IOException {
        GamePlay.setSheetIndex(1);
        App.sceneController.changeScene("gamePlay");
    }

    public void onPlay2() throws IOException {
        GamePlay.setSheetIndex(2);
        App.sceneController.changeScene("gamePlay");
    }

    public void onRecord() throws IOException {
        GamePlay.setSheetIndex(0);
        App.sceneController.changeScene("gameplay");
    }

    @FXML
    private void onExitPressed() throws IOException {
        App.sceneController.mainStage.close();
    }
}