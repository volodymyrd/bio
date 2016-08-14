package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public class PermutationsWithRepetitionsIterator implements Iterator<int[]> {
    private long total;
    private final int N;
    private final int n;
    private final int[] indexes;

    public PermutationsWithRepetitionsIterator(int n, int k) {
        if (k < 1)
            throw new IllegalArgumentException("Illegal number of positions.");

        N = n >= k ? n : k;

        total = (long) Math.pow(n, k);
        this.n = n;

        indexes = new int[N];
    }

    @Override
    public boolean hasNext() {
        return total > 0;
    }

    @Override
    public int[] next() {
        total--;

        for (int i = 0; i < N; i++) {
            if (indexes[i] >= n - 1) {
                indexes[i] = 0;
            } else {
                indexes[i]++;
                break;
            }
        }

        return indexes;
    }

    public static int[][] getAllPermutationsWithRepetitionsIterator(int n, int k) {
        PermutationsWithRepetitionsIterator iterator = new PermutationsWithRepetitionsIterator(n, k);
        int[][] permutations = new int[(int) iterator.total][];

        int i = 0;
        while (iterator.hasNext()) {
            int[] temp = iterator.next();
            permutations[i++] = Arrays.copyOf(temp, temp.length);
        }

        return permutations;
    }
}