package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Volodymyr_Dotsenko on 10/17/2016.
 */
public class HeapsortTest {

    private Integer[] array1 = new Integer[]{2, 1, 7, 4, 3};

    public void setUp() {

    }

    //@Test
    public void sink() {
        Heapsort.sort(array1);
        //System.out.println(Arrays.toString(array1));
    }

    private final int size = 10;
    private final Random random = new Random();

    @Test
    public void shouldSortAnyInteger() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        assertFalse(Helper.isSorted(integers));
        Heapsort.sort(integers);
        assertTrue(Helper.isSorted(integers));
    }

    @Test
    public void shouldSortAnyDouble() {
        Double[] doubles = new Double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = random.nextDouble();
        }
        assertFalse(Helper.isSorted(doubles));
        Heapsort.sort(doubles);
        assertTrue(Helper.isSorted(doubles));
    }

    @Test
    public void shouldSortAnyString() {
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        assertFalse(Helper.isSorted(strings));
        Heapsort.sort(strings);
        assertTrue(Helper.isSorted(strings));
    }
}