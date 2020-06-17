package com.wasabi_neko.nyanVenture.gameObject;

import com.wasabi_neko.nyanVenture.*;

import java.util.PriorityQueue;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;

public class Sheet {
    public Pane tapPane;
    public Pane holdPane;
    public Pane startUpperPane;
    public Pane startLowerPane;
    public Pane tapPosPane;
    public long startTime;

    public int upperY, lowerY;
    public int startX, tapX, endX;

    private Image[] tapImg;
    private Image[] holdImg;

    public SheetData sheetData;
    private PriorityQueue<TapNode> tapList;

    public Sheet(Pane _tapPane, Pane _holdPane, Pane _upperPane, Pane _lowerPane, Pane _tapPosPane,
            SheetData _sheetData) {
        this.tapPane = _tapPane;
        this.holdPane = _holdPane;
        this.startUpperPane = _upperPane;
        this.startLowerPane = _lowerPane;
        this.tapPosPane = _tapPosPane;

        this.sheetData = _sheetData;
        this.tapList = new PriorityQueue<>(Comparators.tapNode_tapTime_CMP);

        this.tapImg = new Image[4];
        this.holdImg = new Image[2];
        this.loadImage();

        this.startX = this.startLowerPane.layoutXProperty().intValue();
        this.lowerY = this.startLowerPane.layoutYProperty().intValue();
        this.upperY = this.startUpperPane.layoutYProperty().intValue();
        this.tapX = this.tapPosPane.layoutXProperty().intValue();
        this.endX = 0;
    }

    // -------------------------------------------------------------------------
    // Getter
    // -------------------------------------------------------------------------
    public boolean isTapListEmpty() {
        return this.tapList.isEmpty();
    }

    // -------------------------------------------------------------------------
    // Animation control
    // -------------------------------------------------------------------------
    public void reset() {
        this.stop();
        this.sheetData.reset();
    }

    public void pause() {
        for (TapNode node : this.tapList) {
            node.timeline.pause();
        }
    }

    public void stop() {
        for (TapNode node : this.tapList) {
            node.timeline.stop();
            node.destroy();
        }
    }

    public void play() {
        for (TapNode node : this.tapList) {
            node.timeline.play();
        }
    }

    // -------------------------------------------------------------------------
    // Main methods
    // -------------------------------------------------------------------------
    public TapNode getNewestTap() {
        return this.tapList.peek();
    }

    public void removeOneFromTapList() {
        this.tapList.poll();
    }

    public void destoryNewestTap() {
        TapNode node = this.tapList.poll(); // remove form list
        node.destroy(); // remove form pane
    }

    /**
     * Create new tap if it's the time
     * 
     * @param currentTime
     */
    public void tapCreateCheck(long currentTime) {
        BaseNode node = null;
        node = sheetData.getNext(currentTime);

        if (node != null) {
            // System.out.printf("test:%d\n", currentTime);
            addTap(node);
        }
    }

    // -------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------
    private void addTap(BaseNode baseNode) {
        TapNode newNode = new TapNode();
        int startY;

        if (baseNode.upDown == 0) {
            startY = lowerY;
        } else {
            startY = upperY;
        }

        // if (baseNode.type >= 2) {
        // System.out.println("#ERROR: baseNode.type no proper");
        // return;
        // }

        int imgCode = baseNode.type[2] == 1 ? 0 : 1; // TODO: new img name control

        // paly animation
        newNode.init(this.tapPane, baseNode, startX, startY, this.tapImg[imgCode]);
        newNode.playAnima(this.tapX, this.endX);
        // add to queue
        this.tapList.add(newNode);
    }

    private void loadImage() {
        try {
            String node1Path = Setting.IMG_NODE1_PATH;
            String node0Path = Setting.IMG_NODE0_PATH;

            this.tapImg[0] = new Image(App.class.getResourceAsStream(node0Path), (double) 100, (double) 100, true,
                    false);
            this.tapImg[1] = new Image(App.class.getResourceAsStream(node1Path), (double) 100, (double) 100, true,
                    false);
        } catch (Exception e) {
            System.out.println("#Error:# no Image");
        }
    }
}