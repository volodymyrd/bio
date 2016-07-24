package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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

    public CycleDigraph() {
    }

    public CycleDigraph(int V) {
        super(V);
    }

    @Override
    public Digraph addEdge(int v, int w) {
        Edge edge = new Edge(v, w);
        edges.add(edge);

        return super.addEdge(v, w);
    }

    public void path() {
        Set<Edge> path = new LinkedHashSet<>();

        int iv = 0;
        for (int v : adj[iv]) {
            Edge edge = new Edge(iv, v);
            if (!path.contains(edge)) {
                path.add(edge);
                break;
            }
        }
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