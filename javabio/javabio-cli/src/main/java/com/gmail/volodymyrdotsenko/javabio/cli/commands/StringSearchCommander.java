package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.simple.SubStringUtils;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    @CliCommand(value = {"frequentWords", "fwords"}, help = "Frequent Words")
    public String frequentWords(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"l"}, mandatory = true, help = "k-mer length") Integer length,
            @CliOption(key = {"withCount"}, mandatory = false, help = "Print result with count",
                    unspecifiedDefaultValue = "false") Boolean withCount) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";

        if (withCount)
            return SubStringUtils.frequentWords(t, length).toString();
        else
            return SubStringUtils.frequentWords(t, length).keySet()
                    .stream().sorted()
                    .map(e -> e.toString())
                    .collect(Collectors.joining(" "));
    }
}