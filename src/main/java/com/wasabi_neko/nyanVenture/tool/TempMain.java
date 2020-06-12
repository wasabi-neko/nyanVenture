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

    public static void main(String[] args) {
        
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


        tempList = new ArrayList<>();
        
        addNode(2000, 0);
        addNode(3000, 0);
        addNode(3500, 0);
        addNode(4000, 0);
        addNode(4200, 1);
        addNode(4300, 0);
        addNode(5000, 1);
        addNode(5500, 1);
        addNode(6000, 0);
        addNode(6500, 0);
        addNode(7000, 1);
        addNode(9000, 1);
        addNode(8000, 0);
        addNode(9500, 1);

        tempList.sort(Comparators.baseNode_startTime_CMP);
        for (BaseNode baseNode : tempList) {
            System.out.println(baseNode);
        }

        SheetData data = new SheetData(tempList, 11000);

        try {
            // FileManager.newSheetData(data, 0);
            FileManager.overWriteSheetData(data, 0);
        } catch(IOException e) {
            System.out.println("in main");
            System.out.println(e);
        }
    }

    public static void addNode(int startTime, int type) {
        long t1 = startTime, t2 = t1 + 2000;
        short u1 = 0;
        BaseNode temp = new BaseNode(t1, t2, (short)type, u1);

        tempList.add(temp);
    }
}