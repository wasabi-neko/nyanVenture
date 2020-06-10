package com.wasabi_neko.nyanVenture.gameObject;

import com.wasabi_neko.nyanVenture.Setting;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player {
    public Pane tapPane;
    public Pane holdPane;
    public long startTime = 0;

    public int upDown = 0;
    public int hp;
    
    private Image img;

    // TODO Sheet add method: get node list.
    //  sheet add method: remove node
    public Player(Pane _tapPane, Pane _holdPane, long _startTime) {
        this.startTime = _startTime;
        this.tapPane = _tapPane;
        this.holdPane = _holdPane;
    }

    
}