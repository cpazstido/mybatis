package com.cf.sitemap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    /**
     * @param name
     * @param text
     * @throws IOException
     */
    public static void write(String name, String text) throws IOException {
        File file = new File(name);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append(text);
        bw.flush();
        bw.close();
        fw.close();
    }

    /**
     * @param name
     * @param text
     * @throws IOException
     */
    public static void writeln(String name, String text) throws IOException {
        write(name, text + "\r\n");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String text = "IT小奋斗，www.what21.com";
        try {
            writeln("c:\\001.txt",text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
