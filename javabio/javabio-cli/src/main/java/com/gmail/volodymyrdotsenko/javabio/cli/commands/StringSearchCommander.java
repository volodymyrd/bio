package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.simple.SubStringUtils;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Map;
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
            @CliOption(key = {"patternFileName"}, mandatory = false, help = "File name contains pattern")
                    String patternFileName,
            @CliOption(key = {"startingPosition"}, mandatory = false, help = "Print starting position", unspecifiedDefaultValue = "false")
                    Boolean startingPosition,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains starting positions")
                    String outputFileName) {

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

        if (startingPosition)
            if (isEmpty(outputFileName))
                return String.valueOf(SubStringUtils.slideWindowPatternCount(t, p)
                        .stream().map(e -> e.toString()).collect(Collectors.joining(" ")));
            else {
                try {
                    saveTextFile(outputFileName, String.valueOf(SubStringUtils.slideWindowPatternCount(t, p)
                            .stream().map(e -> e.toString()).collect(Collectors.joining(" "))));

                    return "File " + outputFileName + " created!";
                } catch (FileNotFoundException e) {
                    LOGGER.severe(e.getMessage());
                }

                return "Error creating a file";
            }
        else
            return String.valueOf(SubStringUtils.slideWindowPatternCount(t, p).size());
    }

    @CliCommand(value = {"frequentWords", "fwords"}, help = "Frequent Words")
    public String frequentWords(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"kmer"}, mandatory = true, help = "k-mer length") Integer kmer,
            @CliOption(key = {"frequency"}, mandatory = true,
                    help = "frequency, if greater or equal 0 will get all with greater this frequency, if less 0 print only with maximum frequency") Integer frequency,
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

        Map<String, Integer> result = SubStringUtils.frequentWords(t, kmer, frequency);

        LOGGER.info("Number of words: " + result.size());

        if (withCount)
            return result.toString();
        else
            return result.keySet().stream().sorted().map(e -> e.toString()).collect(Collectors.joining(" "));
    }

    @CliCommand(value = {"clump"}, help = "Get clumps in genome")
    public String clump(
            @CliOption(key = {"genome"}, mandatory = false, help = "Text") String genome,
            @CliOption(key = {"genomeFileName"}, mandatory = false, help = "File name contains genome") String genomeFileName,
            @CliOption(key = {"k"}, mandatory = true, help = "k-mer length") Integer k,
            @CliOption(key = {"L"}, mandatory = true, help = "k-mer length") Integer L,
            @CliOption(key = {"t"}, mandatory = true, help = "k-mer length") Integer t,
            @CliOption(key = {"withCount"}, mandatory = false, help = "Print result with count",
                    unspecifiedDefaultValue = "false") Boolean withCount) {

        if (isEmpty(genome) && isEmpty(genomeFileName)) {
            LOGGER.severe("You must set either 'genome' or 'genomeFileName' parameter!");

            return "Wrong input parameters";
        }

        String g = null;

        g = extractText(genome, genomeFileName);
        if (isEmpty(g))
            return "";

        long s = System.nanoTime();
        Map<String, Integer> result = SubStringUtils.getClump(g, k, L, t);
        LOGGER.info("Algorithm worked: " + (System.nanoTime() - s) / 1000000.0 + "ms");

        LOGGER.info("Number of clumps: " + result.size());

        if (withCount)
            return result.entrySet().stream()
                    .map(e -> e.toString()).collect(Collectors.joining(" "));
        else
            return result.keySet().stream().map(e -> e.toString()).collect(Collectors.joining(" "));
    }
}