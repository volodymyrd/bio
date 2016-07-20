package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.cli.utils.FileUtils;
import com.gmail.volodymyrdotsenko.javabio.simple.BioJavaUtil;
import com.gmail.volodymyrdotsenko.javabio.simple.SequencesUtil;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
@Component
public class SequencesCommander extends BaseCommander {

    private static final java.util.logging.Logger LOGGER = HandlerUtils
            .getLogger(SequencesCommander.class);

    @CliCommand(value = {"sequences reverse complement", "seqrevcompl"}, help = "Get reverse complement sequence")
    public String reverseComplement(
            @CliOption(key = {"sequence"}, mandatory = false, help = "Sequence") String sequence,
            @CliOption(key = {"sequenceFileName"}, mandatory = false, help = "File name contains sequence")
                    String sequenceFileName,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains reverse complement sequence")
                    String outputFileName) {

        if (isEmpty(sequence) && isEmpty(sequenceFileName)) {
            LOGGER.severe("You must set either 'sequence' or 'sequenceFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(sequence, sequenceFileName);
        if (isEmpty(t))
            return "";

        if (isEmpty(outputFileName))
            return BioJavaUtil.reverseComplement(t);
        else {
            try {
                saveTextFile(outputFileName, BioJavaUtil.reverseComplement(t));

                return "File " + outputFileName + " created!";
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.getMessage());
            }

            return "Error creating a file";
        }
    }

    @CliCommand(value = {"skew sequence"}, help = "Get skew")
    public String skew(
            @CliOption(key = {"sequence"}, mandatory = false, help = "Sequence") String sequence,
            @CliOption(key = {"sequenceFileName"}, mandatory = false, help = "File name contains sequence")
                    String sequenceFileName,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains skew")
                    String outputFileName) {

        if (isEmpty(sequence) && isEmpty(sequenceFileName)) {
            LOGGER.severe("You must set either 'sequence' or 'sequenceFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(sequence, sequenceFileName);
        if (isEmpty(t))
            return "";

        if (isEmpty(outputFileName))
            return Arrays.toString(BioJavaUtil.skew(t)).replace(",", "");
        else {
            try {
                saveTextFile(outputFileName, Arrays.toString(BioJavaUtil.skew(t)).replace(",", ""));

                return "File " + outputFileName + " created!";
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.getMessage());
            }

            return "Error creating a file";
        }
    }

    @CliCommand(value = {"skew min"}, help = "Get skew minimums")
    public String skewMinimums(
            @CliOption(key = {"sequence"}, mandatory = false, help = "Sequence") String sequence,
            @CliOption(key = {"sequenceFileName"}, mandatory = false, help = "File name contains sequence")
                    String sequenceFileName,
            @CliOption(key = {"outputFileName"}, mandatory = false,
                    help = "File name contains skew")
                    String outputFileName) {

        if (isEmpty(sequence) && isEmpty(sequenceFileName)) {
            LOGGER.severe("You must set either 'sequence' or 'sequenceFileName' parameter!");

            return "Wrong input parameters";
        }

        String t = null;

        t = extractText(sequence, sequenceFileName);
        if (isEmpty(t))
            return "";

        if (isEmpty(outputFileName))
            return BioJavaUtil.skewMinimums(t).toString().replace(",", "");
        else {
            try {
                saveTextFile(outputFileName, BioJavaUtil.skewMinimums(t).toString().replace(",", ""));

                return "File " + outputFileName + " created!";
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.getMessage());
            }

            return "Error creating a file";
        }
    }

    @CliCommand(value = {"stringSpelledGenomePathProblem"}, help = "Solve the String Spelled by a Genome Path Problem")
    public String stringSpelledGenomePathProblem(
            @CliOption(key = {"dnasFileName"}, mandatory = true, help = "File contains of collection of strings Dna")
                    String dnasFileName) {
        try {
            List<String> list = FileUtils.getListFromFile(dnasFileName);

            return SequencesUtil.stringSpelledGenomePathProblem(list.toArray(new String[list.size()]));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }

    @CliCommand(value = {"overlapGraph"}, help = "Solve the Overlap Graph Problem")
    public String overlapGraph(
            @CliOption(key = {"kmersFileName"}, mandatory = true, help = "File contains of a collection patterns of k-mers")
                    String kmersFileName,
            @CliOption(key = {"outputFileName"}, mandatory = true,
                    help = "File name contains the overlap graph Overlap(Patterns), in the form of an adjacency list")
                    String outputFileName) {

        try {
            List<String> list = FileUtils.getListFromFile(kmersFileName);

            saveTextFile(outputFileName, SequencesUtil.overlapGraph(list.toArray(new String[list.size()]))
                    .toString(false, true));

            return "File " + outputFileName + " created!";
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        return "Error creating a file";
    }

    @CliCommand(value = {"buildDeBruijnGraphFromString"}, help = "Solve the De Bruijn Graph from a String Problem")
    public String buildDeBruijnGraphFromString(
            @CliOption(key = {"text"}, mandatory = true, help = "string Text") String text,
            @CliOption(key = {"k"}, help = "Length of k-mer", mandatory = true) Integer k,
            @CliOption(key = {"outputFileName"}, mandatory = true, help = "DeBruijnk(Text), in the form of an adjacency list")
                    String outputFileName) {

        try {
            List<String> list = FileUtils.getListFromFile(kmersFileName);

            saveTextFile(outputFileName, SequencesUtil.overlapGraph(list.toArray(new String[list.size()]))
                    .toString(false, true));

            return "File " + outputFileName + " created!";
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        return "Error creating a file";
    }
}