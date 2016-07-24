package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

import java.util.*;

/**
 * Cycle oriented graph
 * <p>
 * Created by Volodymyr Dotsenko on 22.07.16.
 */
public class CycleDigraph extends Digraph {
    public class Edge {
        private final int v;
        private final int w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        public int getV() {
            return v;
        }

        public int getW() {
            return w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (v != edge.v) return false;
            return w == edge.w;

        }

        @Override
        public int hashCode() {
            int result = v;
            result = 31 * result + w;
            return result;
        }
    }

    public class Vertex {
        //private final int v;

    }

    private final Set<Edge> edges = new HashSet<>();
    private final Map<Integer, Set<Edge>> edgesOfVertex = new HashMap<>();
    private final List<LinkedBag<Integer>> connectedVertices = new ArrayList<>();

    public CycleDigraph() {
    }

    public CycleDigraph(int V) {
        super(V);
    }

    @Override
    public Digraph addEdge(int v, int w) {
        Edge edge = new Edge(v, w);
        edges.add(edge);
        addEdgeToEdgesOfVertex(v, edge);
        LinkedBag<Integer> vVertices = connectedVertices.get(v);
        if(vVertices == null)
            vVertices = new LinkedBag<Integer>();
        vVertices.add(w);

        return super.addEdge(v, w);
    }

    private void addEdgeToEdgesOfVertex(int v, Edge edge) {
        Set<Edge> edges = edgesOfVertex.get(v);
        if (edges == null) {
            edges = new HashSet<>();
            edgesOfVertex.put(v, edges);
        }
        edges.add(edge);
    }

    public Set<Edge> getCycle() {
        Set<Edge> cycle = new HashSet<>();

        return cycle;
    }

    private void findCycle(int start) {
        for (int v : adj[start]) {

        }
    }
}