package com.gmail.volodymyrdotsenko.javabio.algorithms.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 01.07.16.
 */
public class ResizingArrayStackTest {

    private ResizingArrayStack<Integer> empty = new ResizingArrayStack();
    private ResizingArrayStack<Integer> one = new ResizingArrayStack();
    private ResizingArrayStack<Integer> tree = new ResizingArrayStack();

    @Before
    public void setUp() {
        one.push(1);

        tree.push(1);
        tree.push(2);
        tree.push(3);
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
    public void shouldPushOneElement() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.push(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());
    }

    @Test
    public void shouldPushTwoElements() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.push(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());

        empty.push(2);

        assertEquals(false, empty.isEmpty());
        assertEquals(2, empty.size());
    }

    @Test
    public void shouldPopElement() throws Exception {
        assertEquals(false, one.isEmpty());
        assertEquals(1, one.size());

        assertEquals(new Integer(1), one.pop());

        assertEquals(true, one.isEmpty());
        assertEquals(0, one.size());
    }

    @Test
    public void shouldPeekElement() throws Exception {
        assertEquals(false, one.isEmpty());
        assertEquals(1, one.size());

        assertEquals(new Integer(1), one.peek());

        assertEquals(false, one.isEmpty());
        assertEquals(1, one.size());
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