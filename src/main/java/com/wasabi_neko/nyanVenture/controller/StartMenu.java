package com.wasabi_neko.nyanVenture.controller;


import com.wasabi_neko.nyanVenture.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class StartMenu {
    @FXML
    public void onStartPressed() throws IOException {
        App.sceneController.changeScene("mainMenu");
    }
}