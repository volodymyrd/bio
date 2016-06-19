package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.simple.SubStringUtils;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
@Component
public class StringSearchCommander implements CommandMarker {

    private static final java.util.logging.Logger LOGGER = HandlerUtils
            .getLogger(StringSearchCommander.class);

    @CliCommand(value = {"pattern count", "pcount"}, help = "Pattern Count")
    public void patternCount(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"pattern"}, mandatory = false, help = "Searching pattern") String pattern,
            @CliOption(key = {"patternFileName"}, mandatory = false, help = "File name contains pattern") String patternFileName) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return;
        }

        if (isEmpty(pattern) && isEmpty(patternFileName)) {
            LOGGER.severe("You must set either 'pattern' or 'patternFileName' parameter!");

            return;
        }

        String t = null, p = null;

        if (!isEmpty(text))
            t = text;
        else {

        }

        if (!isEmpty(pattern))
            p = pattern;
        else {

        }

        LOGGER.info("Count: " + SubStringUtils.slideWindowPatternCount(t, p));
    }

    private boolean isEmpty(String text) {
        return text == null || text.isEmpty() ? true : false;
    }
}