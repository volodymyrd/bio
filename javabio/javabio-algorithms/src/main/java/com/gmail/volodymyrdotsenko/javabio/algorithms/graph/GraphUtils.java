package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

/**
 * Created by Volodymyr Dotsenko on 13.07.16.
 */
public class GraphUtils {
    /**
     * Compute the degree of vertex v
     *
     * @param g - graph
     * @param v - vertex
     * @return degree
     */
    public static int degree(Graph g, int v) {
        int degree = 0;
        for (int w : g.adj(v))
            degree++;
        return degree;
    }
}