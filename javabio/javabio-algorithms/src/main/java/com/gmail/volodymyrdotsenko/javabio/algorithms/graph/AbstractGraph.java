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

    protected class VertexIterator {
        private final int firstVertex;
        private Iterator<Integer> depthIterator;

        public VertexIterator() {
            this(0);
        }

        public VertexIterator(int firstVertex) {
            this.firstVertex = firstVertex;
            depthIterator = adj[firstVertex].iterator();
        }

        public boolean hasDepthNextVertex(){

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