package com.gmail.volodymyrdotsenko.javabio.simple;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

import java.util.*;
import java.util.stream.Collectors;

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

    public static Set<Integer> skewMinimums(String sequence) {
        return skew(sequence, new int[sequence.length() + 1]);
    }

    public static int[] skew(String sequence) {
        int[] skewArray = new int[sequence.length() + 1];
        skew(sequence, skewArray);
        return skewArray;
    }

    public static Set<Integer> skew(String sequence, int[] skewArray) {

        try {
            List<NucleotideCompound> list = new DNASequence(sequence).getAsList();

            boolean decrease = false;
            int min = 0;
            Map<Integer, Integer> mins = new TreeMap<>();

            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getShortName()) {
                    case "C":
                        skewArray[i + 1] = skewArray[i] - 1;
                        break;
                    case "G":
                        skewArray[i + 1] = skewArray[i] + 1;
                        break;
                    default:
                        skewArray[i + 1] = skewArray[i];
                }

                if (skewArray[i + 1] < skewArray[i]) {
                    decrease = true;
                    if (min > skewArray[i + 1])
                        min = skewArray[i + 1];

                } else if (skewArray[i + 1] > skewArray[i]) {
                    if (decrease && skewArray[i] <= min) {
                        mins.put(i, skewArray[i]);
                    }

                    decrease = false;
                } else {
                    if (decrease && skewArray[i] <= min) {
                        mins.put(i, skewArray[i]);
                    }
                }
            }
            final int m = min;

            //return mins;
            return new TreeSet<>(mins.entrySet().stream()
                    .filter(e -> e.getValue() <= m)
                    .map(e -> e.getKey()).collect(Collectors.toSet()));
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}