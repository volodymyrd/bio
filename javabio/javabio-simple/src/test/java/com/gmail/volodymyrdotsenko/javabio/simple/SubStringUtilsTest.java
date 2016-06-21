package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import static org.junit.Assert.*;

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
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, -1).toString());
    }

    @Test
    public void testAllFrequentWords() {
        assertEquals("{ATGA=2, TGCA=2, CATG=3, GCAT=3}",
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 1).toString());
    }

    @Test
    public void testGetClump() {
        System.out.println(SubStringUtils.getClump("CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA",
                5, 50, 4));
    }
}