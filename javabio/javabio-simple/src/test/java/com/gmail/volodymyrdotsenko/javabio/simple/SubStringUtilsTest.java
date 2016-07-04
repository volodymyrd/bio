package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.simple.dna.DNANucleotide;
import com.gmail.volodymyrdotsenko.javabio.simple.dna.DNAProfileMatrix;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/19/16.
 */
public class SubStringUtilsTest {

    @Test
    public void testSlideWindowPatternCountSize() {
        assertEquals(3, SubStringUtils.slideWindowPatternCount("ACAACTATGCATACTATCGGGAACTATCCT", "ACTAT").size());
    }

    @Test
    public void testSlideWindowPatternCount() {
        assertEquals("[1, 3, 9]", SubStringUtils.slideWindowPatternCount("GATATATGCATATACTT", "ATAT").toString());
    }

    @Test
    public void testFrequentWords() {
        assertEquals("{CATG=3, GCAT=3}",
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, -1, 0).toString());
    }

    @Test
    public void testAllFrequentWords() {
        assertEquals("{ATGA=2, TGCA=2, CATG=3, GCAT=3}",
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 1, 0).toString());
    }

    @Test
    public void testGetClump() {
        assertEquals("{GAAGA=16, CGACA=28}",
                SubStringUtils.getClump("CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA",
                        5, 50, 4).toString());
    }

    @Test
    public void testGetClump2() {
        assertEquals("{AC=6, CG=2, GA=12}",
                SubStringUtils.getClump("CGGACTCGACAGATGTG",
                        2, 10, 2).toString());
    }

    @Test
    public void testGetClump_1() {
        assertEquals("{GAAGA=16, CGACA=28}",
                SubStringUtils.getClump_1("CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA",
                        5, 50, 4, true).toString());
    }

    @Test
    public void testGetClump_1_2() {
        assertEquals("{AC=6, CG=2, GA=12}",
                SubStringUtils.getClump_1("CGGACTCGACAGATGTG",
                        2, 10, 2, true).toString());
    }

    @Test
    public void testHammingDistance() {
        assertEquals(3, SubStringUtils.hammingDistance("GGGCCGTTGGT", "GGACCGTTGAC"));
    }

    @Test
    public void testApproximatePatternMatchingProblem() {
        assertEquals("[6, 7, 26, 27]", SubStringUtils
                .approximatePatternCountList("CGCCCGAATCCAGAACGCATTCCCATATTTCGGGACCACTGGCCTCCACGGTACGGACGTCAATCAAAT",
                        "ATTCTGGA", 3).toString());
    }

    @Test
    public void testApproximatePatternMatchingProblemCount() {
        assertEquals(11L, SubStringUtils
                .approximatePatternCount("AACAAGCTGATAAACATTTAAAGAG",
                        "AAAAA", 2));
    }

    @Test
    public void testGetFrequentWordsMapMismatch() {
        assertEquals("{ATGC=5, GATG=5, ATGT=5}",
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, -1, 1).toString());
    }

    @Test
    public void testPermutations() {
        assertEquals("[AA, AT, TA, TT]",
                SubStringUtils.permutationsWithRepetitions(2, new char[]{'A', 'T'}).toString());
    }

    @Test
    public void testPermutations2() {
        assertEquals("[AAA, AAT, ATA, ATT, TAA, TAT, TTA, TTT]",
                SubStringUtils.permutationsWithRepetitions(3, new char[]{'A', 'T'}).toString());
    }

    @Test
    public void testFrequentWordsWithMismatchesShouldIncludesKmersThatNotAppearInText() {
        assertEquals("AA AC AT AG GA TA CA",
                SubStringUtils.frequentWordsWithMismatches("AAAAAAAAAA", 2, 1, false));

        assertEquals("AT TA",
                SubStringUtils.frequentWordsWithMismatches("AAAAAAAAAA", 2, 1, true));
    }

    @Test
    public void testFrequentWordsWithMismatches() {
        assertEquals("ATGC GATG ATGT",
                SubStringUtils.frequentWordsWithMismatches("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 1, false));
    }

    @Test
    public void testFrequentWordsWithMismatchesShouldNotFindingPatternsInTheReverseComplementText() {
        assertEquals("GGTA",
                SubStringUtils.frequentWordsWithMismatches("AATTAATTGGTAGGTAGGTA", 4, 0, false));

        assertEquals("AATT",
                SubStringUtils.frequentWordsWithMismatches("AATTAATTGGTAGGTAGGTA", 4, 0, true));
    }

    @Test
    public void testFrequentWordsWithMismatchesComplements() {
        assertEquals("ACAT ATGT",
                SubStringUtils.frequentWordsWithMismatches("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 1, true));
    }

    @Test
    public void testDNeighborhoods() {
        assertEquals("[ACC, AGG, ACT, ATG, ACG, CCG, AAG, TCG, GCG, ACA]",
                SubStringUtils.dNeighbors("ACG", 1).toString());
    }

    @Test
    public void testMotifEnumerationBruteForce() {
        assertEquals("[ATT, TTT, GTT, ATA]", SubStringUtils.motifEnumerationBruteForce(
                Stream.of("ATTTGGC", "TGCCTTA", "CGGTATC", "GAAAATT")
                        .collect(Collectors.toList()), 3, 1).toString());
    }

    @Test
    public void testMotifEnumerationBruteForceOffByOneError() {
        assertEquals("[CGT, ACG]", SubStringUtils.motifEnumerationBruteForce(
                Stream.of("ACGT", "ACGT", "ACGT")
                        .collect(Collectors.toList()), 3, 0).toString());
    }

    @Test
    public void testMotifEnumerationBruteForceDgreater0() {
        assertEquals("[AAA, CAA, AAC, AAT, TAA, GAA, AAG, AGA, ATA, ACA]", SubStringUtils.motifEnumerationBruteForce(
                Stream.of("AAAAA", "AAAAA", "AAAAA")
                        .collect(Collectors.toList()), 3, 1).toString());
    }

    @Test
    public void testMotifsWithMinHammingDistance() {
        Map<String, Long> motifs = new HashMap<>();
        assertEquals(3, SubStringUtils.motifsWithMinHammingDistance("GATTCTCA", "GCAAAGACGCTGACCAA", motifs));
        assertEquals("[GACGCTGA]",
                motifs.entrySet().stream().filter(e -> e.getValue() <= 3).map(e -> {
                    return e.getKey();
                }).collect(Collectors.toSet()).toString());
    }

    @Test
    public void testMedianString() {
        assertEquals("[GAC]", SubStringUtils.medianString(
                Stream.of("AAATTGACGCAT", "GACGACCACGTT", "CGTCAGCGCCTG", "GCTGAGCACCGG", "AGTTCGGGACAG")
                        .collect(Collectors.toList()), 3).toString());
    }

    @Test
    public void testMedianStringExtra() {
        assertEquals("[CGGCGA]", SubStringUtils.medianString(
                Stream.of("TGATGATAACGTGACGGGACTCAGCGGCGATGAAGGATGAGT",
                        "CAGCGACAGACAATTTCAATAATATCCGCGGTAAGCGGCGTA",
                        "TGCAGAGGTTGGTAACGCCGGCGACTCGGAGAGCTTTTCGCT",
                        "TTTGTCATGAACTCAGATACCATAGAGCACCGGCGAGACTCA",
                        "ACTGGGACTTCACATTAGGTTGAACCGCGAGCCAGGTGGGTG",
                        "TTGCGGACGGGATACTCAATAACTAAGGTAGTTCAGCTGCGA",
                        "TGGGAGGACACACATTTTCTTACCTCTTCCCAGCGAGATGGC",
                        "GAAAAAACCTATAAAGTCCACTCTTTGCGGCGGCGAGCCATA",
                        "CCACGTCCGTTACTCCGTCGCCGTCAGCGATAATGGGATGAG",
                        "CCAAAGCTGCGAAATAACCATACTCTGCTCAGGAGCCCGATG")
                        .collect(Collectors.toList()), 6).toString());
    }

    @Test
    public void testFindProfileMostProbableKmer() {
        DNAProfileMatrix.Builder builder = new DNAProfileMatrix.Builder();
        builder.add(DNANucleotide.A, new Double[]{0.2, 0.2, 0.3, 0.2, 0.3});
        builder.add(DNANucleotide.C, new Double[]{0.4, 0.3, 0.1, 0.5, 0.1});
        builder.add(DNANucleotide.G, new Double[]{0.3, 0.3, 0.5, 0.2, 0.4});
        builder.add(DNANucleotide.T, new Double[]{0.1, 0.2, 0.1, 0.1, 0.2});

        assertEquals("CCGAG",
                SubStringUtils.findProfileMostProbableKmer("ACCTGTTTATTGCCTAAGTTCCGAACAAACCCAATATAGCCCGAGGGCCT",
                        5, builder.getDNAProfileMatrix()));
    }

    @Test
    public void testFindProfileMostProbableKmerCheckBeginning() {
        DNAProfileMatrix.Builder builder = new DNAProfileMatrix.Builder();
        builder.add(DNANucleotide.A, new Double[]{0.7, 0.2, 0.1, 0.5, 0.4, 0.3, 0.2, 0.1});
        builder.add(DNANucleotide.C, new Double[]{0.2, 0.2, 0.5, 0.4, 0.2, 0.3, 0.1, 0.6});
        builder.add(DNANucleotide.G, new Double[]{0.1, 0.3, 0.2, 0.1, 0.2, 0.1, 0.4, 0.2});
        builder.add(DNANucleotide.T, new Double[]{0.0, 0.3, 0.2, 0.0, 0.2, 0.3, 0.3, 0.1});

        assertEquals("AGCAGCTT",
                SubStringUtils.findProfileMostProbableKmer("AGCAGCTTTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATCTGAACTGGTTACCTGCCGTGAGTAAAT",
                        8, builder.getDNAProfileMatrix()));
    }

    @Test
    public void testFindProfileMostProbableKmerCheckEnding() {
        DNAProfileMatrix.Builder builder = new DNAProfileMatrix.Builder();
        builder.add(DNANucleotide.A, "0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.1 0.2 0.3 0.4 0.5");
        builder.add(DNANucleotide.C, "0.3 0.2 0.1 0.1 0.2 0.1 0.1 0.4 0.3 0.2 0.2 0.1");
        builder.add(DNANucleotide.G, "0.2 0.1 0.4 0.3 0.1 0.1 0.1 0.3 0.1 0.1 0.2 0.1");
        builder.add(DNANucleotide.T, "0.3 0.4 0.1 0.1 0.1 0.1 0.0 0.2 0.4 0.4 0.2 0.3");

        assertEquals("AAGCAGAGTTTA",
                SubStringUtils.findProfileMostProbableKmer("TTACCATGGGACCGCTGACTGATTTCTGGCGTCAGCGTGATGCTGGTGTGGATGACATTCCGGTGCGCTTTGTAAGCAGAGTTTA",
                        12, builder.getDNAProfileMatrix()));
    }

    @Test
    public void testFindProfileMostProbableKmer1() {
        DNAProfileMatrix.Builder builder = new DNAProfileMatrix.Builder();
        builder.add(DNANucleotide.A, "0.258 0.288 0.242 0.212 0.258 0.227 0.258 0.242 0.152 0.273 0.242 0.212 0.273 0.288 0.167");
        builder.add(DNANucleotide.C, "0.242 0.227 0.258 0.273 0.242 0.212 0.152 0.394 0.273 0.227 0.212 0.288 0.273 0.258 0.288");
        builder.add(DNANucleotide.G, "0.212 0.273 0.333 0.303 0.288 0.227 0.348 0.197 0.242 0.212 0.273 0.273 0.212 0.273 0.242");
        builder.add(DNANucleotide.T, "0.288 0.212 0.167 0.212 0.212 0.333 0.242 0.167 0.333 0.288 0.273 0.227 0.242 0.182 0.303");


        assertEquals("TCCGGAGCTAGACAT",
                SubStringUtils.findProfileMostProbableKmer("TGACTAGCCTCTCATGTGTCTTCTTGATAAGGATCGCACTAGAGGGGCATCTGTGAGCGGCCGCTGTCTATTAATGTAACGGGGGCATTATCTAATGCAAAATCTATCATATCGGTTAACGACATAGCCACGAAACTCATTAAAAGCCCCCCCCACATGTTGCGCCCACTCGCACACCCCTGAATTTGGACCTTCCTACTAAAAGGAGATAGCTCTGGGACCACGGTTAGGCAGGCTGAGGAAATGTGCTTCAACAATACCTACAAAATCCTATGCACCTGGCTGGCTCTGTCCTAGCTGTGAGTGTTAACAAGCAGGCCCGGTCGCGGATCCAGCTAACTCCTAACAGCATTGTTAAGATTAAGGAAGGATCTCGGTTGGGGAATAAATGGTCAGACTCGTGATTATTTGATGAGCGAGAGGTGTCTCGATTTCTGGTCCGGGCAGAAATTTGCCTCCAGTGAGGTGAGGTTACAGCTATAGAAAACAATTGACGGTTTAGGGTCTTAAGGGAGTCCGCTCTGCAATCGACACATCCTGAACAGAAGTCTCGTGGACGTTGCTAGCGGGTGGATCACCACCGAGAAGGGGTTGCGGAGTCATAGGTCCTTCACTTAAGACCTTTTGCAGAATCCGGCGTCTTAATTTTTACGGCCATAGCTCGGCACTCCGGAGCTAGACATAACCATTAGTTGACTCGCCTTCGGAATGAAGACGTGATCAACTACTCCTGCCGGTCCGTGGCCCCTGTTAGCCCCTCTTCAAAGTCCTTGGATCAGAGGAGCCAAAATAGTAGTTGCCACGCGCAGAATAAGTCACCGAACCACCTCAAAAGGCAACTGAATCACCAACAGCTCCATACCGCCACCCTGGGTACCCTCATATCGCTGCGACCACTAGTTATTGAGACCGACAAGCTCTAGCGTATCGACTAATGCTTGAATACTTCTCAACGAGTCCTGATGCTCTTACCTTTGTCCAACTACCTCGTCACCATGCC",
                        15, builder.getDNAProfileMatrix()));
    }

    @Test
    public void testGetKmers(){
        assertEquals("[AB, BC, CD, DE, EF]", SubStringUtils.getKmers("ABCDEF", 2).toString());
    }
}