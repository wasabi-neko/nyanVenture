package com.wasabi_neko.nyanVenture.gameObject;

public class Score {
    public int missTimes;
    public int greatTimes;
    public int perfectTimes;
    
    public int finalScore;
    public int maxComble;
    public int currentComble;

    public Score() {
        this.missTimes = 0;
        this.greatTimes = 0;
        this.perfectTimes = 0;
        
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

    public void addMiss() {
        this.missTimes += 1;
        currentComble = 0;
    }

    public void tempPrint() {
        System.out.printf("P:%d, G:%d, M:%d\n", this.perfectTimes, this.greatTimes, this.missTimes);
    }
}