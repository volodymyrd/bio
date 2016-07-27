package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.*;

/**
 * Cycle oriented graph
 * <p>
 * Created by Volodymyr Dotsenko on 22.07.16.
 */
public class EulerianDigraph extends Digraph {

    public static class Edge {
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
    private Map<Integer, Set<Edge>> deletedEdges;

    public EulerianDigraph() {
    }

    public EulerianDigraph(int V) {
        super(V);
    }

    @Override
    public Digraph addEdge(int v, int w) {
        Edge edge = new Edge(v, w);
        edges.add(edge);

        return super.addEdge(v, w);
    }

    public boolean isEulerian() {
        BreadthIterator breadthIterator = new BreadthIterator();
        while (breadthIterator.hasNext()) {
            int v = breadthIterator.next();
            if (indegree(v) != outdegree(v))
                return false;
        }

        return true;
    }

    public Set<Edge> findCycleAsEdge() {
        Set<Edge> path = new LinkedHashSet<>();

        Iterator<Integer> it = findCycle().iterator();
        Integer v = null;
        while (it.hasNext()) {
            if (v == null)
                v = it.next();
            Integer w = it.next();
            if (w == null)
                break;

            path.add(new Edge(v, w));
            v = w;
        }

        return path;
    }

    public LinkedStack<Integer> findCycle() {
        if (!isEulerian()) {
            throw new IllegalStateException("Graph is not Eulerian");
        }

        deletedEdges = new HashMap<>();

        LinkedStack<Integer> vertices = new LinkedStack<>();

        LinkedStack<Integer> stack = new LinkedStack<>();
        BreadthIterator breadthIterator = new BreadthIterator();

        if (breadthIterator.hasNext()) {
            stack.push(breadthIterator.next());

            while (!stack.isEmpty()) {
                int v = stack.peek();
                Set<Edge> deleted = deletedEdges.get(v);
                int deletedNumber = (deleted == null ? 0 : deleted.size());
                if ((outdegree(v) - deletedNumber) == 0) {
                    vertices.push(stack.pop());
                } else {
                    if (deleted == null) {
                        deleted = new HashSet<>(degree(v));
                        deletedEdges.put(v, deleted);
                    }

                    DepthIterator depthIterator = new DepthIterator(v);
                    while (depthIterator.hasNext()) {
                        int w = depthIterator.next();
                        Edge edge = new Edge(v, w);
                        if (!deleted.contains(edge)) {
                            stack.push(w);
                            deleted.add(edge);
                            break;
                        }
                    }
                }
            }
        }

        deletedEdges.clear();
        deletedEdges = null;

        return vertices;
    }
}