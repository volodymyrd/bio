package com.gmail.volodymyrdotsenko.javabio.algorithms.graph2;

/**
 * The {@code Edge} class represents a weighted edge.
 * Each edge consists of two integers
 * (naming the two vertices) and a real-value weight. The data type
 * provides methods for accessing the two endpoints of the edge and
 * the weight.
 * <p>
 */
public class Edge {

    protected final int v;
    protected final int w;
    protected final double weight;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param v      one vertex
     * @param w      the other vertex
     * @param weight the weight of this edge
     * @throws IndexOutOfBoundsException if either {@code v} or {@code w} is a negative integer
     * @throws IllegalArgumentException  if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weight) {
        if (v < 0) {
            throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        }
        if (w < 0) {
            throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Initializes an edge between vertices {@code v} and {@code w}
     *
     * @param v one vertex
     * @param w the other vertex
     * @throws IndexOutOfBoundsException if either {@code v} or {@code w} is a negative integer
     * @throws IllegalArgumentException  if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w) {
        this(v, w, Double.POSITIVE_INFINITY);
    }

    /**
     * Returns the 'tail' vertex of the edge.
     *
     * @return the 'tail' vertex of the edge
     */
    public int v() {
        return v;
    }

    /**
     * Returns the 'head' vertex of the edge.
     *
     * @return the 'head' vertex of the edge
     */
    public int w() {
        return w;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the weight of the edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of the directed edge.
     *
     * @return a string representation of the directed edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }
}