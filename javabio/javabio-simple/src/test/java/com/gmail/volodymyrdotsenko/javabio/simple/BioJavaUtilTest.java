package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class BioJavaUtilTest {

    @Test
    public void testReverseComplement() {
        assertEquals("ACCGGGTTTT", BioJavaUtil.reverseComplement("AAAACCCGGT"));
    }

    @Test
    public void testSkewi() {
        assertEquals("[0, -1, -1, -1, 0, 1, 2, 1, 1, 1, 0, 1, 2, 1, 0, 0, 0, 0, -1, 0, -1, -2]",
                Arrays.toString(BioJavaUtil.skew("CATGGGCATCGGCCATACGCC")));
    }

    @Test
    public void testSkewiMinimums() {
        assertEquals("[11, 24]",
                BioJavaUtil.skewMinimums("TAAAGACTGCCGAGAGGCCAACACGAGTGCTAGAACGAGGGGCGTAAACGCGGGTCCGAT").toString());
    }
}