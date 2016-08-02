package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Dotsenko on 7/16/16.
 */
public class SymbolDigraph extends Digraph {

    protected final Map<String, Integer> st = new HashMap<>();    // string -> index
    protected String[] keys;                                      // index  -> string

    public SymbolDigraph() {
        keys = new String[0];
    }

    public SymbolDigraph(int V) {
        super(V);
        keys = new String[V];
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    @Override
    public SymbolDigraph addEdge(int v, int w) {
        super.addEdge(v, w);

//        if (keys.length < V())
//            keys = Arrays.copyOf(keys, V());

//        if (!st.containsKey(v))
//            st.put(String.valueOf(v), st.size());
//
//        if (!st.containsKey(w))
//            st.put(String.valueOf(w), st.size());

        return this;
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public SymbolDigraph addEdge(String v, String w) {

        Edge edge = addSymbolEdge(v, w);

        super.addEdge(edge.getV(), edge.getW());

        return this;
    }

    protected Edge addSymbolEdge(String v, String w) {
        Integer iv = st.get(v);
        if (iv == null) {
            iv = st.size();
            st.put(String.valueOf(v), iv);
        }

        Integer iw = st.get(w);
        if (iw == null) {
            iw = st.size();
            st.put(String.valueOf(w), iw);
        }

        int max = iv > iw ? iv : iw;

        if (max >= keys.length) {
            keys = Arrays.copyOf(keys, max + 1);
        }

        keys[iv] = v;
        keys[iw] = w;

        return new Edge(iv, iw);
    }

    public Iterable<String> adj(String v) {
        Iterable<Integer> it = super.adj(st.get(v));

        LinkedBag<String> bag = new LinkedBag<>();

        for (Integer i : it) {
            bag.add(keys[i]);
        }

        return bag;
    }

    public Map<String, Integer> getSt() {
        return st;
    }

    public String[] getKeys() {
        return keys;
    }

    public List<String> toSymbols(Collection<Integer> vertices) {
        return vertices.stream().map(e -> {
            return keys[e];
        }).collect(Collectors.toList());
    }

    public String toString(boolean printEmptyVertices, boolean sortedVertices) {
        StringBuilder s = new StringBuilder();
        s.append(V() + " vertices, " + E() + " edges " + NEWLINE);

        if (sortedVertices) {
            Set<String> sorted = new TreeSet<>(st.keySet());

            for (String v : sorted) {
                Iterable<Integer> it = adj(st.get(v));
                if (printEmptyVertices || (!printEmptyVertices && it.iterator().hasNext())) {
                    s.append(String.format("%s -> ", v));

                    for (int w : adj(st.get(v))) {
                        s.append(String.format("%s,", keys[w]));
                    }
                    int ind = s.lastIndexOf(",");
                    if (ind >= 0)
                        s.deleteCharAt(ind);
                    s.append(NEWLINE);
                }
            }
        } else
            for (String v : keys) {
                Iterable<Integer> it = adj(st.get(v));
                if (printEmptyVertices || (!printEmptyVertices && it.iterator().hasNext())) {
                    s.append(String.format("%s -> ", v));

                    for (int w : adj(st.get(v))) {
                        s.append(String.format("%s,", keys[w]));
                    }
                    int ind = s.lastIndexOf(",");
                    if (ind >= 0)
                        s.deleteCharAt(ind);
                    s.append(NEWLINE);
                }
            }
        return s.toString();
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        return toString(true, false);
    }
}