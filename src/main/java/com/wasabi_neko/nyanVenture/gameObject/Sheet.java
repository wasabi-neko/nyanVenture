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
    public long startTime;

    public long fixUpdateRate = 10;
    public int upperY = 150, lowerY = 250;
    public int startX = 600, tapX = 100, endX = 0;

    private Score score;
    private Image[] tapImg;
    private Image[] holdImg;

    private ArrayList<BaseNode> tapList;

    public Sheet(Pane tapPane, Pane holdPane, Score _score, long _startTime){
        this.startTime = _startTime;
        this.tapPane = tapPane;
        this.holdPane = holdPane;
        this.score = _score;

        tapList = new ArrayList<BaseNode>();

        this.tapImg = new Image[4];
        this.holdImg = new Image[2];
        this.loadImage();
    }

    public void playSheet() {
        System.out.println("in play Sheet");

        long t1 = 0, t2 = 2000;
        short type1 = 0;
        this.addTap( t1, t2, 0, type1);
    }

    public void addTap(long startTime, long tapTime, int upDown, short type) {
        BaseNode newNode = new BaseNode(this.tapPane);
        int startY;

        if (upDown == 0) {
            startY = lowerY;
        } else {
            startY = upperY;
        }
        
        newNode.init(startTime, tapTime, type, startX, startY, this.tapImg[0], this.score);
        newNode.playAnima(this.tapX, this.endX);

        // add
        this.tapPane.getChildren().add(newNode.imgV);
        this.tapList.add(newNode);
    }

    public BaseNode getNewestTap() {
        return this.tapList.get(0);
    }

    public void tapNewestTap() {
        this.tapList.get(0).endAnima();
        this.tapPane.getChildren().remove(0);
        this.tapList.remove(0);
    }

    public void loadImage() {
        try {
            this.tapImg[0] = new Image(App.class.getResourceAsStream("nodes/node1.png"));
        } catch (Exception e) {
            System.out.println("#Error:# no Image");
        }
    }
}