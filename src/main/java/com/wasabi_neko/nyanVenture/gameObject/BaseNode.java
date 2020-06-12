package com.wasabi_neko.nyanVenture.gameObject;

import java.io.Serializable;

public class BaseNode implements Serializable {
    public long startTime = 0;
    public long tapTime = 0;
    public short upDown = 0;
    public short type = 0;

    public BaseNode() {
        // Null constructor
    }

    /**
     * Base Node data
     * @param _startTime
     * @param _tapTime
     * @param _type
     * @param _upDown 0 for lower; 1 for upper
     */
    public BaseNode(long _startTime, long _tapTime, short _type, short _upDown) {
        this.startTime = _startTime;
        this.tapTime = _tapTime;
        this.type = _type;
        this.upDown = _upDown;
    }

    @Override
    public String toString() {
        return String.format("BaseNode{startTime=%d,tapTime=%d,type=%d,upDown=%d}", startTime, tapTime, type, upDown);
    }
}