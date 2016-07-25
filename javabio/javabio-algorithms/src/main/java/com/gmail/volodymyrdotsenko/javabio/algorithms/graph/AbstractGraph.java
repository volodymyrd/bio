package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Volodymyr Dotsenko on 18.07.16.
 */
public abstract class AbstractGraph implements IGraph {

    protected int V;                      //number of vertices
    protected int E;                      //number of edges
    protected LinkedBag<Integer>[] adj;   //adjacency lists

    protected class DepthIterator implements Iterator<Integer> {
        private final int firstVertex;
        private final Iterator<Integer> iterator;
        private final int lastValue;

        public DepthIterator() {
            this(0);
        }

        public DepthIterator(int firstVertex) {
            this.firstVertex = firstVertex;
            iterator = adj[firstVertex].iterator();
            lastValue = -1;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Integer next() {
            return iterator.next();
        }
    }

    protected class BreadthIterator implements Iterator<Integer> {

        private int index;

        public BreadthIterator() {
            this(0);
        }

        public BreadthIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < adj.length;
        }

        @Override
        public Integer next() {
            return index++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractGraph digraph = (AbstractGraph) o;

        if (V != digraph.V) return false;
        if (E != digraph.E) return false;

        if (adj == digraph.adj)
            return true;
        if (adj == null || digraph.adj == null)
            return false;

        int length = adj.length;
        if (digraph.adj.length != length)
            return false;

        for (int i = 0; i < length; i++) {
            if (!adj[i].equals(digraph.adj[i]))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = V;
        result = 31 * result + E;
        result = 31 * result + Arrays.hashCode(adj);
        return result;
    }
}