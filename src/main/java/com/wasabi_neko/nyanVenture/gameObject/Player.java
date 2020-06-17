package com.wasabi_neko.nyanVenture.gameObject;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Player:
 *  data: player_hight, player_hp
 *  method: play animation
 */
public class Player {
    public Pane playerPane;     // move hight
    public Pane charaPane;      // chara animation
    public Pane effectPane;     // effect animation

    public double hight;
    public boolean grounded;
    public int hp;
    public String currentAnimaState;

    enum AnimaCode{RUN, JUMP, HIT, SLIDE};
    
    private Timeline updater;
    private ImageView playerImgV, effectImgV;
    private Image[] currentAnima;
    private Image[] runAnima, hurtAnima, hitAnima, jumpAnima;

    private final double playerSize = 400;
    private int animaCounter;

    public Player(Pane _playerPane, Pane _charaPane, Pane _effectPane) {
        this.playerPane = _playerPane;
        this.charaPane = _charaPane;
        this.effectPane = _effectPane;

        // Pane init
        this.playerImgV = new ImageView();
        this.charaPane.getChildren().add(this.playerImgV);
        this.playerImgV.setX(-100);
        this.playerImgV.setY(-100);

        // animation init
        this.updater = new Timeline();
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.setAutoReverse(true);

        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(Setting.ANIMA_RATE), this.onUpdate, kv);
        updater.getKeyFrames().add(kf);
    }

    private EventHandler<ActionEvent> onUpdate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            animaUpdate();
        }
    };

    public void animaUpdate() {
        if (this.animaCounter >= this.currentAnima.length) {
            this.animaCounter = 0;
        }
        this.playerImgV.setImage(this.currentAnima[this.animaCounter]);
        this.animaCounter += 1;
    }

    // -------------------------------------------------------------------------
    // Animation
    // -------------------------------------------------------------------------
    public void pauseAnima() {
        this.updater.pause();
    }

    public void stopAnima() {
        this.updater.stop();
    }

    public void playAnimaRun() {
        this.playAnimation(AnimaCode.RUN.toString());
        this.updater.play();
    }

    public void playAnimaHit() {
        // 
    }

    public void playAnimaHurt() {
        // 
    }

    public void playAnimaJump() {
        // 
    }

    private void playAnimation(String type) {
        this.animaCounter = 0;
        this.currentAnimaState = type;
        switch (type) {
            case "RUN":
                this.currentAnima = runAnima;
                break;
            default:
                this.currentAnima = runAnima;
                break;
        }
    }
    
    // -------------------------------------------------------------------------
    // load img
    // -------------------------------------------------------------------------
    public void loadImg() {
        try {
            
            this.runAnima = new Image[4];
            for (int i = 0; i < 4; i++) {
                String runPath = String.format(Setting.PLAYER_ANIMATION_PATH, AnimaCode.RUN.toString(), i);
                this.runAnima[i] = new Image(App.class.getResourceAsStream(runPath), this.playerSize, this.playerSize, true, false);
            }

        } catch (Exception e) {
            System.out.println("#Error:# no player Image");
            System.out.println(e);
        }
    }
}