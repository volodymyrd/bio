package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Quicksort} class provides static methods for sorting an
 * array using quicksort algorithm
 */
public class Quicksort {

    // This class should not be instantiated.
    private Quicksort() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static <T extends Comparable<T>> List<T> sort(List<T> a) {
        if (a.size() < 2) {
            return a;
        }

        final T pivot = a.get(0);
        final List<T> lower = new ArrayList<>();
        final List<T> higher = new ArrayList<>();

        for (int i = 1; i < a.size(); i++) {
            if (a.get(i).compareTo(pivot) < 0) {
                lower.add(a.get(i));
            } else {
                higher.add(a.get(i));
            }
        }

        final List<T> sorted = sort(lower);
        sorted.add(pivot);
        sorted.addAll(sort(higher));

        return sorted;
    }
}
