package com.gmail.volodymyrdotsenko.javabio.simple.dna;

/**
 * Created by Volodymyr Dotsenko on 7/3/16.
 */
public enum DNANucleotide {
    A, T, C, G;

    public static DNANucleotide valueOf(char n) {
        return DNANucleotide.valueOf(new String(new char[]{n}).toUpperCase());
    }
}