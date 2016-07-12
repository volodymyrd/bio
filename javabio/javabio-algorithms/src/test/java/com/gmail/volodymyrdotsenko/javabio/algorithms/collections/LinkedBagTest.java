package com.gmail.volodymyrdotsenko.javabio.algorithms.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 01.07.16.
 */
public class LinkedBagTest {

    private LinkedBag<Integer> empty = new LinkedBag();
    private LinkedBag<Integer> tree = new LinkedBag();

    @Before
    public void setUp() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
    }

    @Test
    public void shouldBeEmpty() throws Exception {
        assertEquals(true, empty.isEmpty());
    }

    @Test
    public void shouldBeHasZeroSize() throws Exception {
        assertEquals(0, empty.size());
    }

    @Test
    public void shouldAddOneElement() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.add(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());
    }

    @Test
    public void shouldAddTwoElements() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.add(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());

        empty.add(2);

        assertEquals(false, empty.isEmpty());
        assertEquals(2, empty.size());
    }

    @Test
    public void shouldIterator() throws Exception {
        assertEquals(false, tree.isEmpty());
        assertEquals(3, tree.size());

        int k = 3;
        for (Integer i : tree) {
            assertEquals(new Integer(k--), i);
        }
    }
}