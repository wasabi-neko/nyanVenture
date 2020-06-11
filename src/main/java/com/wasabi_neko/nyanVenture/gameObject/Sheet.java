package com.wasabi_neko.nyanVenture.gameObject;

import java.util.ArrayList;

import javax.print.event.PrintJobListener;

import com.wasabi_neko.nyanVenture.*;

import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.image.*;

public class Sheet {
    public Pane tapPane;
    public Pane holdPane;
    public Pane startUpperPane;
    public Pane startLowerPane;
    public Pane tapPosPane;
    public long startTime;

    public long fixUpdateRate = 10;
    public int upperY = 150, lowerY = 250;
    public int startX = 600, tapX = 100, endX = 0;

    private Score score;
    private Image[] tapImg;
    private Image[] holdImg;

    public Timeline updater;
    public SheetData sheetData;
    private ArrayList<TapNode> tapList;

    public Sheet(Pane _tapPane, Pane _holdPane, Pane _upperPane, Pane _lowerPane, Pane _tapPosPane, Score _score, long _startTime){
        this.startTime = _startTime;
        this.tapPane = _tapPane;
        this.holdPane = _holdPane;
        this.startUpperPane = _upperPane;
        this.startLowerPane = _lowerPane;
        this.tapPosPane = _tapPosPane;
        this.score = _score;

        this.sheetData = new SheetData();
        this.tapList = new ArrayList<TapNode>();

        this.updater = new Timeline();
        this.tapImg = new Image[4];
        this.holdImg = new Image[2];
        this.loadImage();

        this.startX = this.startLowerPane.layoutXProperty().intValue();
        this.lowerY = this.startLowerPane.layoutYProperty().intValue();
        this.upperY = this.startUpperPane.layoutYProperty().intValue();
        this.tapX = this.tapPosPane.layoutXProperty().intValue();
        this.endX = 0;

        // // temp
        // ArrayList<BaseNode> tempList = new ArrayList<>();
        // for (int i = 1; i < 5; i++) {
        //     long t1 = i*1000, t2 = t1 + 2000;
        //     short type1 = (short) (i%2);
        //     short u1 = 0;
        //     BaseNode temp = new BaseNode(t1, t2, type1, u1);

        //     tempList.add(temp);
        // }   
        // this.sheetData = new SheetData(tempList);

        // for (BaseNode baseNode : tempList) {
        //     System.out.println(baseNode);
        // }
        // // temp

        // start updater
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.setAutoReverse(true);
        EventHandler<ActionEvent> oneUpdate = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                long currentTime = System.currentTimeMillis() - startTime;
                BaseNode node = null;
                node = sheetData.getNext(currentTime);

                if (node != null) {
                    System.out.printf("test:%d\n", currentTime);
                    addTap(node);
                }
            }
        };
        KeyValue kv = null;
        KeyFrame kf = new KeyFrame(Duration.millis(10), oneUpdate,kv);
        updater.getKeyFrames().add(kf);
    }

    public void playSheet() {
        System.out.println("start play sheet"); //TODO print format
        updater.play();
    }

    public void stop() {
        this.updater.stop();
        for (TapNode node : this.tapList) {
            node.timeline.stop();
        }
    }

    public void pauseAll() {
        this.updater.pause();
        for (TapNode node : this.tapList) {
            node.timeline.pause();
        }
    }

    public void reStart() {
        this.updater.play();
        for (TapNode node : this.tapList) {
            node.timeline.play();
        }
    }

    public void addTap(BaseNode baseNode) {
        TapNode newNode = new TapNode(this.tapPane);
        int startY;

        if (baseNode.upDown == 0) {
            startY = lowerY;
        } else {
            startY = upperY;
        }

        if (baseNode.type >= 2)  {
            // TODO: print format
            System.out.println("#ERROR: baseNode.type no proper");
            return;
        }
        
        newNode.init(baseNode, this.tapList, startX, startY, this.tapImg[baseNode.type], this.score);
        newNode.playAnima(this.tapX, this.endX);

        // add
        this.tapPane.getChildren().add(newNode.imgV);
        this.tapList.add(newNode);
    }

    public TapNode getNewestTap() {
        return this.tapList.get(0);
    }

    public void tapNewestTap() {
        this.tapList.get(0).endAnima();
        this.tapPane.getChildren().remove(0);
        this.tapList.remove(0);
    }

    public void loadImage() {
        try {
            String node1Path = Setting.IMG_NODE1_PATH;
            String node0Path = Setting.IMG_NODE0_PATH;
            this.tapImg[0] = new Image(App.class.getResourceAsStream(node0Path));
            this.tapImg[1] = new Image(App.class.getResourceAsStream(node1Path));
        } catch (Exception e) {
            System.out.println("#Error:# no Image");
        }
    }
}