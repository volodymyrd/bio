package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 10/14/2016.
 */
public class RBTTest {

    private RBT<Integer, Integer> one = new RBT<>();

    @Before
    public void setUp() {
        one.put(1, 1);
        one.put(2, 2);
        one.put(3, 3);
        one.put(4, 4);
        one.put(5, 5);
        one.put(6, 6);
        one.put(7, 7);
    }

    @Test
    public void shouldPrintTree() {
        System.out.println(one.print());
    }
}