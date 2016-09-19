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
public class InsertionTest {

    private final int size = 100;
    private final Random random = new Random();

    @Test
    public void shouldSortAnyInteger() {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt();
        }
        assertFalse(Helper.isSorted(integers));
        Insertion.sort(integers);
        assertTrue(Helper.isSorted(integers));
    }

    @Test
    public void shouldSortAnyDouble() {
        Double[] doubles = new Double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = random.nextDouble();
        }
        assertFalse(Helper.isSorted(doubles));
        Insertion.sort(doubles);
        assertTrue(Helper.isSorted(doubles));
    }

    @Test
    public void shouldSortAnyString() {
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        assertFalse(Helper.isSorted(strings));
        Insertion.sort(strings);
        assertTrue(Helper.isSorted(strings));
    }

    private class DescInteger {

        public DescInteger(Integer integer) {
            this.integer = integer;
        }

        private final Integer integer;

        @Override
        public String toString() {
            return String.valueOf(integer);
        }
    }

    @Test
    public void shouldSortWithComparator() {
        DescInteger[] descIntegers = new DescInteger[size];
        for (int i = 0; i < size; i++) {
            descIntegers[i] = new DescInteger(random.nextInt());
        }
        Comparator<DescInteger> comparator = (DescInteger o1, DescInteger o2) -> o2.integer.compareTo(o1.integer);
        assertFalse(Helper.isSorted(descIntegers, comparator));
        Insertion.sort(descIntegers, comparator);
        assertTrue(Helper.isSorted(descIntegers, comparator));
    }
}
