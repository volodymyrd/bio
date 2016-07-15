package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

/**
 * Created by Volodymyr Dotsenko on 7/15/16.
 */
public interface IGraph {

    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    int V();

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    int E();

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    void addEdge(int v, int w);

    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the vertices adjacent to vertex <tt>v</tt>, as an iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v);
}