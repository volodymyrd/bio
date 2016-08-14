package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public class PermutationsWithRepetitionsIteratorTest {

    @Test
    public void shouldCreatePermutations() {
        char[] symbols = {'A', 'B'};

        int[][] result = new int[4][];

        PermutationsWithRepetitionsIterator iterator = new PermutationsWithRepetitionsIterator(2, 2);
        int i = 0;
        while (iterator.hasNext()) {
            int[] temp = iterator.next();
            result[i++] = Arrays.copyOf(temp, temp.length);
        }

        assertArrayEquals(result, new int[][]{{1, 0}, {0, 1}, {1, 1}, {0, 0}});
    }
}