package com.wasabi_neko.nyanVenture.gameObject;

import java.util.ArrayList;

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

public class TapNode {
    public BaseNode baseNode;
    public ArrayList<TapNode> tapList;

    private Pane tapPane;
    private Score score;

    public ImageView imgV;
    public Timeline timeline;

    public TapNode(Pane _tapPane) {
        this.tapPane = _tapPane;
    }

    public void init(BaseNode _baseNode, ArrayList<TapNode> _tapList, double x, double y, Image img, Score _score) {
        this.baseNode = _baseNode;
        this.tapList = _tapList;
        
        this.imgV = new ImageView();
        this.imgV.setImage(img);
        this.imgV.setFitHeight(60);
        this.imgV.setFitWidth(60);
        this.imgV.setX(x);
        this.imgV.setY(y);

        this.score = _score;
    }

    public void playAnima(double tapX, double endX) {
        // this.tapPane.getChildren().add(this.imgV);
        
        // TODO: add animation after `miss`
        // animation
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

        // from initX -> tapX
        long tapDuration = baseNode.tapTime - baseNode.startTime;
        double speed = (tapX - this.imgV.getX()) / tapDuration;
        double terminalX = tapX + speed * (Setting.MISS_TIME);

        long durationT = tapDuration + Setting.MISS_TIME;
        KeyValue kv = new KeyValue(this.imgV.xProperty(), terminalX);
        KeyFrame kf = new KeyFrame(Duration.millis(durationT), kv);

        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("test: in end timeline");   //TODO:
             
                score.addMiss();
                tapPane.getChildren().remove(0);
                tapList.remove(0);
                imgV = null;    // clear
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