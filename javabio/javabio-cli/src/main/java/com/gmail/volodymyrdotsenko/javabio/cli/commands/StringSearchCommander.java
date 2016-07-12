package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.cli.utils.FileUtils;
import com.gmail.volodymyrdotsenko.javabio.simple.SubStringUtils;
import com.gmail.volodymyrdotsenko.javabio.simple.dna.MotifsHolder;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
                    unspecifiedDefaultValue = "false") Boolean withCount,
            @CliOption(key = {"d"}, mandatory = false,
                    unspecifiedDefaultValue = "0", help = "Number of mismatches") Integer d,
            @CliOption(key = {"complement"}, mandatory = false,
                    unspecifiedDefaultValue = "0", help = "With Reverse Complements") boolean complement) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";

        if (d == 0) {
            Map<String, Integer> result = SubStringUtils.frequentWords(t, kmer, frequency, 0);

            LOGGER.info("Number of words: " + result.size());

            if (withCount)
                return result.toString();
            else
                return result.keySet().stream().sorted().map(e -> e.toString()).collect(Collectors.joining(" "));
        } else {
            return SubStringUtils.frequentWordsWithMismatches(t, kmer, d, complement).toString();
        }
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
        Map<String, Integer> result = SubStringUtils.getClump_1(g, k, L, t, withCount);
        LOGGER.info("Algorithm worked: " + (System.nanoTime() - s) / 1000000.0 + "ms");

        LOGGER.info("Number of clumps: " + result.size());

        if (withCount)
            return result.entrySet().stream()
                    .map(e -> e.toString()).collect(Collectors.joining(" "));
        else
            return result.keySet().stream().map(e -> e.toString()).collect(Collectors.joining(" "));
    }

    @CliCommand(value = {"hammingDistance"}, help = "Calculate Hamming Distance")
    public int hammingDistance(
            @CliOption(key = {"text1"}, help = "Text1") String text1,
            @CliOption(key = {"text2"}, help = "Text2") String text2) {
        return SubStringUtils.hammingDistance(text1, text2);
    }

    @CliCommand(value = {"approximatePatternCount"}, help = "Find all approximate occurrences of a pattern in a string")
    public String approximatePatternCount(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"pattern"}, help = "Pattern") String pattern,
            @CliOption(key = {"d"}, help = "Number of mismatches") Integer d,
            @CliOption(key = {"count"}, mandatory = false, unspecifiedDefaultValue = "false",
                    help = "Count matching patterns - true or print matching patterns - false") boolean count) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";

        if (count)
            return String.valueOf(SubStringUtils.approximatePatternCount(t, pattern, d));
        else
            return SubStringUtils.approximatePatternCountList(t, pattern, d)
                    .stream().map(e -> e.toString()).collect(Collectors.joining(" "));
    }

    @CliCommand(value = {"composition"}, help = "Find all k-mer substrings of Text (including repeated k-mers)")
    public String composition(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"k"}, help = "Length of k-mer") Integer k,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains output") String outputFileName) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";

        if (isEmpty(outputFileName))
            return SubStringUtils.getKmers(t, k)
                    .stream().sorted().map(e -> e.toString()).collect(Collectors.joining("\n\r"));
        else {
            try {
                saveTextFile(outputFileName, String.valueOf(SubStringUtils.getKmers(t, k)
                        .stream().sorted().map(e -> e.toString()).collect(Collectors.joining("\r"))));

                return "File " + outputFileName + " created!";
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.getMessage());
            }

            return "Error creating a file";
        }
    }

    @CliCommand(value = {"neighbors"}, help = "Find the d-neighborhood of a string")
    public String neighbors(
            @CliOption(key = {"text"}, mandatory = false, help = "Text") String text,
            @CliOption(key = {"textFileName"}, mandatory = false, help = "File name contains text") String textFileName,
            @CliOption(key = {"d"}, mandatory = true, help = "Distance") Integer d,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains starting positions")
                    String outputFileName) {

        if (isEmpty(text) && isEmpty(textFileName)) {
            LOGGER.severe("You must set either 'text' or 'textFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null, p = null;

        t = extractText(text, textFileName);
        if (isEmpty(t))
            return "";

        if (isEmpty(outputFileName))
            return String.valueOf(SubStringUtils.dNeighbors(t, d)
                    .stream().map(e -> e.toString()).collect(Collectors.joining("\n\r")));
        else {
            try {
                saveTextFile(outputFileName, String.valueOf(SubStringUtils.dNeighbors(t, d)
                        .stream().map(e -> e.toString()).collect(Collectors.joining("\r"))));

                return "File " + outputFileName + " created!";
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.getMessage());
            }

            return "Error creating a file";
        }

    }

    @CliCommand(value = {"medianString"}, help = "Median String Problem")
    public String medianString(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName,
            @CliOption(key = {"k"}, mandatory = true, help = "An integer k") Integer k) {

        String t = null, p = null;

        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);

            return SubStringUtils.medianString(list, k)
                    .stream().map(e -> e).collect(Collectors.joining(" "));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }

    @CliCommand(value = {"greedyMotifSearch"}, help = "Call Greedy Motif Search")
    public String greedyMotifSearch(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName,
            @CliOption(key = {"k"}, mandatory = true, help = "An integer k") Integer k,
            @CliOption(key = {"pseudocounts"}, mandatory = false, help = "GreedyMotifSearch with pseudocounts",
                    unspecifiedDefaultValue = "false") Boolean pseudocounts) {

        String t = null, p = null;

        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);

            return SubStringUtils.greedyMotifSearch(list, k, pseudocounts)
                    .stream().map(e -> e).collect(Collectors.joining("\n\r"));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }

    @CliCommand(value = {"distanceBetweenPatternAndStrings"},
            help = "distances between Pattern and each string in Dna = {Dna1, ..., Dnat}")
    public Long distanceBetweenPatternAndStrings(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName,
            @CliOption(key = {"pattern"}, mandatory = true, help = "pattern") String pattern) {

        String t = null, p = null;

        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);

            return SubStringUtils.distanceBetweenPatternAndStrings(pattern, list);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return -1L;
        }
    }

    @CliCommand(value = {"randomizedMotifSearch"}, help = "Call Randomized Motif Search")
    public String randomizedMotifSearch(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName,
            @CliOption(key = {"k"}, mandatory = true, help = "An integer k") Integer k,
            @CliOption(key = {"step"}, mandatory = true, help = "Number of steps") Integer step,
            @CliOption(key = {"attempts"}, mandatory = true, help = "Number of attempts") Integer attempts) {

        String t = null, p = null;

        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);

            return SubStringUtils.randomizedMotifSearch(list, k, step, attempts)
                    .stream().map(e -> e).collect(Collectors.joining("\n\r"));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }

    @CliCommand(value = {"gibbsSampler"}, help = "Call Gibbs Sampler")
    public String gibbsSampler(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName,
            @CliOption(key = {"k"}, mandatory = true, help = "An integer k") Integer k,
            @CliOption(key = {"step"}, mandatory = true, help = "Number of steps") Integer step,
            @CliOption(key = {"attempts"}, mandatory = true, help = "Number of attempts") Integer attempts) {

        String t = null, p = null;

        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);
            MotifsHolder motifsHolder = SubStringUtils.gibbsSampler(list, k, step, attempts);
            return "Score: " + motifsHolder.score() + "\n\r" + "Motifs:\n\r" +
                    motifsHolder.getMotifs()
                            .stream().map(e -> e).collect(Collectors.joining("\n\r"));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }
}