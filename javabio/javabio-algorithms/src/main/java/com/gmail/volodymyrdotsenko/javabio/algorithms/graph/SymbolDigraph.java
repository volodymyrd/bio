package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Dotsenko on 7/16/16.
 */
public class SymbolDigraph<T> extends Digraph {

    protected final Map<T, Integer> st = new HashMap<>();    // string -> index
    protected T[] keys;                                      // index  -> string

    public SymbolDigraph() {
        keys = (T[]) new Object[0];
    }

    public SymbolDigraph(int V) {
        super(V);
        keys = (T[]) new Object[V];
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
    public SymbolDigraph addEdge(T v, T w) {

        Edge edge = addSymbolEdge(v, w);

        super.addEdge(edge.getV(), edge.getW());

        return this;
    }

    protected Edge addSymbolEdge(T v, T w) {
        Integer iv = st.get(v);
        if (iv == null) {
            iv = st.size();
            st.put(v, iv);
        }

        Integer iw = st.get(w);
        if (iw == null) {
            iw = st.size();
            st.put(w, iw);
        }

        int max = iv > iw ? iv : iw;

        if (max >= keys.length) {
            keys = Arrays.copyOf(keys, max + 1);
        }

        keys[iv] = v;
        keys[iw] = w;

        return new Edge(iv, iw);
    }

    public Iterable<T> adj(String v) {
        Iterable<Integer> it = super.adj(st.get(v));

        LinkedBag<T> bag = new LinkedBag<>();

        for (Integer i : it) {
            bag.add(keys[i]);
        }

        return bag;
    }

    public Map<T, Integer> getSt() {
        return st;
    }

    public List<T> getKeys() {
        return Arrays.asList(keys);
    }

    public List<T> toSymbols(Collection<Integer> vertices) {
        return vertices.stream().map(e -> {
            return keys[e];
        }).collect(Collectors.toList());
    }

    public String toString(boolean printEmptyVertices, boolean sortedVertices) {
        StringBuilder s = new StringBuilder();
        s.append(V() + " vertices, " + E() + " edges " + NEWLINE);

        if (sortedVertices) {
            Set<T> sorted = new TreeSet<>(st.keySet());

            for (T v : sorted) {
                Iterable<Integer> it = adj(st.get(v));
                if (printEmptyVertices || (!printEmptyVertices && it.iterator().hasNext())) {
                    s.append(String.format("%s -> ", v));

                    for (int w : adj(st.get(v))) {
                        s.append(String.format("%s,", keys[w]));
                    }
                    int ind = s.lastIndexOf(",");
                    if (ind == s.length() - 1)
                        s.deleteCharAt(ind);
                    s.append(NEWLINE);
                }
            }
        } else
            for (T v : keys) {
                Iterable<Integer> it = adj(st.get(v));
                if (printEmptyVertices || (!printEmptyVertices && it.iterator().hasNext())) {
                    s.append(String.format("%s -> ", v));

                    for (int w : adj(st.get(v))) {
                        s.append(String.format("%s,", keys[w]));
                    }
                    int ind = s.lastIndexOf(",");
                    if (ind == s.length() - 1)
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