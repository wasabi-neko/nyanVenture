package com.wasabi_neko.nyanVenture.gameObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

public class Popouts {
    private Image[] popImgArr;
    private ArrayList<Timeline> timeLineArray;
    private Pane popPane;

    public Popouts(Pane _popPane) {
        this.popPane = _popPane;
        timeLineArray = new ArrayList<>();
        loadImg();
    }

    public void popPerfect() {
        pop(this.popPane, 0);
    }

    public void popGreat() {
        pop(this.popPane, 1);
    }

    public void popBad() {
        pop(this.popPane, 2);
    }

    public void popMiss() {
        pop(this.popPane, 3);
    }

    // public start() {
        
    // }

    // public pause() {

    // }

    // public stop() {

    // }

    private void pop(final Pane popPane, int index) {
        final ImageView imgV = new ImageView(this.popImgArr[index]);
        popPane.getChildren().add(imgV);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popPane.getChildren().remove(imgV);
            }
        });
        timeline.play();
    }

    public void loadImg() {
        try {
            String perfect = String.format(Setting.POPOUT_IMG_PATH, "perfect");
            String great = String.format(Setting.POPOUT_IMG_PATH, "great");
            String bad = String.format(Setting.POPOUT_IMG_PATH, "bad");
            String miss = String.format(Setting.POPOUT_IMG_PATH, "miss");
    
            this.popImgArr = new Image[4];
            this.popImgArr[0] = new Image(App.class.getResourceAsStream(perfect));
            this.popImgArr[1] = new Image(App.class.getResourceAsStream(great));
            this.popImgArr[2] = new Image(App.class.getResourceAsStream(bad));
            this.popImgArr[3] = new Image(App.class.getResourceAsStream(miss));
        } catch (Exception e) {
            System.out.println("#ERROR:# popouts img not found");
            System.out.println(e);
        }
    }
}