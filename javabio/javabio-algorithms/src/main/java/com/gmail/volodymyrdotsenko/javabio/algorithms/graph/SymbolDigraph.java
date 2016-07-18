package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Volodymyr Dotsenko on 7/16/16.
 */
public class SymbolDigraph extends Digraph {

    private final Map<String, Integer> st = new HashMap<>();  // string -> index
    //private final List<String> keys = new ArrayList<>();           // index  -> string

    public SymbolDigraph(int V) {
        super(V);
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    @Override
    public void addEdge(int v, int w) {
        super.addEdge(v, w);

        if (!st.containsKey(v))
            st.put(String.valueOf(v), st.size());

        if (!st.containsKey(w))
            st.put(String.valueOf(w), st.size());
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(String v, String w) {

        Integer iv = st.get(v);
        if (iv == null) {
            iv = st.size();
            st.put(String.valueOf(v), iv);
        }

        Integer iw = st.get(w);
        if (iw == null) {
            iw = st.size();
            st.put(String.valueOf(v), iw);
        }

        super.addEdge(iv, iw);
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V() + " vertices, " + E() + " edges " + NEWLINE);
        for (int v = 0; v < V(); v++) {
            s.append(String.format("%d: ", v));
            s.append("->");
            for (int w : adj(v)) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}