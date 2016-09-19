package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

/**
 * The {@code Shell} class provides static methods for sorting an
 * array using Shellsort with Knuth's increment sequence (1, 4, 13, 40, ...).
 * <p>
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Shell {

    // This class should not be instantiated.
    private Shell() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static <T extends Comparable> void sort(T[] a) {
        int n = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && Helper.less(a[j], a[j - h]); j -= h) {
                    Helper.exchange(a, j, j - h);
                }
            }
            h /= 3;
        }
    }
}
