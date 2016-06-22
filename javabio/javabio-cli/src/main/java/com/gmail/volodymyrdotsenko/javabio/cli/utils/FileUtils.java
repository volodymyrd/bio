package com.gmail.volodymyrdotsenko.javabio.cli.utils;

import com.gmail.volodymyrdotsenko.javabio.cli.commands.BaseCommander;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.*;

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
                sb.append(line);
            }

            LOGGER.info("File has been read for " + (System.nanoTime() - s) / 1000000.0 + "ms");

            return sb.toString();
        }
    }
}