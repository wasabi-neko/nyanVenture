package com.wasabi_neko.nyanVenture.gameObject;

public class Score {
    public int missTimes;
    public int greatTimes;
    public int perfectTimes;
    public int badTimes;
    
    public int finalScore;
    public int maxComble;
    public int currentComble;

    public Score() {
        this.missTimes = 0;
        this.greatTimes = 0;
        this.perfectTimes = 0;
        this.badTimes = 0;
        
        this.finalScore = 0;
        this.maxComble = 0;
        this.currentComble = 0;
    }

    public void addPerfect() {
        this.perfectTimes += 1;
        currentComble += 1;
        if (currentComble > maxComble) {
            maxComble = currentComble;
        }
    }

    public void addGreat() {
        this.greatTimes += 1;
        currentComble += 1;
        if (currentComble > maxComble) {
            maxComble = currentComble;
        }
    }

    public void addBad() {
        this.badTimes += 1;
        currentComble = 0;
    }

    public void addMiss() {
        this.missTimes += 1;
        currentComble = 0;
    }

    public int getScore() {
        return this.perfectTimes * 100 + this.greatTimes * 70 + this.badTimes * 30;
    }

    public double getAccuracy() {
        double all = this.perfectTimes + this.greatTimes + this.badTimes + this.missTimes;
        double hit = this.perfectTimes + this.greatTimes * 0.7 + this.badTimes * 0.3;

        return hit / all;
    }

    public void tempPrint() {
        System.out.printf("P:%d, G:%d, B:%d, M:%d\n", this.perfectTimes, this.greatTimes, this.badTimes, this.missTimes);
    }
}