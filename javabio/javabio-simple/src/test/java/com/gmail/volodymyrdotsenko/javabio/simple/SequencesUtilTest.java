package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 12.07.16.
 */
public class SequencesUtilTest {
    @Test
    public void stringSpelledGenomePathProblemTest() throws Exception {
        assertEquals("ACCGAAGCT",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new String[]{"ACCGA", "CCGAA", "CGAAG", "GAAGC", "AAGCT"}));
    }

    @Test
    public void shouldReturnPrefix() throws Exception {
        assertEquals("TA", SequencesUtil.prefix("TAA"));
    }

    @Test
    public void shouldReturnSuffix() throws Exception {
        assertEquals("AA", SequencesUtil.suffix("TAA"));
    }
}