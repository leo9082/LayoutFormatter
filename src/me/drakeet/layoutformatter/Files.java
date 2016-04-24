package me.drakeet.layoutformatter;

import com.intellij.openapi.vfs.VirtualFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 16/4/10 15:48
 */
public class Files {

    public static boolean isXmlFileOrDir(@Nullable VirtualFile file) {
        return file != null && (file.getName().endsWith(".xml") || file.isDirectory());
    }


    public static String getContent(VirtualFile file) {
        final String contents;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
            String currentLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((currentLine = br.readLine()) != null) {
                stringBuilder.append(currentLine);
                stringBuilder.append("\n");
            }
            contents = stringBuilder.toString();
        } catch (IOException e1) {
            return null;
        }
        return contents;
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
