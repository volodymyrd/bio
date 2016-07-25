package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import org.junit.Test;

/**
 * Created by Volodymyr Dotsenko on 25.07.16.
 */
public class CycleDigraphTest {

    @Test
    public void pathTest() {
        CycleDigraph cycleDigraph = new CycleDigraph();
        cycleDigraph.addEdge(0, 1);
        cycleDigraph.addEdge(1, 2);
        cycleDigraph.addEdge(1, 6);
        cycleDigraph.addEdge(2, 0);
        cycleDigraph.addEdge(6, 2);
        cycleDigraph.addEdge(2, 4);
        cycleDigraph.addEdge(4, 3);
        cycleDigraph.addEdge(3, 5);
        cycleDigraph.addEdge(4, 5);
        cycleDigraph.addEdge(5, 4);
        cycleDigraph.addEdge(5, 1);

        System.out.println(cycleDigraph.path());
    }
}