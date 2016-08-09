package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.EulerianDigraph;
import com.gmail.volodymyrdotsenko.javabio.cli.utils.FileUtils;
import com.gmail.volodymyrdotsenko.javabio.simple.BioJavaUtil;
import com.gmail.volodymyrdotsenko.javabio.simple.KDmer;
import com.gmail.volodymyrdotsenko.javabio.simple.SequencesUtil;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            saveTextFile(outputFileName, SequencesUtil.buildBruijnGraph(text, k)
                    .toString(false, true));

            return "File " + outputFileName + " created!";
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        return "Error creating a file";
    }

    @CliCommand(value = {"deBruijnGraphFromKmers"}, help = "Construct the de Bruijn graph from a set of k-mers")
    public String deBruijnGraphFromKmers(
            @CliOption(key = {"kmersFileName"}, mandatory = true, help = "File contains of a collection patterns of k-mers")
                    String kmersFileName,
            @CliOption(key = {"outputFileName"}, mandatory = true,
                    help = "The adjacency list of the de Bruijn graph DeBruijn(Patterns)")
                    String outputFileName) {

        try {
            List<String> list = FileUtils.getListFromFile(kmersFileName);

            saveTextFile(outputFileName, SequencesUtil.buildBruijnGraph(list.toArray(new String[list.size()]))
                    .toString(false, true));

            return "File " + outputFileName + " created!";
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        return "Error creating a file";
    }

    @CliCommand(value = {"findEulerianCycle"}, help = "Solve the Eulerian Cycle Problem")
    public String findEulerianCycle(
            @CliOption(key = {"adjacencyListFileName"}, mandatory = true, help = "File contains the adjacency list of an Eulerian directed graph")
                    String adjacencyListFileName,
            @CliOption(key = {"outputFileName"}, mandatory = true, help = "An Eulerian cycle in this graph")
                    String outputFileName,
            @CliOption(key = {"startPoint"}, help = "Index of start point", mandatory = false) Integer startPoint) {

        try {
            List<String> list = FileUtils.getListFromFile(adjacencyListFileName);

            EulerianDigraph<String> eulerianDigraph = new EulerianDigraph();
            eulerianDigraph.buildFromAdjacencyList(list);

            StringBuilder output = new StringBuilder("");
            for (Integer v : startPoint == null ? eulerianDigraph.findPath() : eulerianDigraph.findCycle(startPoint)) {
                output.append(v);
                output.append("->");
            }
            output.delete(output.length() - 2, output.length());
            saveTextFile(outputFileName, output.toString());

            return "File " + outputFileName + " created!";
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        return "Error creating a file";
    }

    @CliCommand(value = {"stringReconstruction"}, help = "Solve the String Reconstruction Problem")
    public String stringReconstruction(
            @CliOption(key = {"kmersFileName"}, mandatory = true, help = "File contains list of k-mers Patterns")
                    String kmersFileName) {
        try {
            List<String> list = FileUtils.getListFromFile(kmersFileName);

            return SequencesUtil.stringReconstruction(list.toArray(new String[list.size()]));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }

    @CliCommand(value = {"stringReconstructionFromReadPairs"}, help = "Solve the String Reconstruction from Read-Pairs Problem")
    public String stringReconstructionFromReadPairs(
            @CliOption(key = {"kmersFileName"}, mandatory = true, help = "File contains a collection of paired k-mers PairedReads")
                    String kmersFileName,
            @CliOption(key = {"k"}, help = "Length of k-mer", mandatory = true) Integer k,
            @CliOption(key = {"d"}, help = "Distance", mandatory = true) Integer d) {
        try {
            List<String> list = FileUtils.getListFromFile(kmersFileName);

            List<KDmer> kDmers = list.stream().map(e -> {
                String[] patterns = e.split("\\|");
                return new KDmer(patterns[0], patterns[1]);
            }).collect(Collectors.toList());

            return SequencesUtil.stringReconstruction(kDmers.toArray(new KDmer[kDmers.size()]), k, d);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());

            return "";
        }
    }
}