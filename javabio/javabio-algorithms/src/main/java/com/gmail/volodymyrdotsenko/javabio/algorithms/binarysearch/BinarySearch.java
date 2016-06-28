package com.gmail.volodymyrdotsenko.javabio.algorithms.binarysearch;

import java.util.Arrays;

/**
 * Created by Volodymyr Dotsenko on 6/28/16.
 */
public class BinarySearch {
    private final int[] a;

    public BinarySearch(int[] a) {
        this.a = a;

        Arrays.sort(a);
    }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param key the search key
     * @return index of key in array <tt>a</tt> if present; <tt>-1</tt> otherwise
     */
    public int indexOf(int key) {
        int lo = 0;
        int hi = a.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid])
                hi = mid - 1;
            else if (key > a[mid])
                lo = mid + 1;
            else
                return mid;
        }

        return -1;
    }
}