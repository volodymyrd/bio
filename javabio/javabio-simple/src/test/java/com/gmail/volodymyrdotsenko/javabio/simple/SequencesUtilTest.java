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
    public void stringSpelledGenomePathProblemForKDmer() throws Exception {
        assertEquals("TAATGCCATGGGATGTT",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new KDmer[]{
                                        new KDmer("TAA", "CCA"),
                                        new KDmer("AAT", "CAT"),
                                        new KDmer("ATG", "ATG"),
                                        new KDmer("TGC", "TGG"),
                                        new KDmer("GCC", "GGG"),
                                        new KDmer("CCA", "GGA"),
                                        new KDmer("CAT", "GAT"),
                                        new KDmer("ATG", "ATG"),
                                        new KDmer("TGG", "TGT"),
                                        new KDmer("GGG", "GTT")}, 3, 2));
    }

    @Test
    public void stringSpelledGenomePathProblemForKDmer2() throws Exception {
        assertEquals("AGCAGCTGCTGCA",
                SequencesUtil
                        .stringSpelledGenomePathProblem(
                                new KDmer[]{
                                        new KDmer("AG", "AG"),
                                        new KDmer("GC", "GC"),
                                        new KDmer("CA", "CT"),
                                        new KDmer("AG", "TG"),
                                        new KDmer("GC", "GC"),
                                        new KDmer("CT", "CT"),
                                        new KDmer("TG", "TG"),
                                        new KDmer("GC", "GC"),
                                        new KDmer("CT", "CA")}, 2, 1));
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

    @Test
    public void buildBruijnGraphSeq() throws Exception {
        assertEquals(new SymbolDigraph()
                        .addEdge("AAG", "AGA")
                        .addEdge("AGA", "GAT")
                        .addEdge("GAT", "ATT")
                        .addEdge("ATT", "TTC")
                        .addEdge("TTC", "TCT")
                        .addEdge("TCT", "CTC")
                        .addEdge("CTC", "TCT")
                        .addEdge("TCT", "CTA")
                        .addEdge("CTA", "TAA")
                        .addEdge("TAA", "AAG")
                        .addEdge("AAG", "AGA"),
                SequencesUtil.buildBruijnGraph("AAGATTCTCTAAGA", 4));
    }

    @Test
    public void buildBruijnGraphSeq2() throws Exception {
        assertEquals(new SymbolDigraph()
                        .addEdge("GAG", "AGG")
                        .addEdge("CAG", "AGG")
                        .addEdge("GGG", "GGG")
                        .addEdge("GGG", "GGA")
                        .addEdge("CAG", "AGG")
                        .addEdge("AGG", "GGG")
                        .addEdge("GGA", "GAG")
                ,
                SequencesUtil.buildBruijnGraph(new String[]{"GAGG", "CAGG", "GGGG", "GGGA", "CAGG", "AGGG", "GGAG"}));
    }

    @Test
    public void findHamiltonianPathInBruijnGraph2() throws Exception {
        assertEquals("00110", SequencesUtil.findHamiltonianPathInBruijnGraph(2));
    }

    @Test
    public void findHamiltonianPathInBruijnGraph3() throws Exception {
        assertEquals("0000100110101111000", SequencesUtil.findHamiltonianPathInBruijnGraph(4));
    }

    @Test
    public void stringReconstruction() {
        assertEquals("GGCTTACCA",
                SequencesUtil.stringReconstruction(new String[]{"CTTA", "ACCA", "TACC", "GGCT", "GCTT", "TTAC"}));
    }

    @Test
    public void stringReconstructionForKUniversal() {
        assertEquals("0001111011001010",
                SequencesUtil.stringReconstructionForKUniversal(4));
    }

    //@Test
    public void stringReconstructionForKDmer() {
        assertEquals("AGCAGCTGCTGCA",
                SequencesUtil.stringReconstruction(new KDmer[]{
                        new KDmer("AG", "AG"), new KDmer("GC", "GC"),
                        new KDmer("CA", "CT"), new KDmer("AG", "TG"),
                        new KDmer("GC", "GC"), new KDmer("CT", "CT"),
                        new KDmer("TG", "TG"), new KDmer("GC", "GC"),
                        new KDmer("CT", "CA"),}, 2, 1));
    }
}