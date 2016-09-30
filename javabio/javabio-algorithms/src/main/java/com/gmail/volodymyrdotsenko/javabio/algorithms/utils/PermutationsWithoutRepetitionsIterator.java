package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public class PermutationsWithoutRepetitionsIterator implements Iterator<int[]> {

    private long total;
    private final int K;
    private final int n;
    private final int[] indexes;
    private int index = 0;

    public PermutationsWithoutRepetitionsIterator(int n, int k) {
        if (n < k || n  < 0) {
            throw new IllegalArgumentException("Illegal number of positions.");
        }

        K = k;

        total = (long) (Factorial.calculate(n) / Factorial.calculate(n - k) / Factorial.calculate(k));
        this.n = n;

        indexes = new int[K];
    }

    @Override
    public boolean hasNext() {
        return total > 0;
    }

    @Override
    public int[] next() {
        total--;

        for (int i = 0; i < K; i++) {
            if (indexes[i] >= n - 1) {
                indexes[i] = ++index;
            } else {
                if(indexes[i] < index)
                    indexes[i] = index;
                indexes[i]++;
                break;
            }
        }

        return indexes;
    }

    public static int[][] getAllPermutationsWithoutRepetitions(int n, int k) {
        PermutationsWithoutRepetitionsIterator iterator = new PermutationsWithoutRepetitionsIterator(n, k);
        int[][] permutations = new int[(int) iterator.total][];

        int i = 0;
        while (iterator.hasNext()) {
            int[] temp = iterator.next();
            permutations[i++] = Arrays.copyOf(temp, temp.length);
        }

        return permutations;
    }
}
