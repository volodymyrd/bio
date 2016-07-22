package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Eulerian oriented graph
 * <p>
 * <p>
 * Created by Volodymyr Dotsenko on 22.07.16.
 */
public class EulerianDigraph extends Digraph {
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
    }

    private final Set<Edge> edges = new HashSet<>();

    public EulerianDigraph() {
    }

    public EulerianDigraph(int V) {
        super(V);
    }

    @Override
    public Digraph addEdge(int v, int w) {
        edges.add(new Edge(v, w));

        return super.addEdge(v, w);
    }

    public Set<Edge> getCycle() {
        Set<Edge> cycle = new HashSet<>();

        return cycle;
    }

    private void findCycle(int start){
        for(int v : adj[start]){

        }
    }
}