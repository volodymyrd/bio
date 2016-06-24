package com.gmail.volodymyrdotsenko.javabio.simple;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

import java.util.List;

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

    public static int[] skew(String sequence) {
        try {
            List<NucleotideCompound> list = new DNASequence(sequence).getAsList();
            int[] skew = new int[list.size() + 1];
            skew[0] = 0;
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getShortName()) {
                    case "C":
                        skew[i + 1] = skew[i] - 1;
                        break;
                    case "G":
                        skew[i + 1] = skew[i] + 1;
                        break;
                    default:
                        skew[i + 1] = skew[i];
                }

            }

            return skew;
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}