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

        @Override
        public String toString() {
            return "Edge{" +
                    "v=" + v +
                    ", w=" + w +
                    '}';
        }
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

    private boolean checkPathContainsAllEdges(Set<Edge> path) {
        if (edges.size() > path.size())
            return false;

        if (path.size() > edges.size())
            throw new IllegalStateException("Path is wrong");

        if (edges.stream().filter(e -> !path.contains(e)).findFirst().isPresent())
            return false;
        else
            return true;
    }

    public Set<Edge> path() {
        Set<Edge> path = new LinkedHashSet<>();

        BreadthIterator breadthIterator = new BreadthIterator();
        int nextStartPoint = -1;

        EXIT:
        while (breadthIterator.hasNext()) {
            int startPoint = -1;

            if (nextStartPoint == -1)
                startPoint = breadthIterator.next();
            else
                startPoint = nextStartPoint;

            if(startPoint == -1)
                break;

            int v = startPoint;

            NEXT:
            for (; ; ) {
                DepthIterator depthIterator = new DepthIterator(v, startPoint, true);
                int temp = v;
                while (depthIterator.hasNext()) {
                    Integer w = depthIterator.next();
                    if (w == null)
                        break NEXT;

                    Edge edge = new Edge(v, w);
                    if (!path.contains(edge)) {
                        path.add(edge);
                        v = w;
                        break;
                    }
                }

                if (v == startPoint) {
                    if(checkPathContainsAllEdges(path))
                        break EXIT;

                    path.clear();

                    break NEXT;
                }
                if (temp == v)
                    break EXIT;
            }
        }

        return path;
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