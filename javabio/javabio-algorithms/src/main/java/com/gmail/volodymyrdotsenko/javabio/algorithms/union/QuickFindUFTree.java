package com.gmail.volodymyrdotsenko.javabio.algorithms.union;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Volodymyr_Dotsenko on 10/19/2016.
 */
public class QuickFindUFTree implements IUnion<Integer> {

    private final Integer[] id;
    private final int size[];

    public QuickFindUFTree(int n) {
        this.id = new Integer[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i) {
        while (id[i] != i) {
            i = id[i];
        }

        return i;
    }

    @Override
    public void union(int p, int q) {
        int rp = root(p);
        int rq = root(q);
        if (rp != rq) {
            id[rp] = rq;
            size[rq] += size[rp];
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public Integer[] ids() {
        return id;
    }

    @Override
    public int[] size() throws OperationNotSupportedException {
        return size;
    }
}