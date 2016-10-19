package com.gmail.volodymyrdotsenko.javabio.algorithms.union;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Volodymyr_Dotsenko on 10/19/2016.
 */
public interface IUnion<T> {

    void union(int p, int q);

    boolean connected(int p, int q);

    T[] ids();

    int[] size() throws OperationNotSupportedException;
}