package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Comparator;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Volodymyr_Dotsenko on 9/19/2016.
 */
public class ShellTest {

    private final int size = 100;
    private final Random random = new Random();

    @Test
    public void demo() {
        Integer[] integers = new Integer[]{61, 109, 149, 111, 34, 2, 24, 119, 122, 125, 27, 145};
        assertFalse(Helper.isSorted(integers));
        Shell.sort(integers);
        assertTrue(Helper.isSorted(integers));
    }

    @Test
    public void shouldSortAnyInteger() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        assertFalse(Helper.isSorted(integers));
        Shell.sort(integers);
        assertTrue(Helper.isSorted(integers));
    }

    @Test
    public void shouldSortAnyDouble() {
        Double[] doubles = new Double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = random.nextDouble();
        }
        assertFalse(Helper.isSorted(doubles));
        Shell.sort(doubles);
        assertTrue(Helper.isSorted(doubles));
    }

    @Test
    public void shouldSortAnyString() {
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        assertFalse(Helper.isSorted(strings));
        Shell.sort(strings);
        assertTrue(Helper.isSorted(strings));
    }
}
