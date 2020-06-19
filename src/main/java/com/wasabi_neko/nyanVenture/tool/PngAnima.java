package com.wasabi_neko.nyanVenture.tool;

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

public class PngAnima {
    public Pane pane;
    public ImageView imgV;
    private Image[] imgArr;
    public Timeline timeline, oneceTimeline;
    
    private int animaCounter = 0;
    private int arrSize;

    public String path;
    public double imgSizeX, imgSizeY;
    

    public PngAnima(int size, double _imgSizeX, double _imgSizeY) {
        // this.imgV = _imgV;
        this.imgArr = new Image[size];
        this.arrSize = size;
        this.imgSizeX = _imgSizeX;
        this.imgSizeY = _imgSizeY;

        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(Setting.BREAK_ANIMA_RATE), this.onUpdate, kv);
        timeline.getKeyFrames().add(kf);

        this.oneceTimeline = new Timeline();
        oneceTimeline.setCycleCount(Timeline.INDEFINITE);
        oneceTimeline.setAutoReverse(true);

        KeyValue kv1 = null;
        KeyFrame kf1 = new KeyFrame(Duration.millis(Setting.BREAK_ANIMA_RATE), this.destoryUpdate, kv1);
        oneceTimeline.getKeyFrames().add(kf1);
    }

    public void setImgArr(Image[] _imgArr)  {
        this.imgArr = _imgArr;
    }

    public void setPath(String _path) {
        this.path = _path;
    }

    public void setImgV(ImageView _imgV) {
        this.imgV = _imgV;
    }

    public void setPane(Pane _pane) {
        this.pane = _pane;
    }

    private EventHandler<ActionEvent> onUpdate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (animaCounter >= arrSize) {
                animaCounter = 0;
            }
            imgV.setImage(imgArr[animaCounter]);
            animaCounter += 1;
        }
    };

    private EventHandler<ActionEvent> destoryUpdate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (animaCounter >= arrSize) {
                pane.getChildren().remove(imgV);
                imgV = null;
                oneceTimeline.stop();
            } else {
                imgV.setImage(imgArr[animaCounter]);
                animaCounter += 1;
            }
        }
    };

    public void playOnceAndDestory(Pane _pane, ImageView _imgV) {
        PngAnima newAnima = new PngAnima(this.arrSize, this.imgSizeX, this.imgSizeY);
        newAnima.setImgArr(this.imgArr);
        newAnima.setPane(_pane);
        newAnima.setImgV(_imgV);
        newAnima.oneceTimeline.play();
    }

    // public void play() {
        
    // }

    // public void pause() {

    // }

    // public void stop() {

    // }

    public void loadImg() {
        try {
            for (int i = 0; i < this.arrSize; i++) {
                String runPath = String.format(this.path, i);
                this.imgArr[i] = new Image(App.class.getResourceAsStream(runPath), this.imgSizeX, this.imgSizeY, true, false);
            }

        } catch (Exception e) {
            System.out.println("#Error:# no animation Image");
            System.out.println("\tpath:" + this.path);
            System.out.println(e);
        }
    }
}