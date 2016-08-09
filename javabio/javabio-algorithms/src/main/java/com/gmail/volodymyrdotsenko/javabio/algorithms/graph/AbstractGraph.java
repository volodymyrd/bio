package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;
import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.*;

/**
 * Abstract graph
 * <p>
 * Created by Volodymyr Dotsenko on 18.07.16.
 */
public abstract class AbstractGraph implements IGraph {

    public class UnbalancedVertex {
        public final int v;
        public final int indegree;
        public final int outdegree;

        public UnbalancedVertex(int v, int indegree, int outdegree) {
            this.v = v;
            this.indegree = indegree;
            this.outdegree = outdegree;
        }

        @Override
        public String toString() {
            return "UnbalancedVertex{" +
                    "v=" + v +
                    ", indegree=" + indegree +
                    ", outdegree=" + outdegree +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UnbalancedVertex that = (UnbalancedVertex) o;

            return v == that.v;

        }

        @Override
        public int hashCode() {
            return v;
        }
    }

    public static class Edge {
        private final int v;
        private final int w;
        private final int degree;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            degree = 1;
        }

        public Edge(int v, int w, int degree) {
            this.v = v;
            this.w = w;
            this.degree = degree;
        }

        public Edge(Edge edge) {
            this.v = edge.v;
            this.w = edge.w;
            this.degree = edge.degree;
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
            if (w != edge.w) return false;
            return degree == edge.degree;
        }

        @Override
        public int hashCode() {
            int result = v;
            result = 31 * result + w;
            result = 31 * result + degree;
            return result;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "v=" + v +
                    ", w=" + w +
                    ", degree=" + degree +
                    '}';
        }
    }

    protected int V;                      //number of vertices
    protected int E;                      //number of edges
    protected LinkedBag<Integer>[] adj;   //adjacency lists

    protected final Set<Edge> edges = new HashSet<>();
    protected Set<UnbalancedVertex> unbalancedVertices = new HashSet<>();

    public AbstractGraph() {
    }

    /**
     * @param adjacencyList - The adjacency list as a list of string such vertex id -> vertex id,vertex id,...
     */
    public void buildFromAdjacencyList(List<String> adjacencyList) {
        adjacencyList.forEach(e -> {
            String[] input = e.split("\\W*->\\W*");
            int v = Integer.valueOf(input[0]);
            String[] wstr = input[1].split("\\W*,\\W*");
            for (int i = 0; i < wstr.length; i++) {
                int w = Integer.valueOf(wstr[i]);
                addEdge(v, w);
            }
        });
    }

    protected class DepthIterator implements Iterator<Integer> {
        private final int firstVertex;
        private final Iterator<Integer> iterator;
        private final int returnLastIfExist;
        private final boolean returnLastIfExistFlag;
        private LinkedStack<Integer> store;

        public DepthIterator() {
            this(0);
        }

        public DepthIterator(int firstVertex) {
            this(firstVertex, -1, false);
        }

        public DepthIterator(int firstVertex, int returnLast, boolean returnLastFlag) {
            this.firstVertex = firstVertex;
            iterator = adj[firstVertex].iterator();
            returnLastIfExist = returnLast;
            returnLastIfExistFlag = returnLastFlag;
            if (returnLastIfExistFlag)
                store = new LinkedStack();
        }

        @Override
        public boolean hasNext() {
            boolean exist = iterator.hasNext();

            if (store != null && !exist && store.size() > 0) {
                return true;
            } else
                return exist;
        }

        @Override
        public Integer next() {
            if (returnLastIfExistFlag)
                return getNext();
            else
                return iterator.next();
        }

        private Integer getNext() {
            if (iterator.hasNext()) {
                Integer it = iterator.next();
                if (it == returnLastIfExist) {
                    store.push(it);
                    return getNext();
                } else
                    return it;
            } else if (store.size() > 0) {
                return store.pop();
            } else
                return null;
        }
    }

    protected class BreadthIterator implements Iterator<Integer> {

        private int index;

        public BreadthIterator() {
            this(0);
        }

        public BreadthIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < adj.length;
        }

        @Override
        public Integer next() {
            return index++;
        }
    }

    /**
     * Returns the degree of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the degree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    @Override
    public int degree(int v) {
        return adj[v].size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractGraph digraph = (AbstractGraph) o;

        if (V != digraph.V) return false;
        if (E != digraph.E) return false;

        if (adj == digraph.adj)
            return true;
        if (adj == null || digraph.adj == null)
            return false;

        int length = adj.length;
        if (digraph.adj.length != length)
            return false;

        for (int i = 0; i < length; i++) {
            if (!adj[i].equals(digraph.adj[i]))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = V;
        result = 31 * result + E;
        result = 31 * result + Arrays.hashCode(adj);
        return result;
    }
}