package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.SymbolDigraph;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 12.07.16.
 */
public class SequencesUtilTest {
    @Test
    public void stringSpelledGenomePathProblem() throws Exception {
        assertEquals("ACCGAAGCT",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new String[]{"ACCGA", "CCGAA", "CGAAG", "GAAGC", "AAGCT"}));
    }

    @Test
    public void stringSpelledGenomePathProblemForBruijn() throws Exception {
        assertEquals("00110",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new String[]{"00", "01", "11", "10"}));
    }

    @Test
    public void stringSpelledGenomePathProblemForBruijn2() throws Exception {
        assertEquals("01100",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new String[]{"01", "11", "10", "00"}));
    }

    @Test
    public void prefix() throws Exception {
        assertEquals("TA", SequencesUtil.prefix("TAA"));
    }

    @Test
    public void suffix() throws Exception {
        assertEquals("AA", SequencesUtil.suffix("TAA"));
    }

    @Test
    public void overlapGraph() throws Exception {
        assertEquals(new SymbolDigraph()
                        .addEdge("GCATG", "CATGC")
                        .addEdge("CATGC", "ATGCG")
                        .addEdge("AGGCA", "GGCAT")
                        .addEdge("GGCAT", "GCATG"),
                SequencesUtil.overlapGraph(new String[]{"ATGCG", "GCATG", "CATGC", "AGGCA", "GGCAT"}));
    }

    @Test
    public void buildBruijnGraph() throws Exception {
        assertEquals(new SymbolDigraph()
                        .addEdge("00", "01")
                        .addEdge("01", "10")
                        .addEdge("01", "11")
                        .addEdge("10", "00")
                        .addEdge("10", "01")
                        .addEdge("11", "10"),
                SequencesUtil.buildBruijnGraph(2));
    }
}