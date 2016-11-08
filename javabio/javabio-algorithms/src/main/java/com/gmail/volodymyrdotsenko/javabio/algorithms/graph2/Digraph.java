package com.gmail.volodymyrdotsenko.javabio.algorithms.graph2;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

/**
 * The {@code Digraph} class represents a edge-weighted
 * digraph of vertices named 0 through <em>V</em> - 1, where each
 * directed edge is of type {@link Edge} and might has a real-valued weight.
 * It supports the following two primary operations: add a directed edge
 * to the digraph and iterate over all of edges incident from a given vertex.
 * It also provides methods for returning the number of vertices <em>V</em> and the number
 * of edges <em>E</em>. Parallel edges and self-loops are permitted.
 * <p>
 * All operations take constant time (in the worst case) except
 * iterating over the edges incident from a given vertex, which takes
 * time proportional to the number of such edges.
 * <p>
 */
public class Digraph extends AbstractGraph {

    private int[] indegree; // indegree[v] = indegree of vertex v

    /**
     * Initializes an empty digraph
     */
    public Digraph() {
        this.E = 0;
        indegree = new int[0];
        adj = new LinkedBag[0];
    }


    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param V the number of vertices
     * @throws IllegalArgumentException if V < 0
     */
    public Digraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        this.E = 0;
        indegree = new int[V];
        adj = new LinkedBag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedBag<Edge>();
        }
    }

    @Override
    public Digraph addEdge(Edge e) {
        int v = e.v();
        int w = e.w();
        adj[v].add(e);
        indegree[w]++;
        E++;
        return this;
    }

    @Override
    public Digraph addEdge(int v, int w, double weight) {
        return this.addEdge(new Edge(v, w, weight));
    }

    @Override
    public Digraph addEdge(int v, int w) {
        return this.addEdge(v, w, Double.POSITIVE_INFINITY);
    }

    @Override
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        return indegree[v];
    }
}