package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 8/2/16.
 */
public class KDmerIteratorTest {

    @Test
    public void shouldPrintSortedKDmer() {
        KDmerIterator iterator = new KDmerIterator("TAATGCCATGGGATGTT", 3, 1);
        assertEquals("[(AAT|CCA), (ATG|CAT), (ATG|GAT), (CAT|GGA), (CCA|GGG), (GCC|TGG), (GGA|GTT), (GGG|TGT), (TAA|GCC), (TGC|ATG), (TGG|ATG)]"
                , iterator.getList(true).toString());
    }

    @Test
    public void shouldPrintSortedKDmer1() {
        KDmerIterator iterator = new KDmerIterator("TAATGCCATGGGATGTT", 3, 1);
        assertEquals("(AAT|CCA) (ATG|CAT) (ATG|GAT) (CAT|GGA) (CCA|GGG) (GCC|TGG) (GGA|GTT) (GGG|TGT) (TAA|GCC) (TGC|ATG) (TGG|ATG)",
                iterator.getList(true).stream().map(e -> e.toString()).collect(Collectors.joining(" ")));
    }

    @Test
    public void shouldPrintSortedKDmer2() {
        KDmerIterator iterator = new KDmerIterator("TAATGCCATGGGATGTT", 3, 2);
        assertEquals("(AAT|CAT) (ATG|ATG) (ATG|ATG) (CAT|GAT) (CCA|GGA) (GCC|GGG) (GGG|GTT) (TAA|CCA) (TGC|TGG) (TGG|TGT)",
                iterator.getList(true).stream().map(e -> e.toString()).collect(Collectors.joining(" ")));
    }
}