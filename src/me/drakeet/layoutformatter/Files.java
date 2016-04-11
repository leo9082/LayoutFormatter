package me.drakeet.layoutformatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 16/4/10 15:48
 */
public class Files {

    public static String getContent(File file) {
        ArrayList<String> lines = getContentLines(file);
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }


    public static ArrayList<String> getContentLines(File file) {
        ArrayList<String> lines = new ArrayList<String>();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return lines;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
