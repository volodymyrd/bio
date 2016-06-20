package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.simple.BioJavaUtil;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

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
}