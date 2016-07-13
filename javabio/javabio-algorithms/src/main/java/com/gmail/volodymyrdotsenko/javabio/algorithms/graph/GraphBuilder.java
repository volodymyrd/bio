package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

/**
 * Help to building a graph
 * <p>
 * Created by Volodymyr Dotsenko on 13.07.16.
 */
public class GraphBuilder {
    private final Graph graph;

    public GraphBuilder(int v) {
        graph = new Graph(v);
    }

    public Graph toGraph() {
        return graph;
    }

    public GraphBuilder addEdge(int v, int w) {
        graph.addEdge(v, w);

        return this;
    }
}