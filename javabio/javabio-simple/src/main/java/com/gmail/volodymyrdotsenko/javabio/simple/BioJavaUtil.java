package com.gmail.volodymyrdotsenko.javabio.simple;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class BioJavaUtil {

    public static String reverseComplement(String sequence) {
        try {
            return new DNASequence(sequence).getReverseComplement().getSequenceAsString();
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}