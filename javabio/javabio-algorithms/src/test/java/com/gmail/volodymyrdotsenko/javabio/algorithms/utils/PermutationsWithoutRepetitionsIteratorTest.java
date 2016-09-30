package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Volodymyr_Dotsenko on 9/30/2016.
 */
public class PermutationsWithoutRepetitionsIteratorTest {
    @Test
    public void shouldCreatePermutations() {
        int[][] result = new int[6][];

        PermutationsWithoutRepetitionsIterator iterator = new PermutationsWithoutRepetitionsIterator(4, 2);
        int i = 0;
        while (iterator.hasNext()) {
            int[] temp = iterator.next();
            result[i++] = Arrays.copyOf(temp, temp.length);
        }

        assertArrayEquals(new int[][]{{1, 0}, {0, 1}, {1, 1}, {0, 0}}, result);
    }
}
