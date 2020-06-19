package com.wasabi_neko.nyanVenture.tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.stream.FileImageInputStream;

import com.wasabi_neko.nyanVenture.gameObject.BaseNode;
import com.wasabi_neko.nyanVenture.gameObject.Comparators;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;

public class TempMain {
    static ArrayList<BaseNode> tempList;

    public static void main(String[] args) throws Exception {
        
        // try {
        //     File myObj = new File("javaTestFile.txt");
        //     if (myObj.createNewFile()) {
        //       System.out.println("File created: " + myObj.getName());
        //     } else {
        //       System.out.println("File already exists.");
        //     }
        //   } catch (IOException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        //   }

        // SheetData data = FileManager.getSheetData(5);

        tempList = new ArrayList<>();


        tempList.sort(Comparators.baseNode_startTime_CMP);
        for (BaseNode baseNode : tempList) {
            System.out.println(baseNode);
        }

        SheetData data = new SheetData(tempList, 81000);

        try {
            FileManager.newSheetData(data, 0);
            // FileManager.overWriteSheetData(data, 0);
        } catch(IOException e) {
            System.out.println("in main");
            System.out.println(e);
        }
    }

    public static void addNode(int startTime, int delta, int tempType) {
        long t1 = startTime, t2 = t1 + delta;
        short u1 = 0;
        short[] type;

        if (tempType == 0) {
            short[] temp = {-1, -1, 1, 0};
            type = temp;
        } else {
            short[] temp = {-1, -1, 0, 1};
            type = temp;
        }
        BaseNode temp = new BaseNode(t1, t2, type, u1, false);

        tempList.add(temp);
    }
}