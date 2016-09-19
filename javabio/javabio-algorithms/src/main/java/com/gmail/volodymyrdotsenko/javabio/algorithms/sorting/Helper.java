package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.Comparator;

/**
 * Useful methods for sorting
 *
 * Created by Volodymyr_Dotsenko on 9/19/2016.
 */
class Helper {

    // is v < w ?
    static <T extends Comparable<T>>  boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    static <T, C extends Comparator<T>> boolean less(C comparator, T v, T w) {
        return comparator.compare(v, w) < 0;
    }

    // exchange a[i] and a[j]
    static <T> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // is the array a[] sorted?
    static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array a[] sorted?
    static <T, C extends Comparator<T>> boolean isSorted(T[] a, C comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    static <T, C extends Comparator<T>> boolean isSorted(T[] a, C comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i-1])) return false;
        return true;
    }
}
