package com.wasabi_neko.nyanVenture;

public class Setting {
    
    public static final long FIXUPDATE_RATE = 10;
    public static final long BREAK_ANIMA_RATE = 20;
    public static final long ANIMA_RATE = 50;      // 10 frame per second
    /**
     * miss | bad | great | perfect | great | bad | no in range
     *     -150  -100   -50   0     50     100   150
     */
    public static final long BAD_TIME = 150;
    public static final long GREAT_TIME = 100;
    public static final long PERFECT_TIME = 50;

    public static final String IMG_NODE0_PATH = "nodes/node0.png";
    public static final String IMG_NODE1_PATH = "nodes/node1.png";
    public static final String BREAK_LEFT_IMG = "nodes/break0/%02d.png";
    public static final String BREAK_RIGHT_IMG = "nodes/break1/%02d.png";
    public static final String TARGET_IMG_PATH = "target.png";
    
    public static final String BG_PATH = "background/bg.png";
    public static final String BG_ROAD_PATH = "background/road.png";

    public static final String POPOUT_IMG_PATH = "popouts/%s.png";

    public static final String PLAYER_ANIMATION_PATH = "anima/%s/%02d.png";

    public static final String MUSIC_PATH = "music/%02d.wav";
}