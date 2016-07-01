package com.gmail.volodymyrdotsenko.javabio.algorithms.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 01.07.16.
 */
public class LinkedQueueTest {

    private LinkedQueue<Integer> empty = new LinkedQueue();
    private LinkedQueue<Integer> one = new LinkedQueue();
    private LinkedQueue<Integer> tree = new LinkedQueue();

    @Before
    public void setUp() {
        one.enqueue(1);

        tree.enqueue(1);
        tree.enqueue(2);
        tree.enqueue(3);
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
    public void shouldEnqueueOneElement() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.enqueue(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());
    }

    @Test
    public void shouldEnqueueTwoElements() throws Exception {
        assertEquals(true, empty.isEmpty());

        empty.enqueue(1);

        assertEquals(false, empty.isEmpty());
        assertEquals(1, empty.size());

        empty.enqueue(2);

        assertEquals(false, empty.isEmpty());
        assertEquals(2, empty.size());
    }

    @Test
    public void shouldDequeueElement() throws Exception {
        assertEquals(false, one.isEmpty());
        assertEquals(1, one.size());

        assertEquals(new Integer(1), one.dequeue());

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

        int k = 1;
        for (Integer i : tree) {
            assertEquals(new Integer(k++), i);
        }
    }
}