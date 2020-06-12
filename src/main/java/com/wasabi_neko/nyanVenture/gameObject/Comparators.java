package com.wasabi_neko.nyanVenture.gameObject;

import java.util.Comparator;

public class Comparators {
    private Comparators() {
        // not allow making an instnace
    }

    public static final Comparator<TapNode> tapNode_tapTime_CMP = new Comparator<TapNode>() {
        @Override
        public int compare(TapNode bigger, TapNode smaller) {
            int returns = 0;
            if (bigger.baseNode.tapTime > smaller.baseNode.tapTime) {
                returns = 1;
            } else if (bigger.baseNode.tapTime < smaller.baseNode.tapTime) {
                returns = -1;
            }

            return returns;
        }
    };

    public static final Comparator<BaseNode> baseNode_startTime_CMP = new Comparator<BaseNode>() {
        @Override
        public int compare(BaseNode bigger, BaseNode smaller) {
            int returns = 0;
            if (bigger.startTime > smaller.startTime) {
                returns = 1;
            } else if (bigger.startTime < smaller.startTime) {
                returns = -1;
            }

            return returns;
        }
    };
}