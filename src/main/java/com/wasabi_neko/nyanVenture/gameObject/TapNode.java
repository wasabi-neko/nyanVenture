package com.wasabi_neko.nyanVenture.gameObject;

import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.tool.PngAnima;

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

    private PngAnima[] destoryAnima;

    public TapNode() {
        this.imgV = new ImageView();
    }

    public void init(Pane _tapPane, BaseNode _baseNode, double x, double y, Image img) {
        this.tapPane = _tapPane;
        this.baseNode = _baseNode;

        this.imgV.setImage(img);
        // this.imgV.setFitHeight(200);
        // this.imgV.setFitWidth(200);
        this.imgV.setX(x);
        this.imgV.setY(y);

        this.destoryAnima = new PngAnima[2];
        this.destoryAnima[0] = new PngAnima(4, 300, 300);
        this.destoryAnima[0].setPane(this.tapPane);
        this.destoryAnima[0].setPath(Setting.BREAK_LEFT_IMG);
        this.destoryAnima[0].loadImg();

        this.destoryAnima[1] = new PngAnima(4, 300, 300);
        this.destoryAnima[1].setPane(this.tapPane);
        this.destoryAnima[1].setPath(Setting.BREAK_RIGHT_IMG);
        this.destoryAnima[1].loadImg();
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
    public void goodDestroy() {
        this.tapPane.getChildren().remove(this.imgV);
        this.timeline.stop();

        ImageView temp = new ImageView();
        temp.setX(200);
        temp.setY(450);
        tapPane.getChildren().add(temp);
        int typeCode = this.baseNode.type[2] == 1 ? 0 : 1; // TODO: new img name control
        destoryAnima[typeCode].playOnceAndDestory(tapPane, temp);
    }

    public void badDestroy() {
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
                // System.out.println("test: in end timeline");
                tapPane.getChildren().remove(imgV);
                imgV = null;    // clear
            }
        });
        timeline.play();
    }
}