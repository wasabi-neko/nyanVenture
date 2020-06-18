package com.wasabi_neko.nyanVenture.controller;

import com.wasabi_neko.nyanVenture.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class StartMenu  implements Initializable {
    @FXML ImageView title;
    @FXML ImageView chara;
    @FXML ImageView startImg;

    private Timeline titleShowAnima;
    private Timeline titleMoveAnima;
    private Timeline showAllAnima;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initTitleShow();
        this.initTitleMove();
        this.initShowAll();

        this.startImg.setDisable(true);
        this.titleShowAnima.play();
    };

    // -------------------------------------------------------------------------
    // GUI
    // -------------------------------------------------------------------------
    @FXML
    public void onStartPressed() throws IOException {
        App.sceneController.changeScene("mainMenu");
    }

    @FXML
    public void light() {
        if ( !startImg.isDisable()) {
            this.startImg.setOpacity(1);
        }
    }

    @FXML void deem() {
        if (!startImg.isDisable()) {
            this.startImg.setOpacity(0.6);
        }
    }

    // -------------------------------------------------------------------------
    // Init 
    // -------------------------------------------------------------------------

    private void initTitleShow() {
        this.titleShowAnima = new Timeline();

        this.title.setOpacity(0);
        KeyValue kv1 = new KeyValue(this.title.opacityProperty(), 0);
        KeyValue kv2 = new KeyValue(this.title.opacityProperty(), 1);
        KeyFrame kf = new KeyFrame(Duration.millis(3000), kv1, kv2);
        this.titleShowAnima.getKeyFrames().add(kf);

        this.titleShowAnima.setOnFinished( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                titleMoveAnima.play();
            }
        });
    }
    private void initTitleMove() {
        this.titleMoveAnima = new Timeline();

        this.title.setY(200);
        KeyValue kv1 = new KeyValue(this.title.yProperty(), 500);
        KeyValue kv2 = new KeyValue(this.title.yProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(1500), kv1, kv2);
        this.titleMoveAnima.getKeyFrames().add(kf);

        this.titleMoveAnima.setOnFinished( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startImg.setDisable(false);
                showAllAnima.play();
            }
        });
    }
    private void initShowAll() {
        this.showAllAnima = new Timeline();

        this.chara.setOpacity(0);
        this.startImg.setOpacity(0);

        KeyValue kvChara2 = new KeyValue(this.chara.opacityProperty(), 1);
        KeyFrame kfChara = new KeyFrame(Duration.millis(1500), kvChara2);

        KeyValue kvStart2 = new KeyValue(this.startImg.opacityProperty(), 0.6);
        KeyFrame kfStart = new KeyFrame(Duration.millis(1500), kvStart2);
        this.showAllAnima.getKeyFrames().addAll(kfChara, kfStart);
    }
}