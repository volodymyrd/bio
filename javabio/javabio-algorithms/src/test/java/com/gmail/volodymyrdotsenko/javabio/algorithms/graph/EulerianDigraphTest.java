package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 25.07.16.
 */
public class EulerianDigraphTest {

    @Test
    public void shouldPrint() {
        EulerianDigraph digraph = new EulerianDigraph();
        digraph.addEdge("A", "A");
        digraph.addEdge("A", "B");
        System.out.println(digraph);
    }

    @Test
    public void cycleFromAdjacencyListTest() {
        List<String> adjacencyList = Stream.of("0   -> 3", "1 ->0", "2 -> 1, 6", "3 -> 2", "4 -> 2", "5 -> 4",
                "6 -> 5,8", "7 -> 9", "8 ->    7", "9 -> 6").collect(Collectors.toList());
        EulerianDigraph eulerianDigraph = new EulerianDigraph();
        eulerianDigraph.buildFromAdjacencyList(adjacencyList);
        assertEquals("[6, 8, 7, 9, 6, 5, 4, 2, 1, 0, 3, 2, 6]", eulerianDigraph.findCycle(6).toString());
    }

    @Test
    public void cycleTest() {
        EulerianDigraph eulerianDigraph = new EulerianDigraph();
        eulerianDigraph.addEdge(0, 3);
        eulerianDigraph.addEdge(1, 0);
        eulerianDigraph.addEdge(2, 1);
        eulerianDigraph.addEdge(2, 6);
        eulerianDigraph.addEdge(3, 2);
        eulerianDigraph.addEdge(4, 2);
        eulerianDigraph.addEdge(5, 4);
        eulerianDigraph.addEdge(6, 5);
        eulerianDigraph.addEdge(6, 8);
        eulerianDigraph.addEdge(7, 9);
        eulerianDigraph.addEdge(8, 7);
        eulerianDigraph.addEdge(9, 6);

        assertEquals("[6, 8, 7, 9, 6, 5, 4, 2, 1, 0, 3, 2, 6]", eulerianDigraph.findCycle(6).toString());
    }

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

    @Test
    public void faindAllPathesTest() {
        EulerianDigraph eulerianDigraph = new EulerianDigraph();
        eulerianDigraph.addEdge(0, 1);
        eulerianDigraph.addEdge(1, 2);
        eulerianDigraph.addEdge(1, 2);
        eulerianDigraph.addEdge(1, 2);
        eulerianDigraph.addEdge(3, 1);
        eulerianDigraph.addEdge(4, 1);
        eulerianDigraph.addEdge(2, 3);
        eulerianDigraph.addEdge(2, 4);
        eulerianDigraph.addEdge(2, 5);

        assertEquals(2, eulerianDigraph.findAllPathes().size());

    }
}