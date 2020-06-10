package com.wasabi_neko.nyanVenture.gameObject;

import com.wasabi_neko.nyanVenture.Setting;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BaseNode {
    public long startTime = 0;
    public long tapTime = 0;
    public short upDown = 0;
    public short type = 0;
    public double startX;

    private Pane tapPane;
    private Score score;

    public ImageView imgV;
    public Timeline timeline;

    public BaseNode(Pane _tapPane) {
        this.tapPane = _tapPane;
    }

    public void init(long _startTime, long _tapTime, short _type, double x, double y, Image img, Score _score) {
        this.startTime = _startTime;
        this.tapTime = _tapTime;
        this.type = _type;

        this.imgV = new ImageView();
        this.imgV.setImage(img);
        this.imgV.setFitHeight(60);
        this.imgV.setFitWidth(60);
        this.startX = x;
        this.imgV.setX(x);
        this.imgV.setY(y);

        this.score = _score;
    }

    public void playAnima(double tapX, double endX) {
        // this.tapPane.getChildren().add(this.imgV);
        
        // TODO: add animation after miss
        // animation
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

        // from initX -> tapX
        long durationT = (this.tapTime - this.startTime) + Setting.MISS_TIME;
        KeyValue kv = new KeyValue(this.imgV.xProperty(), tapX);
        KeyFrame kf = new KeyFrame(Duration.millis(durationT), kv);

        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("test: in end timeline");   //TODO:
             
                score.addMiss();
                tapPane.getChildren().remove(0);
            }
        });
        timeline.play();
    }

    public void endAnima() {
        this.timeline.stop();

        // special aniamtion
        // this.tapPane.getChildren().remove(0);
    }
}