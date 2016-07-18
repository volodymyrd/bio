package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Volodymyr Dotsenko on 13.07.16.
 */
public class DigraphTest {

    @Test
    public void shouldAddEdgesToEmtyDigraph() {
        Digraph digraphWith4vert = new Digraph(4);
        digraphWith4vert.addEdge(0, 1);
        digraphWith4vert.addEdge(0, 2);
        digraphWith4vert.addEdge(1, 3);

        Digraph emptyDigraph = new Digraph();
        emptyDigraph.addEdge(0, 1);
        emptyDigraph.addEdge(0, 2);
        emptyDigraph.addEdge(1, 3);

        assertEquals(digraphWith4vert, emptyDigraph);
    }
}