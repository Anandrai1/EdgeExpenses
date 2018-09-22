package com.edgefintrack.expense.utils;

import android.os.Environment;

import com.edgefintrack.expense.interfaces.FileGeneratorParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class FileManager {

    public static String getFileName() {
        return "Edge Expenses " + (new Date().toString());
    }

    public static final File generateFile(FileGeneratorParser fileGeneratorParser) {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            File root = new File(Environment.getExternalStorageDirectory(), "EdgeExpenses");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, getFileName());
            try {
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(fileGeneratorParser.generateFileContent());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gpxfile;
        }
        return null;
    }
}

