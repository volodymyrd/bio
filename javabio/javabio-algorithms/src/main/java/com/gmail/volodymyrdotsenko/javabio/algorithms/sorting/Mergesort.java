package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import java.util.List;

/**
 * The {@code Mergesort} class provides static methods for sorting an array
 * using mergesort algorithm
 */
public class Mergesort {

	// This class should not be instantiated.
	private Mergesort() {
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
        
        final List<T> leftHalf = a.subList(0, a.size() / 2);
        final List<T> rightHalf = a.subList(a.size() / 2, a.size());
        
        return Helper.merge(sort(leftHalf), sort(rightHalf));
    }
}