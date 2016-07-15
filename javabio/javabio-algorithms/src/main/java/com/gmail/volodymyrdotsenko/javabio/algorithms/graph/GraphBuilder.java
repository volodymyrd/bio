package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

/**
 * Help to building a graph
 * <p>
 * Created by Volodymyr Dotsenko on 13.07.16.
 */
public class GraphBuilder {
    public enum GraphType {
        UNDIRECTED, DIRECTED
    }

    private final IGraph graph;

    public GraphBuilder(int v, GraphType type) {
        switch (type) {
            case UNDIRECTED:
                graph = new Graph(v);
                break;
            case DIRECTED:
                graph = new Digraph(v);
                break;
            default:
                throw new IllegalArgumentException("Unsupported graph type");
        }
    }

    public IGraph toGraph() {
        return graph;
    }

    public GraphBuilder addEdge(int v, int w) {
        graph.addEdge(v, w);

        return this;
    }
}