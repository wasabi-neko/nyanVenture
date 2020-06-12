package com.wasabi_neko.nyanVenture.gameObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wasabi_neko.nyanVenture.Setting;

public class SheetData implements Serializable {
    private long songLength;
    private List<BaseNode> nodeList;
    private int index = 0;

    // public SheetData() {
    //     super();
    //     this.nodeList = new ArrayList<>();
    //     this.index = 0;
    // }

    public SheetData(List<BaseNode> _nodeList, long _songLength) {
        super();
        this.nodeList = _nodeList;
        this.songLength = _songLength;
        this.index = 0;
    }

    public long getSongLength() {
        return this.songLength;
    }

    public boolean isEmpty() {
        return this.nodeList.isEmpty();
    }

    public void sort() {
        this.nodeList.sort(Comparators.baseNode_startTime_CMP);
    }

    public void reset() {
        this.index = 0;
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

    // public void tempPrint() {
    //     if ( !this.nodeList.isEmpty() ) {
    //         for (BaseNode baseNode : this.nodeList) {
    //             System.out.println(baseNode.toString());
    //         }
    //     }
    // }
}