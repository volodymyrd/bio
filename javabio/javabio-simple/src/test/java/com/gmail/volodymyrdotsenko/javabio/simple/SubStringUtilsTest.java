package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

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
}