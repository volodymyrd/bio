package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 13.07.16.
 */
public class GraphUtilsTest {

    private IGraph graph;

    @Before
    public void setUp() {
        GraphBuilder builder = new GraphBuilder(13, GraphBuilder.GraphType.UNDIRECTED);
        graph = builder
                .addEdge(0, 5)
                .addEdge(4, 3)
                .addEdge(0, 1)
                .addEdge(9, 12)
                .addEdge(6, 4)
                .addEdge(5, 4)
                .addEdge(0, 2)
                .addEdge(11, 12)
                .addEdge(9, 10)
                .addEdge(0, 6)
                .addEdge(7, 8)
                .addEdge(9, 11)
                .addEdge(5, 3)
                .toGraph();
    }

    @Test
    public void shouldReturnDegree() {
        assertEquals(4, GraphUtils.degree(graph, 0));
    }

    @Test
    public void shouldReturnMaxDegree() {
        assertEquals(4, GraphUtils.maxDegree(graph));
    }
}