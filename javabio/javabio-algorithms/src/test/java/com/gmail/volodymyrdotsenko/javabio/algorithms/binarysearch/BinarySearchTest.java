package com.gmail.volodymyrdotsenko.javabio.algorithms.binarysearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/28/16.
 */
public class BinarySearchTest {

    private BinarySearch bs;
    private int a[] = new int[]{101, 3, 125, 34, 1, 93};

    @Before
    public void setUp() {
        bs = new BinarySearch(a);
    }

    @Test
    public void shouldFindElement() {
        BinarySearch bs = new BinarySearch(a);
        assertEquals(3, a[bs.indexOf(3)]);
    }

    @Test
    public void shouldFailed() {
        BinarySearch bs = new BinarySearch(a);
        assertEquals(-1, bs.indexOf(20));
    }
}