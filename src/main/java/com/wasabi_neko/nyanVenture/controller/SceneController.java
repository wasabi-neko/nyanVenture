package com.wasabi_neko.nyanVenture.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class SceneController {
    public Stage mainStage;
    public Scene currentScene;

    public void startShow() {
        this.mainStage.setTitle("nyanVenture");
        this.changeScene("startMenu");
        // this.changeScene("gamePlay");   //TODO temp

        
        this.mainStage.show();
    }

    public void changeScene(String sceneName) {
        Scene newScene = new Scene(loadFXML(sceneName), 600, 400);
        this.currentScene = newScene;
        this.currentScene.getRoot().requestFocus();

        // this.mainStage.minWidthProperty().bind(this.currentScene.heightProperty().multiply(2));
        // this.mainStage.minHeightProperty().bind(this.currentScene.widthProperty().divide(2));
        this.mainStage.setScene(currentScene);
    }
    
    public Parent loadFXML(String fileName) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource(fileName+".fxml"));
            return fxmlLoader.load();
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("#ERROR# Cannot find fxml:" + fileName);
            return null;
        }
    }
}