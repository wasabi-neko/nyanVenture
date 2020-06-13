package com.wasabi_neko.nyanVenture.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.gameObject.GameManager;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePlay implements Initializable {
    @FXML Pane rootPane;
    @FXML Pane gamePlayPane;
    @FXML Pane pausePane;
    @FXML Pane finishPane;
    @FXML Pane holdPane;
    @FXML Pane tapPane;
    @FXML Pane popoutPane;
    @FXML Pane startLowerPane;
    @FXML Pane startUpperPane;
    @FXML Pane endPane;
    @FXML Button btEsc;

    public GameManager gameManager;
    public Timeline updater;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("start");

        this.gameManager = new GameManager(this.tapPane, this.holdPane, this.endPane, this.startLowerPane, this.startUpperPane, this.popoutPane);

        if (this.gameManager.loadGame(1) == false) {
            this.onBackPressed();
        }

        this.updater = new Timeline();
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.setAutoReverse(true);

        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(Setting.FIXUPDATE_RATE), this.onUpdate, kv);
        updater.getKeyFrames().add(kf);
        
        resetGame();
        startGame();
    }

    // -------------------------------------------------------------------------
    // Game Loops and Updater
    // -------------------------------------------------------------------------

    private EventHandler<ActionEvent> onUpdate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            gameManager.update();
        }
    };
    public void onKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        int keyType = -1;

        if (key == KeyCode.RIGHT) {
            keyType = 0;
        } else if (key == KeyCode.LEFT) {
            keyType = 1;
        }

        if (key == KeyCode.UP) {
            keyType = 2;
        } else if (key == KeyCode.DOWN) {
            keyType = 3;
        }

        if (keyType != -1) {
            this.gameManager.update();
            this.gameManager.tapCheck(keyType);
            this.gameManager.getScore().tempPrint();    //TODO: temp
        }
    }

    public void onKeyRelease(KeyEvent event) {
        // 
    }


    // -------------------------------------------------------------------------
    // GUI settings
    // -------------------------------------------------------------------------
    public void onEscPressed() {
        this.pauseGame();
    }

    public void onBackPressed() {
        App.sceneController.changeScene("mainMenu");
    }

    public void onResumePressed() {
        this.startGame();
    }

    public void onAgainPressed() {
        this.resetGame();
    }


    // -------------------------------------------------------------------------
    // Game Controller
    // -------------------------------------------------------------------------
    public void resetGame() {
        gameManager.resetGame();
    }
    public void startGame() {
        System.out.println("start game in gamePlay");

        // enable gameplay pane
        gamePlayPane.setVisible(true);
        gamePlayPane.setDisable(false);
        // diable finish pane
        finishPane.setVisible(false);
        finishPane.setDisable(true);
        // disable pause pane
        pausePane.setVisible(false);
        pausePane.setDisable(true);
        // requestFocus
        gamePlayPane.requestFocus();

        gameManager.startGame();
        this.updater.play();
    }
    public void pauseGame() {
        // enable pause pane
        pausePane.setVisible(true);
        pausePane.setDisable(false);
        pausePane.requestFocus();

        this.updater.pause();
        gameManager.pauseGame();
    }
    public void finishGame() {
        // disable gameplay pane
        gamePlayPane.setVisible(false);
        gamePlayPane.setDisable(true);
        // enable finish pane
        finishPane.setVisible(true);
        finishPane.setDisable(false);

        finishPane.requestFocus();

        gameManager.pauseGame();

        // TODO: save score data
    }
}