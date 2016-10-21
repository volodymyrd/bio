package com.gmail.volodymyrdotsenko.javabio.algorithms.sorting;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Volodymyr_Dotsenko on 9/28/2016.
 */
public class HelperTest {

    @Test
    public void partition() {
        // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
        // and return the index j.
        Integer[] a = new Integer[]{12, 25, 10, 100, -11, 17, 15, 1, 99};
        System.out.println(Helper.partition(a, 0, a.length - 1));
        System.out.println(Arrays.toString(a));
    }

    @Test
    public void shouldMerge1() throws Exception {
        List<Integer> list2 = Stream.of(1, 4, 7).collect(Collectors.toList());
        List<Integer> list1 = Stream.of(2, 3, 5, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 4, 5, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge2() throws Exception {
        List<Integer> list1 = Stream.of(1, 4, 7).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(2, 3, 5, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 4, 5, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge3() throws Exception {
        List<Integer> list1 = Stream.of(1, 2, 3).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(5, 6, 7, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 5, 6, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldMerge4() throws Exception {
        List<Integer> list2 = Stream.of(1, 2, 3).collect(Collectors.toList());
        List<Integer> list1 = Stream.of(5, 6, 7, 10).collect(Collectors.toList());

        assertEquals(Stream.of(1, 2, 3, 5, 6, 7, 10).collect(Collectors.toList()), Helper.merge(list1, list2));
    }

    @Test
    public void shouldCopyArraysOfInteger() {
        Integer[] a1 = new Integer[]{1, 2, 3};
        Integer[] a2 = new Integer[]{4, 5};
        Integer[] a3 = new Integer[]{6, 7, 8, 9};
        Integer[] a4 = new Integer[]{10};

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                          Helper.copyArraysToNewArray(a1, a2, a3, a4));
    }

    @Test
    public void shouldCopyArraysOfString() {
        String[] a1 = new String[]{"1", "2", "3"};
        String[] a2 = new String[]{};
        String[] a3 = new String[]{"4", "5"};
        String[] a4 = new String[]{"6", "7", "8", "9"};
        String[] a5 = new String[]{"10"};

        assertArrayEquals(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                          Helper.copyArraysToNewArray(a1, a2, a3, a4, a5));
    }

    @Test
    public void shouldMergeStringArrays() {
        String[] a = new String[]{"A", "G", "L", "O", "R", "H", "I", "M", "S", "T"};
        Helper.merge(a, 0, 4, a.length - 1);
        System.out.println(Arrays.toString(a));
    }
}