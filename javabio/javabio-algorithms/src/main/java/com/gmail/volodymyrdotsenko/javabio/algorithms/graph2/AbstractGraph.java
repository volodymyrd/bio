package com.gmail.volodymyrdotsenko.javabio.algorithms.graph2;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

/**
 * Created by Volodymyr_Dotsenko on 11/8/2016.
 */
public abstract class AbstractGraph {

    protected int V;                 //number of vertices
    protected int E;                 //number of edges
    protected LinkedBag<Edge>[] adj; //adjacency lists

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the edge {@code e} to this graph.
     *
     * @param e the edge
     * @return this
     * @throws IndexOutOfBoundsException unless endpoints of edge are between {@code 0} and {@code V-1}
     */
    abstract public AbstractGraph addEdge(Edge e);

    /**
     * Adds the weighted edge v-w to this graph.
     *
     * @param v      the 'tail' vertex
     * @param w      the 'head' vertex
     * @param weight the weight of edge
     * @return this
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    abstract public AbstractGraph addEdge(int v, int w, double weight);

    /**
     * Adds the edge v-w to this graph.
     *
     * @param v the 'tail' vertex
     * @param w the 'head' vertex
     * @return this
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    abstract public AbstractGraph addEdge(int v, int w);

    /**
     * Returns the edges incident from vertex {@code v}.
     *
     * @param v the vertex
     * @return the edges incident from vertex {@code v} as an Iterable
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    abstract public Iterable<Edge> adj(int v);
}