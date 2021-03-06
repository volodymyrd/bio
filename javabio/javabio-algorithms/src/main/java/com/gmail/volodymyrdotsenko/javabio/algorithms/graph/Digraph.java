package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;
import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The <tt>Digraph</tt> class represents a directed graph of vertices
 * named 0 through <em>V</em> - 1.
 * It supports the following two primary operations: add an edge to the digraph,
 * iterate over all of the vertices adjacent from a given vertex.
 * Parallel edges and self-loops are permitted.
 * <p>
 * This implementation uses an adjacency-lists representation, which
 * is a vertex-indexed array of {@link LinkedBag} objects.
 * All operations take constant time (in the worst case) except
 * iterating over the vertices adjacent from a given vertex, which takes
 * time proportional to the number of such vertices.
 * <p>
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Digraph extends AbstractGraph {
    private int[] indegree;           // indegree[v] = indegree of vertex v

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
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.E = 0;
        indegree = new int[V];
        adj = new LinkedBag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedBag<Integer>();
        }
    }

    /**
     * Initializes a new digraph that is a deep copy of the specified digraph.
     *
     * @param G the digraph to copy
     */
    public Digraph(Digraph G) {
        this(G.V);
        this.E = G.E();
        for (int v = 0; v < V; v++)
            this.indegree[v] = G.indegree(v);
        for (int v = 0; v < G.V; v++) {
            // reverse so that adjacency list is in same order as original
            LinkedStack<Integer> reverse = new LinkedStack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    @Override
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    @Override
    public int E() {
        return E;
    }


    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    @Override
    public Digraph addEdge(int v, int w) {

        int max = v > w ? v : w;

        if (max >= adj.length) {
            adj = Arrays.copyOf(adj, max + 1);
            for (int i = V; i <= max; i++)
                adj[i] = new LinkedBag<Integer>();
        }

        adj[v].add(w);

        if (max >= indegree.length) {
            indegree = Arrays.copyOf(indegree, max + 1);
        }

        indegree[w]++;

        E++;
        V = adj.length;

        return this;
    }

    /**
     * Returns the vertices adjacent from vertex <tt>v</tt> in this digraph.
     *
     * @param v the vertex
     * @return the vertices adjacent from vertex <tt>v</tt> in this digraph, as an iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex <tt>v</tt>.
     * This is known as the <em>outdegree</em> of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the outdegree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int outdegree(int v) {
        return adj[v].size();
    }

    public int outdegreeUnique(int v) {
        Set<Integer> unique = new HashSet<>();
        for (Integer i : adj[v])
            unique.add(i);

        return unique.size();
    }

    @Override
    public int degree(int v) {
        return outdegree(v) + indegree(v);
    }

    /**
     * Returns the number of directed edges incident to vertex <tt>v</tt>.
     * This is known as the <em>indegree</em> of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the indegree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int indegree(int v) {
        return indegree[v];
    }

    /**
     * Returns the reverse of the digraph.
     *
     * @return the reverse of the digraph
     */
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    protected static final String NEWLINE = System.getProperty("line.separator");

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}