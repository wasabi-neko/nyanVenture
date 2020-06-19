package com.wasabi_neko.nyanVenture.gameObject;

import javax.print.event.PrintJobListener;

import com.wasabi_neko.nyanVenture.App;
import com.wasabi_neko.nyanVenture.Setting;
import com.wasabi_neko.nyanVenture.tool.FileManager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameManager {
    public Sheet  sheet;
    public Player player;
    public Score score;
    public Popouts popouts;
    public BGManager bgManager;
    
    public long startTime = 0;
    private long timePassed = 0;
    private int gameStatus;

    private Pane tapPane, holdPane;
    private Pane tapPosPane, lowerPosPane, upperPosPane;
    private Pane popPane;
    private ImageView targetImgV;

    public GameManager() {
        // no thing
        // kimoji~
    }

    // -------------------------------------------------------------------------
    // Init  methods
    // -------------------------------------------------------------------------
    public void initSheet(Pane _tapPane, Pane _holdPane, Pane _tapPosPane, Pane _lowerPosPane, Pane _upperPosPane) {
        this.tapPane = _tapPane;
        this.holdPane = _holdPane;
        this.tapPosPane = _tapPosPane;
        this.lowerPosPane = _lowerPosPane;
        this.upperPosPane = _upperPosPane;
    }

    public void initPopouts(Pane _popPane) {
        this.popPane = _popPane;
    }

    public void initPalyer(Pane _playerPane, Pane _charaPane, Pane _effectPane) {
        this.player = new Player(_playerPane, _charaPane, _effectPane);
    }

    public void initBG(Pane _bgPane, Pane _roadPane) {
        this.bgManager = new BGManager(_bgPane, _roadPane);   
    }

    // -------------------------------------------------------------------------
    // Getter
    // -------------------------------------------------------------------------
    public int getGameStatus() {
        return this.gameStatus;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getTimePassed() {
        return this.timePassed;
    }

    public final Score getScore() {
        return this.score;
    }

    // -------------------------------------------------------------------------
    // Game Manage
    // -------------------------------------------------------------------------
    public boolean loadGame(int sheetIndex) {
        this.gameStatus = -1;
        try {
            // Load Sheet Data
            SheetData data = FileManager.getSheetData(sheetIndex);
            this.sheet = new Sheet(this.tapPane, this.holdPane, this.upperPosPane, this.lowerPosPane, this.tapPosPane, data);
            this.sheet.sheetData.sort();

            this.popouts = new Popouts(popPane);

            // Load Player
            this.player.loadImg();

            // Load bg
            this.bgManager.loadImg();

            Image temp = new Image(App.class.getResourceAsStream(Setting.TARGET_IMG_PATH), 128, 128, true, false);
            this.targetImgV = new ImageView(temp);
            this.tapPosPane.getChildren().add(this.targetImgV);
            this.targetImgV.setX(-16);
            this.targetImgV.setY(-16);

            return true;
        } catch (Exception e) {
            System.out.println("load game fail");
            System.out.println(e);
            return false;
        }
    }

    public void resetGame() {
        long timeBiass = 300;

        this.gameStatus = 0;
        this.startTime = -1;
        this.timePassed = timeBiass;
        this.score = new Score();
        this.sheet.reset();
    }

    public void startGame() {
        this.gameStatus = 1;
        this.startTime = System.currentTimeMillis() - timePassed;
        this.sheet.play();
        this.player.playAnimaRun();
        this.bgManager.play();
        System.out.println(this.timePassed);
    }

    public void pauseGame() {
        this.gameStatus = 2;
        this.timePassed = System.currentTimeMillis() - this.startTime;
        this.sheet.pause();
        this.player.pauseAnima();
        this.bgManager.pause();
    }

    public void stopGame() {
        this.gameStatus = 3;
        this.sheet.stop();
        this.player.stopAnima();
        this.bgManager.stop();
        this.timePassed = 0;
    }

    // -------------------------------------------------------------------------
    // Checker
    // -------------------------------------------------------------------------
    public void update() {
        if (this.gameStatus != 1) {
            return;
        }

        this.timePassed = System.currentTimeMillis() - this.startTime;

        // System.out.println("game update:" + this.timePassed);
        this.sheet.tapCreateCheck(timePassed);
        missCheck();
    }

    public void missCheck() {
        if (this.gameStatus == 1 && !this.sheet.isTapListEmpty()) {
            long ct = this.timePassed;
            BaseNode newNode = this.sheet.getNewestTap().baseNode;
    
            if (ct - newNode.tapTime > Setting.BAD_TIME) {
                popouts.popMiss();
                score.addMiss();
                sheet.removeOneFromTapList();
                // sheet.destoryNewestTap();
            }
        }
    }
    
    /**
     * check a tapNode
     * @param tapArr [up, down, left, right] is pressed
     */
    public void tapCheck(boolean[] tapArr) {
        // if playing game and list not empty
        if ( this.gameStatus == 1 && !this.sheet.isTapListEmpty() ) {
            BaseNode newNode = this.sheet.getNewestTap().baseNode;
            
            // crrentTime
            long ct = this.timePassed;
            long diff = Math.abs(ct - newNode.tapTime);

            if (diff < Setting.BAD_TIME) {
                // tap! OwO
                int breakType = 1;
                
                if (newNode.compareType(tapArr)) {
                    // correct type
                    // check is bad or great or perfect
                    if (diff <= Setting.PERFECT_TIME) {
                        this.popouts.popPerfect();
                        this.score.addPerfect();
                    } else if (diff <= Setting.GREAT_TIME) {
                        this.popouts.popGreat();
                        this.score.addGreat();
                    } else if (diff <= Setting.BAD_TIME) {
                        this.popouts.popBad();
                        this.score.addBad();
                        breakType = 0;
                    } 
                } else {
                    // wrong type => bad
                    this.popouts.popBad();
                    this.score.addBad();
                    breakType = 0;
                }

                this.sheet.destoryNewestTap(breakType);
            } else {
                // ignore
                // System.out.println("ignore");
            }
        }
    }

    public boolean isFisnished() {
        return isDaed() || (this.timePassed > this.sheet.sheetData.getSongLength());
    }
    
    public boolean isDaed() {
        return false;
    }

}