package com.gmail.volodymyrdotsenko.javabio.simple.dna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Dotsenko on 7/3/16.
 */
public class DNAProfileMatrix {
    public static class Builder {
        private final Map<DNANucleotide, Double[]> profile = new HashMap<>();

/*        public Builder add(DNANucleotide nucleotide, List<Double> probabilities) {
            profile.put(nucleotide, probabilities.toArray(new Double[probabilities.size()]));

            return this;
        }*/

        public Builder add(DNANucleotide nucleotide, String probabilitiesSplitBySpace) {
            String[] data = probabilitiesSplitBySpace.split("\\s");
            Double[] probs = new Double[data.length];
            for (int i = 0; i < data.length; i++)
                probs[i] = Double.valueOf(data[i]);

            profile.put(nucleotide, probs);

            return this;
        }

        public Builder add(DNANucleotide nucleotide, Double[] probabilities) {
            profile.put(nucleotide, probabilities);

            return this;
        }

        public DNAProfileMatrix getDNAProfileMatrix() {
            return new DNAProfileMatrix(profile);
        }
    }

    private final Map<DNANucleotide, Double[]> profile;

    public DNAProfileMatrix(Map<DNANucleotide, Double[]> profile) {
        this.profile = profile;
    }

    public double getProbability(int i, DNANucleotide nucleotide) {
        return profile.get(nucleotide)[i];
    }

    @Override
    public String toString() {
        return profile.entrySet().stream()
                .sorted((o1, o2) -> o1.getKey().toString().compareTo(o2.getKey().toString()))
                .map(e -> {
                    String s = e.getKey() + ": ";
                    for (Double v : e.getValue())
                        s += v + ", ";

                    return s.substring(0, s.length() - 2) + " ";
                }).collect(Collectors.joining());
    }
}