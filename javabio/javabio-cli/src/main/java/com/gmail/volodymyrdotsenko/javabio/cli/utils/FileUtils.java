package com.gmail.volodymyrdotsenko.javabio.cli.utils;

import org.springframework.shell.support.logging.HandlerUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class FileUtils {
    private static final java.util.logging.Logger LOGGER = HandlerUtils
            .getLogger(FileUtils.class);

    public static String getStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        return getStringFromFile(new FileInputStream(file));
    }

    public static String getStringFromFile(InputStream is) throws IOException {
        long s = System.nanoTime();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }

            LOGGER.info("File has been read for " + (System.nanoTime() - s) / 1000000.0 + "ms");

            return sb.toString();
        }
    }

    public static List<String> getListFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        return getListFromFile(new FileInputStream(file));
    }

    public static List<String> getListFromFile(InputStream is) throws IOException {
        long s = System.nanoTime();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            List<String> list = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                list.add(line.trim());
            }

            LOGGER.info("File has been read for " + (System.nanoTime() - s) / 1000000.0 + "ms");

            return list;
        }
    }
}