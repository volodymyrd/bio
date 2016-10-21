package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Volodymyr_Dotsenko on 9/28/2016.
 */
public class MergesortTest {

    private final int size = 100;
    private final Random random = new Random();

    @Test
    public void shouldSortAnyIntegerList() {
        List<Integer> integers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            integers.add(random.nextInt());
        }
        assertFalse(Helper.isSorted(integers.toArray(new Integer[size])));
        integers = Mergesort.sort(integers);
        assertTrue(Helper.isSorted(integers.toArray(new Integer[size])));
    }

    @Test
    public void shouldSortAnyInteger() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        assertFalse(Helper.isSorted(integers));
        Mergesort.sort(integers);
        assertTrue(Helper.isSorted(integers));
    }

    @Test
    public void shouldSortAnyDouble() {
        Double[] doubles = new Double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = random.nextDouble();
        }
        assertFalse(Helper.isSorted(doubles));
        Mergesort.sort(doubles);
        assertTrue(Helper.isSorted(doubles));
    }

    @Test
    public void shouldSortAnyString() {
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        assertFalse(Helper.isSorted(strings));
        Mergesort.sort(strings);
        assertTrue(Helper.isSorted(strings));
    }
}
