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

public class BGManager {
    public Pane bgPane;
    public Pane roadPane;
    public final long bgTime = 10000;
    public final long roadTime = 5000;


    public Timeline bgTimeline1, bgTimeline2;
    public Timeline fgTimeline1, fgTimeline2;
    private ImageView bgImgV1;
    private ImageView bgImgV2;
    private ImageView roadImgV1;
    private ImageView roadImgV2;

    public BGManager(Pane _bgPane, Pane _roadPane) {
        this.bgPane = _bgPane;
        this.roadPane = _roadPane;

        this.bgImgV1 = new ImageView();
        this.bgImgV2 = new ImageView();
        this.roadImgV1 = new ImageView();
        this.roadImgV2 = new ImageView();
        
        this.bgPane.getChildren().addAll(bgImgV1, bgImgV2);
        this.roadPane.getChildren().addAll(roadImgV1, roadImgV2);
        bgImgV1.setX(0);
        bgImgV2.setX(2875);
        roadImgV1.setX(0);
        roadImgV2.setX(2875);

        this.bgTimeline1 = new Timeline();
        this.bgTimeline2 = new Timeline();

        KeyValue kv1_1 = new KeyValue(bgImgV1.xProperty(), 0);
        KeyValue kv1_2 = new KeyValue(bgImgV1.xProperty(), -2880);
        KeyFrame kf1 = new KeyFrame(Duration.millis(bgTime), kv1_1, kv1_2);
        this.bgTimeline1.getKeyFrames().add(kf1);
        this.bgTimeline1.setOnFinished(bgPlayAgain);

        KeyValue kv2_1 = new KeyValue(bgImgV2.xProperty(), 2875);
        KeyValue kv2_2 = new KeyValue(bgImgV2.xProperty(), 0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(bgTime), kv2_1, kv2_2);
        this.bgTimeline2.getKeyFrames().add(kf2);

        this.fgTimeline1 = new Timeline();
        this.fgTimeline2 = new Timeline();

        KeyValue kv_r1_1 = new KeyValue(roadImgV1.xProperty(), 0);
        KeyValue kv_r1_2 = new KeyValue(roadImgV1.xProperty(), -2880);
        KeyFrame kf_r1 = new KeyFrame(Duration.millis(roadTime), kv_r1_1, kv_r1_2);
        this.fgTimeline1.getKeyFrames().add(kf_r1);
        this.fgTimeline1.setOnFinished(roadPlayAgain);

        KeyValue kv_r2_1 = new KeyValue(roadImgV2.xProperty(), 2875);
        KeyValue kv_r2_2 = new KeyValue(roadImgV2.xProperty(), 0);
        KeyFrame kf_r2 = new KeyFrame(Duration.millis(roadTime), kv_r2_1, kv_r2_2);
        this.fgTimeline2.getKeyFrames().add(kf_r2);
    }
    private EventHandler<ActionEvent> bgPlayAgain = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("bg end");
            bgImgV1.setX(0);
            bgImgV2.setX(2875);
            bgTimeline1.stop();
            bgTimeline2.stop();
            bgTimeline1.play();
            bgTimeline2.play();
        }
    };
    private EventHandler<ActionEvent> roadPlayAgain = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("bg end");
            roadImgV1.setX(0);
            roadImgV2.setX(2875);
            fgTimeline1.stop();
            fgTimeline2.stop();
            fgTimeline1.play();
            fgTimeline2.play();
        }
    };

    public void play() {
        this.bgTimeline1.play();
        this.bgTimeline2.play();
        this.fgTimeline1.play();
        this.fgTimeline2.play();
    }

    public void pause() {
        this.bgTimeline1.pause();
        this.bgTimeline2.pause();
        this.fgTimeline1.pause();
        this.fgTimeline2.pause();
    }

    public void stop() {
        this.bgTimeline1.stop();
        this.bgTimeline2.stop();
        this.fgTimeline1.stop();
        this.fgTimeline2.stop();
    }

    public void loadImg() {
        try {
            Image bg;
            Image road;
            double imgWidth = 2880;
            double imgHight = 900;

            bg = new Image(App.class.getResourceAsStream(Setting.BG_PATH), imgWidth, imgHight, true, false);
            road = new Image(App.class.getResourceAsStream(Setting.BG_ROAD_PATH), imgWidth, imgHight, true, false);
            
            this.bgImgV1.setImage(bg);
            this.bgImgV2.setImage(bg);
            this.roadImgV1.setImage(road);
            this.roadImgV2.setImage(road);
        } catch (Exception e) {
            System.out.println("#Error:# no bg Image");
            System.out.println(e);
        }
    }
}