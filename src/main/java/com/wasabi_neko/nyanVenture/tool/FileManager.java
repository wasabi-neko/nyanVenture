package com.wasabi_neko.nyanVenture.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.print.event.PrintJobListener;

import com.wasabi_neko.nyanVenture.gameObject.Sheet;
import com.wasabi_neko.nyanVenture.gameObject.SheetData;

public class FileManager {
    static final String SHEETDATA_NAME = "data/sheetData%d";
    static final String MUSIC_NAME = "data/music%d";

    public static SheetData getSheetData(int index) throws IOException {
        String fileName = String.format(SHEETDATA_NAME, index);
        SheetData data = new SheetData();

        try (
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        ) {
            data = (SheetData) objectIn.readObject();
        } catch (Exception e) {
            data = null;
            System.out.println(e);  //TODO: change print format?
        }

        return data;
    }

    static void overWriteSheetData(SheetData data, int index) throws IOException {
        String fileName = String.format(SHEETDATA_NAME, index);
        File file = new File(fileName);

        if (!file.exists()) {
            // TODO: error handling
            System.out.println("#ERROR: file not exists");
            return;
        } else {
            writeData(data, file);
        }
    }

    static void newSheetData(SheetData data ,int index) throws IOException {
        String fileName = String.format(SHEETDATA_NAME, index);
        File file = new File(fileName);

        if (file.exists()) {
            // TODO: error handling
            System.out.println("#ERROR: file already exists");
            return;
        } else {
            writeData(data, file);
        }
    }

    static void  writeData(SheetData data, File file) throws IOException {
        try (
            FileOutputStream fileout = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileout);
        ) {
            objectOut.writeObject(data);
        } catch (IOException e) {
            System.out.println("wwww");
            System.out.println(e);  //TODO: change print format?
        } finally {
            System.out.println(file.getAbsolutePath());
        }
    }
}