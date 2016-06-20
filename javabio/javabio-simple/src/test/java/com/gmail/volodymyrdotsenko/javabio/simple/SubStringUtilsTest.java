package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 6/19/16.
 */
public class SubStringUtilsTest {

    @Test
    public void testSlideWindowPatternCount() {
        assertEquals(3, SubStringUtils.slideWindowPatternCount("ACAACTATGCATACTATCGGGAACTATCCT", "ACTAT"));
    }

    @Test
    public void testfrequentWords() {
        System.out.println();
        assertEquals("{CATG=2, GCAT=2}",
                SubStringUtils.frequentWords("ACGTTGCATGTCGCATGATGCATGAGAGCT", 4).toString());
    }
}