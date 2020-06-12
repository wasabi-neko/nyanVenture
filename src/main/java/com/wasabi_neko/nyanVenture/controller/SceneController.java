package com.wasabi_neko.nyanVenture.controller;

import javax.print.event.PrintJobListener;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneController {
    public Stage mainStage;
    public Scene currentScene;

    public int winWidth = 600;
    public int winHight = 400;
    public int fullWidth = 1920;
    public int fullHight = 1080;

    public SceneController() {
        this.winWidth = (int) Screen.getPrimary().getVisualBounds().getMaxX();
        this.winHight = (int) Screen.getPrimary().getVisualBounds().getMaxY();
        System.out.println(winWidth + "," + winHight);
    }

    public void startShow() {
        this.mainStage.setTitle("nyanVenture");
        this.changeScene("startMenu");
        // this.changeScene("gamePlay");
        this.mainStage.show();
    }

    public void changeScene(String sceneName) {
        // if (currentScene != null) {
        //     xSize = currentScene.getWidth();
        //     ySize = currentScene.getHeight();
        // }
        Parent root = loadFXML(sceneName);
        
        if (this.currentScene == null) {
            // System.out.println(this.winWidth + "," + this.winHight);
            Scene newScene = new Scene(root);

            this.currentScene = newScene;
            this.mainStage.setScene(currentScene);
            // this.mainStage.setWidth(3000);
            // this.mainStage.setHeight(2000);
        }

        // double scaleFactor = ( winHight / fullHight);
        double scaleFactor = 0.75;
        Scale scale = new Scale(scaleFactor, scaleFactor);
        scale.setPivotX(0);
        scale.setPivotY(0);

        root.getTransforms().setAll(scale);
        // root.setPrefWidth(1920  / scaleFactor);
        // root.setPrefHeight(1080 / scaleFactor);
        
        this.mainStage.getScene().setRoot(root);
        this.mainStage.getScene().getRoot().requestFocus();
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

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio      = initWidth / initHeight;
    
        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
      }
    
    private class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;
    
        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }
    
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth  = scene.getWidth();
            final double newHeight = scene.getHeight();
        
            double scaleFactor =
                newWidth / newHeight > ratio
                    ? newHeight / initHeight
                    : newWidth / initWidth;
        
            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);
        
                contentPane.setPrefWidth (newWidth  / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}