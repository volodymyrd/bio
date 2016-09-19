package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.Comparator;

/**
 * The {@code Selection} class provides static methods for sorting an
 * array using selection sort.
 * <p>
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Selection {

    // This class should not be instantiated.
    private Selection() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (Helper.less(a[j], a[min])) {
                    min = j;
                }
            }
            Helper.exchange(a, i, min);
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
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (Helper.less(a[j], a[min], comparator)) {
                    min = j;
                }
            }
            Helper.exchange(a, i, min);
        }
    }
}
