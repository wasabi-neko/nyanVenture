package com.wasabi_neko.nyanVenture.gameObject;

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
 * class: TapNode
 *  holds BaseNode and img data
 *  perform animation
 */
public class TapNode {
    public BaseNode baseNode;
    public Timeline timeline;

    private ImageView imgV;
    private Pane tapPane;

    public TapNode() {
        this.imgV = new ImageView();
    }

    public void init(Pane _tapPane, BaseNode _baseNode, double x, double y, Image img) {
        this.tapPane = _tapPane;
        this.baseNode = _baseNode;

        this.imgV.setImage(img);
        this.imgV.setFitHeight(60);
        this.imgV.setFitWidth(60);
        this.imgV.setX(x);
        this.imgV.setY(y);
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------
    public double getImgX() {
        double returns = -1;
        if (this.imgV != null) {
            returns = this.imgV.xProperty().doubleValue();
        }

        return returns;
    }

    public double getImgY() {
        double returns = -1;
        if (this.imgV != null) {
            returns = this.imgV.yProperty().doubleValue();
        }

        return returns;
    }

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------
    public void destroy() {
        this.tapPane.getChildren().remove(this.imgV);
        this.timeline.stop();
    }

    public void playAnima(double tapX, double endX) {
        this.tapPane.getChildren().add(this.imgV);

        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);

        // from initX -> endX
        double startX = this.imgV.xProperty().doubleValue();
        long tapDuration = baseNode.tapTime - baseNode.startTime;
        double speed = (tapX - startX) / tapDuration;
        long realDuration = Math.round((endX - startX) / speed );

        KeyValue kv = new KeyValue(this.imgV.xProperty(), endX);
        KeyFrame kf = new KeyFrame(Duration.millis(realDuration), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("test: in end timeline");
            
                tapPane.getChildren().remove(imgV);
                imgV = null;    // clear
            }
        });
        timeline.play();
    }
}