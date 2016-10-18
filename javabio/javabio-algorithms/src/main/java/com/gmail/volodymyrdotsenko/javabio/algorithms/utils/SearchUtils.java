package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import com.gmail.volodymyrdotsenko.javabio.algorithms.sorting.Helper;
import com.sun.deploy.ref.Helpers;

import java.util.Objects;

/**
 * Created by Volodymyr_Dotsenko on 10/18/2016.
 */
public class SearchUtils {

    public static <T extends Comparable<T>> T[] findNMaxElements(T[] a, int n) {
        for (int i = 0; i < n; i++) {
            T max = a[i];
            for (int k = i + 1; k < a.length; k++) {
                if (max.compareTo(a[k]) < 0) {
                    max = a[k];
                    a[k] = a[i];
                    a[i] = max;
                }
            }
        }
        return a;
    }

    public static <T extends Comparable<T>> T[] findNMinElementsFaster(T[] a, int n) {
        int l0 = 0;
        int hi = a.length - 1;
        while (hi > l0) {
            int j = Helper.partition(a, l0, hi);
            if (j == n) {
                return a;
            } else if (j > n) {
                hi = j - 1;
            } else if (j < n) {
                l0 = j + 1;
            }
        }
        return a;
    }
}