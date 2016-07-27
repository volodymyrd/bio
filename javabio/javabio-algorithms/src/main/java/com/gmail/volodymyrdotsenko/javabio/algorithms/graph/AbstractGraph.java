package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedBag;
import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Abstract graph
 * <p>
 * Created by Volodymyr Dotsenko on 18.07.16.
 */
public abstract class AbstractGraph implements IGraph {

    protected int V;                      //number of vertices
    protected int E;                      //number of edges
    protected LinkedBag<Integer>[] adj;   //adjacency lists

    protected class DepthIterator implements Iterator<Integer> {
        private final int firstVertex;
        private final Iterator<Integer> iterator;
        private final int returnLastIfExist;
        private final boolean returnLastIfExistFlag;
        private LinkedStack<Integer> store;

        public DepthIterator() {
            this(0);
        }

        public DepthIterator(int firstVertex) {
            this(firstVertex, -1, false);
        }

        public DepthIterator(int firstVertex, int returnLast, boolean returnLastFlag) {
            this.firstVertex = firstVertex;
            iterator = adj[firstVertex].iterator();
            returnLastIfExist = returnLast;
            returnLastIfExistFlag = returnLastFlag;
            if (returnLastIfExistFlag)
                store = new LinkedStack();
        }

        @Override
        public boolean hasNext() {
            boolean exist = iterator.hasNext();

            if (store != null && !exist && store.size() > 0) {
                return true;
            } else
                return exist;
        }

        @Override
        public Integer next() {
            if (returnLastIfExistFlag)
                return getNext();
            else
                return iterator.next();
        }

        private Integer getNext() {
            if (iterator.hasNext()) {
                Integer it = iterator.next();
                if (it == returnLastIfExist) {
                    store.push(it);
                    return getNext();
                } else
                    return it;
            } else if (store.size() > 0) {
                return store.pop();
            } else
                return null;
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

    /**
     * Returns the degree of vertex <tt>v</tt>.
     *
     * @param v the vertex
     * @return the degree of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    @Override
    public int degree(int v) {
        return adj[v].size();
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