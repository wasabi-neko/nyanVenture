package com.wasabi_neko.nyanVenture.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.gameObject.BaseNode;
import com.wasabi_neko.nyanVenture.gameObject.Player;
import com.wasabi_neko.nyanVenture.gameObject.Score;
import com.wasabi_neko.nyanVenture.gameObject.Sheet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePlay implements Initializable {
    private long fixUpdateRate = 10;
    public long startTime = 0;

    public Sheet  sheet;
    public Player player;
    public Score score;

    @FXML
    Pane rootPane;
    @FXML
    Pane holdPane;
    @FXML
    Pane tapPane;
    @FXML
    Button btEsc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("start");

        this.startTime = System.currentTimeMillis();
        this.player = new Player(this.tapPane, this.holdPane, startTime);
        this.score = new Score();
        this.sheet = new Sheet(this.tapPane, this.holdPane, this.score, startTime);

        sheet.playSheet();
    }

    public void onEscPressed() {
        App.sceneController.changeScene("mainMenu");
    }

    public void onKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        int keyType = -1;

        if (key == KeyCode.RIGHT) {
            keyType = 0;
        } else if (key == KeyCode.LEFT) {
            keyType = 1;
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
            BaseNode temp = sheet.getNewestTap();

            System.out.println("type: " + type);
            // crrentTime
            long ct = System.currentTimeMillis() - this.startTime;
            long diff = Math.abs(ct - temp.tapTime);

            if (diff < Setting.NO_TIGGER_TIME) {
                // tap
                if (type != temp.type) {
                    this.score.addMiss();
                } else if ( diff < Setting.GREAT_TIME) {
                    this.score.addPerfect();
                } else if (diff < Setting.MISS_TIME) {
                    this.score.addGreat();
                } else {
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