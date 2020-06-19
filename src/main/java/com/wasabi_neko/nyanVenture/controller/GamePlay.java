package com.wasabi_neko.nyanVenture.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.gameObject.BaseNode;
import com.wasabi_neko.nyanVenture.gameObject.Comparators;
import com.wasabi_neko.nyanVenture.gameObject.GameManager;
import com.wasabi_neko.nyanVenture.gameObject.Score;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;
import com.wasabi_neko.nyanVenture.tool.FileManager;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePlay implements Initializable {
    @FXML
    Pane rootPane;
    // GamePlay
    @FXML
    Pane gamePlayPane;
    @FXML
    Pane bgPane;
    @FXML
    Pane roadPane;
    // -- player
    @FXML
    Pane playerPane;
    @FXML
    Pane charaPane;
    @FXML
    Pane effectPane;
    // -- node
    @FXML
    Pane holdPane;
    @FXML
    Pane tapPane;
    @FXML
    Pane popoutPane;
    @FXML
    Pane startLowerPane;
    @FXML
    Pane startUpperPane;
    @FXML
    Pane endPane;

    // Pause
    @FXML
    ImageView escButton;
    @FXML
    Pane pausePane;

    // Finish
    @FXML
    Pane finishPane;
    @FXML
    Label perfectLabel;
    @FXML
    Label greatLabel;
    @FXML
    Label badlLabel;
    @FXML
    Label missLabel;
    @FXML
    Label scoreLabel;
    @FXML
    Label accuracyLabel;

    public GameManager gameManager;
    public Timeline updater;

    public KeyCode[] keyCodeArr = { KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT };

    private boolean[] isTapKey;
    private boolean[] isHoldKey;

    public ArrayList<BaseNode> recordArr;
    public int sheetIndex = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.recordArr = new ArrayList<>();
        System.out.println("start");

        this.gameManager = new GameManager();
        this.gameManager.initSheet(this.tapPane, this.holdPane, this.endPane, this.startLowerPane, this.startUpperPane);
        this.gameManager.initPalyer(this.playerPane, this.charaPane, this.effectPane);
        this.gameManager.initPopouts(this.popoutPane);
        this.gameManager.initBG(this.bgPane, this.roadPane);

        // == GameManager ==
        if (this.gameManager.loadGame(this.sheetIndex) == false) {
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
        this.onAgainPressed();
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

        if (key == KeyCode.P) {
            this.onBackPressed();
        }

        if (tapped) {
            this.gameManager.update();
            this.gameManager.tapCheck(this.isTapKey); // TODO: edit tap arr method

            // long tapTime = System.currentTimeMillis() - this.gameManager.startTime;
            // int isLeft = this.isTapKey[2] == true ? 1 : 0;
            // int isRight = isLeft == 0 ? 1 : 0;
            // short[] type = { -1, -1, (short) isLeft, (short) isRight };
            // BaseNode temp = new BaseNode(tapTime - 1500, tapTime, type, (short) 0, false);
            // this.recordArr.add(temp);
            // System.out.println("add");
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
    }

    public void onMouseHoverESC() {
        this.escButton.setOpacity(1);
    }

    public void onMouseExitESC() {
        this.escButton.setOpacity(0.6);
    }

    // -------------------------------------------------------------------------
    // GUI settings
    // -------------------------------------------------------------------------
    public void onEscPressed() {
        this.pauseGame();
    }

    public void onBackPressed() {
        this.pauseGame();
        this.resetGame();
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
        // for (BaseNode node : this.recordArr) {
        //     System.out.println(node);
        // }

        // this.recordArr.sort(Comparators.baseNode_startTime_CMP);
        // SheetData data = new SheetData(this.recordArr, 81000);

        // try {
        //     FileManager.newSheetData(data, 1);
        //     // FileManager.overWriteSheetData(data, 5);
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
        this.updater.pause();
    }
}