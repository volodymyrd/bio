package com.gmail.volodymyrdotsenko.javabio.simple.dna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holder for motifs
 * <p>
 * Created by Volodymyr Dotsenko on 04.07.16.
 */
public class MotifsHolder {
    private static class NucleotidesCount {
        private final Map<DNANucleotide, Long> counts = new HashMap<>();

        void add(DNANucleotide nucleotide) {
            Long l = counts.get(nucleotide);

            if (l == null)
                l = 1L;
            else
                l++;

            counts.put(nucleotide, l);
        }
    }

    private final List<String> motifs;
    private final NucleotidesCount[] nucleotidesCounts;
    private final int k;

    public MotifsHolder(int k) {
        this(k, null);
    }

    public MotifsHolder(int k, List<String> motifs) {
        this.k = k;
        this.nucleotidesCounts = new NucleotidesCount[k];
        this.motifs = new ArrayList<>();

        if (motifs != null)
            motifs.forEach(this::add);
    }

    public MotifsHolder add(String motif) {
        if (motif.length() != k)
            throw new IllegalArgumentException("Length of motif must be equal to " + k);

        motifs.add(motif);

        for (int i = 0; i < k; i++) {
            DNANucleotide nucleotide = DNANucleotide.valueOf(motif.charAt(i));
            if (nucleotidesCounts[i] == null)
                nucleotidesCounts[i] = new NucleotidesCount();

            nucleotidesCounts[i].add(nucleotide);
        }

        return this;
    }

    private int getWidth() {
        return k;
    }

    private int getHeight() {
        return motifs.size();
    }

    public List<String> getMotifs() {
        return motifs;
    }

    public DNAProfileMatrix getProfileMatrix(int pseudocountValue) {
        Map<DNANucleotide, Double[]> profile = new HashMap<>();
        for (DNANucleotide n : DNANucleotide.values()) {
            profile.put(n, new Double[getWidth()]);
        }

        for (int i = 0; i < nucleotidesCounts.length; i++) {
            for (Map.Entry<DNANucleotide, Long> e : nucleotidesCounts[i].counts.entrySet()) {
                Double[] probs = profile.get(e.getKey());
                if (probs == null) {
                    probs = new Double[getWidth()];
                    probs[i] = new Double(e.getValue());
                } else {
                    if (probs[i] == null)
                        probs[i] = new Double(e.getValue());
                    else
                        probs[i] += e.getValue().doubleValue();
                }

                profile.put(e.getKey(), probs);
            }
        }

        profile.values().forEach(v -> {
            for (int i = 0; i < v.length; i++) {
                if (v[i] == null)
                    v[i] = 0d;

                v[i] += pseudocountValue;
                v[i] /= getHeight();
            }
        });

        return new DNAProfileMatrix(profile);
    }

    public long score() {
        long score = 0;
        for (NucleotidesCount nucleotidesCount : nucleotidesCounts) {
            if (nucleotidesCount == null)
                return Long.MAX_VALUE;

            score += (getHeight() - nucleotidesCount.counts.values().stream().max(Long::compare).get());
        }

        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotifsHolder that = (MotifsHolder) o;

        return motifs.equals(that.motifs);

    }

    @Override
    public int hashCode() {
        return motifs.hashCode();
    }
}