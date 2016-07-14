package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

/**
 * Set of static methods helping to work with graph
 * <p>
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

    /**
     * Compute maximum degree
     *
     * @param g - graph
     * @return maximum degree
     */
    public static int maxDegree(Graph g) {
        int max = 0;
        for (int v = 0; v < g.V(); v++) {
            int degree = degree(g, v);
            if (degree > max)
                max = degree;
        }
        return max;
    }

    /**
     * Compute average degree
     *
     * @param g - graph
     * @return average degree
     */
    public static double averageDegree(Graph g) {
        return 2.0 * g.E() / g.V();
    }
}