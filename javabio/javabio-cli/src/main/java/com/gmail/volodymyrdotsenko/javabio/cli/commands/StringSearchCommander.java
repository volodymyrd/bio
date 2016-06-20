package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.cli.utils.FileUtils;
import com.gmail.volodymyrdotsenko.javabio.simple.SubStringUtils;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
@Component
public class StringSearchCommander extends BaseCommander {

    private static final java.util.logging.Logger LOGGER = HandlerUtils
            .getLogger(StringSearchCommander.class);

    @CliCommand(value = {"patternCount", "pcount"}, help = "Pattern Count")
    public String patternCount(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"pattern"}, mandatory = false, help = "Searching pattern") String pattern,
            @CliOption(key = {"patternFileName"}, mandatory = false, help = "File name contains pattern") String patternFileName) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        if (isEmpty(pattern) && isEmpty(patternFileName)) {
            LOGGER.severe("You must set either 'pattern' or 'patternFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null, p = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";


        p = extractText(pattern, patternFileName);
        if (isEmpty(p))
            return "";

        return String.valueOf(SubStringUtils.slideWindowPatternCount(t, p));
    }

    private String extractText(String text, String fileName) {
        if (!isEmpty(text))
            return text;
        else {
            try {
                return FileUtils.getStringFromFile(fileName);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());

                return null;
            }
        }
    }
}