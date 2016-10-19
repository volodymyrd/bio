package com.gmail.volodymyrdotsenko.javabio.algorithms.union;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Volodymyr_Dotsenko on 10/19/2016.
 */
public class QuickFindUF implements IUnion<Integer> {

    private Integer[] id;

    public QuickFindUF(int n) {
        this.id = new Integer[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public Integer[] ids() {
        return id;
    }

    @Override
    public int[] size() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}