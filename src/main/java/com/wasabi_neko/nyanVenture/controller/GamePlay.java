package com.wasabi_neko.nyanVenture.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.gameObject.GameManager;
import com.wasabi_neko.nyanVenture.gameObject.Score;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePlay implements Initializable {
    @FXML Pane rootPane;
    // GamePlay
    @FXML Pane gamePlayPane;
    @FXML Pane playerPane;
    @FXML Pane charaPane;
    @FXML Pane effectPane;
    @FXML Pane holdPane;
    @FXML Pane tapPane;
    @FXML Pane popoutPane;
    @FXML Pane startLowerPane;
    @FXML Pane startUpperPane;
    @FXML Pane endPane;
    @FXML Button btEsc;
    
    // Pause
    @FXML Pane pausePane;

    // Finish
    @FXML Pane finishPane;
    @FXML Label perfectLabel;
    @FXML Label greatLabel;
    @FXML Label badlLabel;
    @FXML Label missLabel;
    @FXML Label scoreLabel;
    @FXML Label accuracyLabel;

    public GameManager gameManager;
    public Timeline updater;

    public KeyCode[] keyCodeArr = { KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT };

    private boolean[] isTapKey;
    private boolean[] isHoldKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("start");

        this.gameManager = new GameManager(this.tapPane, this.holdPane, this.endPane, this.startLowerPane, this.startUpperPane, this.popoutPane, this.playerPane, this.charaPane, this.effectPane);

        // == GameManager ==
        if (this.gameManager.loadGame(1) == false) {
            this.onBackPressed();
        }

        this.updater = new Timeline();
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.setAutoReverse(true);

        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(Setting.FIXUPDATE_RATE), this.onUpdate, kv);
        updater.getKeyFrames().add(kf);
        // == End GameManger ==
        // == Menbers ==
        isHoldKey = new boolean[4];
        Arrays.fill(isHoldKey, false);

        isTapKey = new boolean[4];
        Arrays.fill(isTapKey, false);
        // == End Menbers ==

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
            if (gameManager.isFisnished()) {
                finishGame();
            }
        }
    };

    public void onKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        boolean tapped = false;

        // tap but not hold
        for (int i = 0; i < 4; i++) {
            if (key == keyCodeArr[i] && !isHoldKey[i]) {
                this.isTapKey[i] = true;
                this.isHoldKey[i] = true;
                tapped = true;
            }
        }

        // System.out.println("tap:" + Arrays.toString(this.isTapKey));
        // System.out.println("hold:" + Arrays.toString(isHoldKey));

        if (tapped) {
            this.gameManager.update();
            this.gameManager.tapCheck(this.isTapKey); // TODO: edit tap arr method
            // this.gameManager.getScore().tempPrint(); //TODO: temp
        }

        // reset isTap
        for (int i = 0; i < 4; i++) {
            this.isTapKey[i] = false;
        }
    }

    public void onKeyRelease(KeyEvent event) {
        KeyCode key = event.getCode();

        for (int i = 0; i < 4; i++) {
            if (key == this.keyCodeArr[i]) {
                this.isHoldKey[i] = false;
                this.isTapKey[i] = false;
            }
        }
        // System.out.println("tap:" + Arrays.toString(this.isTapKey));
        // System.out.println("hold:" + Arrays.toString(isHoldKey));
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
        this.startGame();
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
        // request focus;
        finishPane.requestFocus();

        gameManager.pauseGame();

        Score score = this.gameManager.score;
        this.perfectLabel.setText("Perfect: "+score.perfectTimes);
        this.greatLabel.setText("Great: "+score.greatTimes);
        this.badlLabel.setText("Bad: "+score.badTimes);
        this.missLabel.setText("Miss: "+score.missTimes);


        // TODO: save score data
    }
}