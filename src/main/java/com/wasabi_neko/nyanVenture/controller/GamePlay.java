package com.wasabi_neko.nyanVenture.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.event.PrintJobListener;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.gameObject.BaseNode;
import com.wasabi_neko.nyanVenture.gameObject.Player;
import com.wasabi_neko.nyanVenture.gameObject.Score;
import com.wasabi_neko.nyanVenture.gameObject.Sheet;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;
import com.wasabi_neko.nyanVenture.gameObject.TapNode;
import com.wasabi_neko.nyanVenture.tool.FileManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePlay implements Initializable {
    public long startTime = 0;

    public Sheet  sheet;
    public Player player;
    public Score score;

    @FXML Pane rootPane;
    @FXML Pane gamePlayPane;
    @FXML Pane pausePane;
    @FXML Pane finishPane;
    @FXML Pane holdPane;
    @FXML Pane tapPane;
    @FXML Pane popoutPane;
    @FXML Pane missPane;
    @FXML Pane startLowerPane;
    @FXML Pane startUpperPane;
    @FXML Pane endPane;
    @FXML Button btEsc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("start");

        this.startTime = System.currentTimeMillis();
        this.player = new Player(this.tapPane, this.holdPane, startTime);
        this.score = new Score();
        this.sheet = new Sheet(this.tapPane, this.holdPane, this.startUpperPane, this.startLowerPane, this.endPane, this.score, startTime);

        try {
            SheetData data = FileManager.getSheetData(0);
            sheet.sheetData = data;
        } catch (Exception e) {
            System.out.println(e);  //TODO: change
            this.onBackPressed();
        }

        resetGame();
        startGame();
    }

    public void onEscPressed() {
        this.pauseGame();
    }

    public void onBackPressed() {
        this.sheet.stop();
        App.sceneController.changeScene("mainMenu");
    }

    public void onAgainPressed() {
        resetGame();
    }

    public void resetGame() {
        // this.sheet.stop();
        this.startTime = System.currentTimeMillis();
    }
    public void startGame() {
        gamePlayPane.setVisible(true);
        gamePlayPane.setDisable(false);
        finishPane.setVisible(false);
        finishPane.setDisable(true);
        pausePane.setVisible(false);
        pausePane.setDisable(true);
        this.sheet.playSheet();
    }
    public void pauseGame() {
        pausePane.setVisible(true);
        pausePane.setDisable(false);
        this.sheet.pauseAll();
        
    }
    public void finishGame() {
        this.sheet.stop();
        gamePlayPane.setVisible(false);
        gamePlayPane.setDisable(true);
        finishPane.setVisible(true);
        finishPane.setDisable(false);
    }

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
            this.tapCheck(keyType);
            this.score.tempPrint();   
        }
    }

    public void onKeyRelease(KeyEvent event) {
        // 
    }

    public void tapCheck(int type) {
        if (this.tapPane.getChildren().size() > 0 ) {
            BaseNode temp = sheet.getNewestTap().baseNode;

            System.out.println("type: " + type);
            // crrentTime
            long ct = System.currentTimeMillis() - this.startTime;
            long diff = Math.abs(ct - temp.tapTime);

            if (diff < Setting.NO_TIGGER_TIME && (ct-temp.tapTime < Setting.MISS_TIME) ){
                // tap
                if (type != temp.type) {
                    this.score.addMiss();
                } else if ( diff < Setting.GREAT_TIME) {
                    this.score.addPerfect();
                } else if (diff < Setting.MISS_TIME) {
                    this.score.addGreat();
                } else if (ct - temp.tapTime < 0){
                    // diif > miss_time
                    this.score.addMiss();
                }

                sheet.tapNewestTap();
            } else {
                // ignore
                System.out.println("ignore");   // 
            }
        }
    }

    public void keyHandler(KeyEvent event) {
        KeyCode key = event.getCode();
    }
}