package com.wasabi_neko.nyanVenture;

import com.wasabi_neko.nyanVenture.controller.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class App extends Application {
    public static SceneController sceneController;

    public static void main(String args[]) {
        App.sceneController = new SceneController();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sceneController.mainStage = primaryStage;
        sceneController.startShow();
    }

    @Override
    public void stop() {
        System.out.println("end play");
    }
}