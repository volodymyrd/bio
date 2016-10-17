package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.Arrays;

/**
 * The {@code Heap} class provides a static methods for heapsorting
 * an array.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Heapsort {

    // This class should not be instantiated.
    private Heapsort() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param pq the array to be sorted
     */
    public static <T extends Comparable> void sort(T[] pq) {
        int n = pq.length;
        for (int k = n / 2; k >= 0; k--) {
            sink(pq, k, n);
        }
        while (n > 0) {
            Helper.exchange(pq, 0, n-1);
            n--;
            sink(pq, 0, n);
        }
    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    static <T extends Comparable> void sink(T[] pq, int k, int n) {
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j < n - 1 && Helper.less(pq[j], pq[j + 1])) {
                j++;
            }
            if (!Helper.less(pq[k], pq[j])) {
                break;
            }
            Helper.exchange(pq, k, j);
            k = j;
        }
    }
}