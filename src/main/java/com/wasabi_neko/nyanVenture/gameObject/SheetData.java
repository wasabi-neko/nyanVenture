package com.wasabi_neko.nyanVenture.gameObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wasabi_neko.nyanVenture.Setting;

public class SheetData implements Serializable {
    public long songLength;
    private List<BaseNode> nodeList;
    private int index = 0;

    public SheetData() {
        super();
        this.nodeList = new ArrayList<BaseNode>();
        this.index = 0;
    }

    public SheetData(List<BaseNode> _nodeList) {
        super();
        this.nodeList = _nodeList;
        this.index = 0;
    }

    public void sort() {
        // un compleeted
    }

    public BaseNode getNext(long currentTime) {
        if (this.nodeList.size() <= index) {
            return null;
        }

        BaseNode nextNode = this.nodeList.get(this.index);

        boolean isBiggerThen = currentTime - nextNode.startTime >= 0;
        boolean isInInterval = Math.abs(currentTime - nextNode.startTime) < Setting.FIXUPDATE_RATE;

        if (isBiggerThen || isInInterval) {
            index += 1;
            return nextNode;
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return this.nodeList.isEmpty();
    }
}