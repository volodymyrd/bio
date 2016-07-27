package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Volodymyr Dotsenko on 25.07.16.
 */
public class EulerianDigraphTest {

    @Test
    public void pathTest() {
        EulerianDigraph eulerianDigraph = new EulerianDigraph();
        eulerianDigraph.addEdge(0, 1);
        eulerianDigraph.addEdge(1, 2);
        eulerianDigraph.addEdge(1, 6);
        eulerianDigraph.addEdge(2, 0);
        eulerianDigraph.addEdge(6, 2);
        eulerianDigraph.addEdge(2, 4);
        eulerianDigraph.addEdge(4, 3);
        eulerianDigraph.addEdge(3, 5);
        eulerianDigraph.addEdge(4, 5);
        eulerianDigraph.addEdge(5, 4);
        eulerianDigraph.addEdge(5, 1);

        assertEquals(Stream.of(new EulerianDigraph.Edge(0, 1),
                new EulerianDigraph.Edge(1, 6),
                new EulerianDigraph.Edge(6, 2),
                new EulerianDigraph.Edge(2, 4),
                new EulerianDigraph.Edge(4, 5),
                new EulerianDigraph.Edge(5, 4),
                new EulerianDigraph.Edge(4, 3),
                new EulerianDigraph.Edge(3, 5),
                new EulerianDigraph.Edge(5, 1),
                new EulerianDigraph.Edge(1, 2),
                new EulerianDigraph.Edge(2, 0))
                .collect(Collectors.toSet()), eulerianDigraph.findCycleAsEdge());
    }
}