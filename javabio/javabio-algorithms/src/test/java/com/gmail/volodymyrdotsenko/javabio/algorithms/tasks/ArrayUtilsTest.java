package com.gmail.volodymyrdotsenko.javabio.algorithms.tasks;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 9/29/2016.
 */
public class ArrayUtilsTest {

    @Test
    public void shouldReverseArrayOfIntegers() {
        Integer[] a = new Integer[]{1, 3, 5, 7, 8, 9};
        assertArrayEquals(new Integer[]{9, 8, 7, 5, 3, 1}, ArrayUtils.reverse(a, 0, a.length));
    }
    
    @Test
    public void shouldPartlyReverseArrayOfIntegers() {
        Integer[] a = new Integer[]{1, 3, 5, 7, 8, 9};
        assertArrayEquals(new Integer[]{1, 7, 5, 3, 8, 9}, ArrayUtils.reverse(a, 1, 4));
    }
    
    @Test
    public void shouldRotateArrayOfIntegers() {
        Integer[] a = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        assertArrayEquals(new Integer[]{5, 6, 7, 1, 2, 3, 4}, ArrayUtils.rotate(a, 3));
    }
}