package com.gmail.volodymyrdotsenko.javabio.algorithms.extremumfind;

import java.util.concurrent.RecursiveTask;

/**
 * The {@code FindMaximum} class provides static methods for find maximum in an
 * array
 */
public class FindMaximum {
	// This class should not be instantiated.
	private FindMaximum() {
	}

	/**
	 * Find maximum value in array.
	 *
	 * @param a
	 *            the array
	 */
	public static <T extends Comparable<T>> T max(T[] a) {
		if (a == null || a.length == 0)
			throw new IllegalArgumentException("array must be set");

		if (a.length == 1)
			return a[0];

		T max = a[0];

		for (int i = 1; i < a.length; i++) {
			if (a[i].compareTo(max) > 0)
				max = a[i];
		}

		return max;
	}

	/**
	 * Find maximum value in array using fork-join framework.
	 *
	 * @param a
	 *            the array
	 */
	public static <T extends Comparable<T>> T maxWithForkJoin(T[] a) {
		return new FindMaxTask<T>(a, 0, a.length, a.length / 2).compute();
	}

	public static class FindMaxTask<T extends Comparable<T>> extends RecursiveTask<T> {

		private static final long serialVersionUID = 1L;

		private final T[] array;
		private final int start;
		private final int end;
		private final int THRESHOLD;
		
		public FindMaxTask(T[] array, int start, int end, int threshold) {
			this.array = array;
			this.start = start;
			this.end = end;
			this.THRESHOLD = threshold;
		}

		@Override
		protected T compute() {
			if (end - start <= THRESHOLD) {
				return max(array);
			} else {
				// number of elements is above the threshold - split into
				// subtasks
				int mid = start + (end - start) / 2;
				FindMaxTask<T> left = new FindMaxTask<>(array, start, mid, THRESHOLD);
				FindMaxTask<T> right = new FindMaxTask<>(array, mid, end, THRESHOLD);

				// invoke the tasks in parallel and wait for them to complete
				invokeAll(left, right);

				// maximum of overall range is maximum of sub-ranges
				return max(left.join(), right.join());
			}
		}
	}

	/**
	 * Returns the greater of two {@code int} values. If the arguments have the
	 * same value, the result is that same value.
	 *
	 * @param a
	 *            an argument.
	 * @param b
	 *            another argument.
	 * @return the larger of {@code a} and {@code b}.
	 */
	public static <T extends Comparable<T>> T max(T a, T b) {
		return (a.compareTo(b) >= 0) ? a : b;
	}
}
