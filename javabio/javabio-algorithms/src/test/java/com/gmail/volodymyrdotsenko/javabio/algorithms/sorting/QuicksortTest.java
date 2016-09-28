package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Volodymyr_Dotsenko on 9/28/2016.
 */
public class QuicksortTest {

    private final int size = 100;
    private final Random random = new Random();

    @Test
    public void shouldSortAnyInteger() {
        List<Integer> integers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            integers.add(random.nextInt());
        }
        assertFalse(Helper.isSorted(integers.toArray(new Integer[size])));
        integers = Quicksort.sort(integers);
        assertTrue(Helper.isSorted(integers.toArray(new Integer[size])));
    }
}
