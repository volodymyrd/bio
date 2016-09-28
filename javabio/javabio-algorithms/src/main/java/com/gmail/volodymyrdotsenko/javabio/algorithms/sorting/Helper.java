package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Useful methods for sorting
 *
 * Created by Volodymyr_Dotsenko on 9/19/2016.
 */
class Helper {

    // is v < w ?
    static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    static <T> boolean less(T v, T w, Comparator<T> comparator) {
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
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    // is the array a[] sorted?
    static <T> boolean isSorted(T[] a, Comparator<T> comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    static <T> boolean isSorted(T[] a, Comparator<T> comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1], comparator)) {
                return false;
            }
        }
        return true;
    }

    static <T extends Comparable<T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<T>(left.size() + right.size());
        int leftPos = 0;
        int rightPos = 0;

        while (leftPos < left.size() || rightPos < right.size()) {
            if (leftPos < left.size() && rightPos < right.size()
                && left.get(leftPos).compareTo(right.get(rightPos)) < 0 ||
                rightPos >= right.size() && leftPos < left.size()) {
                merged.add(left.get(leftPos++));
            } else if (rightPos < right.size()) {
                merged.add(right.get(rightPos++));
            }
        }
        return merged;
    }
}
