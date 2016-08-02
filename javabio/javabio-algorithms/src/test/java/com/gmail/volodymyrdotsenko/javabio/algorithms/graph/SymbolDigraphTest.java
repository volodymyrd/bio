package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import org.junit.Test;

/**
 * Created by Volodymyr Dotsenko on 7/31/16.
 */
public class SymbolDigraphTest {

    @Test
    public void shouldPrint() {
        SymbolDigraph digraph = new SymbolDigraph();
        digraph.addEdge("A", "A");
        digraph.addEdge("A", "B");
        System.out.println(digraph);
    }
}