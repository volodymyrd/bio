package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Useful methods for sorting
 *
 * Created by Volodymyr_Dotsenko on 9/19/2016.
 */
public class Helper {

    // is v < w ?
    public static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    public static <T> boolean less(T v, T w, Comparator<T> comparator) {
        return comparator.compare(v, w) < 0;
    }

    // exchange a[i] and a[j]
    public static <T> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // is the array a[] sorted?
    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    public static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    // is the array a[] sorted?
    public static <T> boolean isSorted(T[] a, Comparator<T> comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    public static <T> boolean isSorted(T[] a, Comparator<T> comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1], comparator)) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> List<T> merge(List<T> left, List<T> right) {
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

    public static <T extends Comparable<T>> void merge(T[] a, int lo, int mid, int hi) {
        Class<T[]> type = (Class<T[]>) a.getClass();
        T[] copy = (T[]) Array.newInstance(type.getComponentType(), a.length);
        System.arraycopy(a, lo, copy, lo, hi - lo + 1);

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = copy[j++];
            } else if (j > hi) {
                a[k] = copy[i++];
            } else if (less(copy[j], copy[i])) {
                a[k] = copy[j++];
            } else {
                a[k] = copy[i++];
            }
        }
    }

    static <T> T[] copyArraysToNewArray(T[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException("Parameter 'arrays' must be set");
        }
        int newLength = 0;
        for (int i = 0; i < arrays.length; i++) {
            newLength += arrays[i].length;
        }

        Class<T[]> type = (Class<T[]>) arrays[0].getClass();

        T[] copy = (T[]) Array.newInstance(type.getComponentType(), newLength);

        int pos = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, copy, pos, arrays[i].length);
            pos += arrays[i].length;
        }

        return copy;
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    public static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        T v = a[lo];
        while (true) {

            // find item on lo to swap
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }

            // find item on hi to swap
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;      // redundant since a[lo] acts as sentinel
                }
            }

            // check if pointers cross
            if (i >= j) {
                break;
            }

            exchange(a, i, j);
        }

        // put partitioning item v at a[j]
        exchange(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
}