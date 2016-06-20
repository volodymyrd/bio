package com.gmail.volodymyrdotsenko.javabio.cli.utils;

import java.io.*;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class FileUtils {
    public static String getStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        return getStringFromFile(new FileInputStream(file));
    }

    public static String getStringFromFile(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        }
    }
}