package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.Comparator;

/**
 * The {@code Insertion} class provides static methods for sorting an
 * array using insertion sort.
 * <p>
 * This implementation makes ~ 1/2 n^2 compares and exchanges in
 * the worst case, so it is not suitable for sorting large arbitrary arrays.
 * More precisely, the number of exchanges is exactly equal to the number
 * of inversions. So, for example, it sorts a partially-sorted array
 * in linear time.
 * <p>
 * The sorting algorithm is stable and uses O(1) extra memory.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Insertion {

    // This class should not be instantiated.
    private Insertion() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static <T extends Comparable> void sort(T[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && Helper.less(a[j], a[j - 1]); j--) {
                Helper.exchange(a, j, j - 1);
            }
        }
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     *
     * @param a          the array
     * @param comparator the comparator specifying the order
     */
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && Helper.less(a[j], a[j - 1], comparator); j--) {
                Helper.exchange(a, j, j - 1);
            }
        }
    }
}
