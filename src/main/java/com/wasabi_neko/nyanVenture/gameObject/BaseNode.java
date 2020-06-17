package com.wasabi_neko.nyanVenture.gameObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class BaseNode implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public long startTime = 0;
    public long tapTime = 0;
    public short upDown = 0;
    public short[] type;
    public boolean isHold;

    public BaseNode() {
        this.type = new short[4];
    }

    /**
     * Base Node data
     * @param _startTime
     * @param _tapTime
     * @param _type [0,0,0,0] for u,d,l,r; -1 -> ignore, 0 -> not tap, 1 -> should tap
     * @param _upDown 0 for lower; 1 for upper
     */
    public BaseNode(long _startTime, long _tapTime, short[] _type, short _upDown, boolean _isHold){
        this.startTime = _startTime;
        this.tapTime = _tapTime;
        this.type = _type;
        this.upDown = _upDown;
        this.isHold = _isHold;

        if (this.type.length != 4) {
            System.out.println("#ERROR:# wrong input format of type. at BaseNode()");
        }
    }

    public boolean compareType(boolean[] tapArr) {
        if (tapArr.length < 4) {
            System.out.println("#ERROR:# wrong input type at BaseNode.compareType");
            return false;
        }

        boolean returns = true;
        for(int i = 0; i < 4; i++) {
            if (this.type[i] != -1) {
                int tapVal = tapArr[i] ? 1 : 0;
                if (this.type[i] != tapVal) {
                    returns = false;
                    break;
                }
            }
        }

        return returns;
    }

    public int getTypeInt(int index) {
        return this.type[index];
    }

    @Override
    public String toString() {
        String template = "BaseNode{startTime=%d,tapTime=%d,type=%s,upDown=%d,isHold=%b}";
        String arrStr = Arrays.toString(this.type);
        return String.format(template, startTime, tapTime, arrStr, upDown, isHold);
    }
}