package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Volodymyr_Dotsenko on 10/18/2016.
 */
public class SearchUtilsTest {

    private final int size = 100;
    private final int k = 5;
    private final Random random = new Random();

    private Integer[] maxCheck1 = new Integer[k];
    private  Integer[] maxCheck2 = new Integer[k];

    @Test
    public void shouldFindKMaxElementsInIntegerArray() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        Integer[] max = SearchUtils.findNMaxElements(integers, k);
        Arrays.sort(integers);
        for (int i = integers.length - 1, n = 0; n < k; i--, n++) {
            maxCheck1[n] = integers[i];
            maxCheck2[n] = max[i];
        }

        assertArrayEquals(maxCheck1, maxCheck2);
    }

    @Test
    public void shouldFindKMinElementsInIntegerArray() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        Integer[] max = SearchUtils.findNMinElementsFaster(integers, k);
        Arrays.sort(integers);
        for (int n = 0; n < k; n++) {
            maxCheck1[n] = integers[n];
            maxCheck2[n] = max[n];
        }
        assertArrayEquals(maxCheck1, maxCheck2);
    }
}