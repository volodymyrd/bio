package com.gmail.volodymyrdotsenko.javabio.algorithms.heap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 11/8/2016.
 */
public class IndexMinPQTest {

    private final String[] strings = {"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};
    private IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);

    @Before
    public void setUp() {
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        assertFalse(pq.isEmpty());
        assertEquals(10, pq.size());
    }

    @Test
    public void shouldBeFirst() {
        pq = new IndexMinPQ<String>(strings.length + 1);
        setUp();
        pq.insert(pq.size(), "a");
        assertEquals("a", pq.minKey());
        assertEquals(10, pq.minIndex());
        assertFalse(pq.isEmpty());
        assertEquals(11, pq.size());
    }

    @Test
    public void shouldIterateElements() {
        int index = 1;
        for (int i : pq) {
            check(i, index++);
        }

        assertFalse(pq.isEmpty());
        assertEquals(10, pq.size());
    }

    @Test
    public void shouldDeleteElementsInOrder() {
        int index = 1;
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            check(i, index++);
        }
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    private void check(int i, int index) {
        switch (index) {
            case 1:
                assertEquals(3, i);
                assertEquals("best", strings[i]);
                break;
            case 2:
                assertEquals(0, i);
                assertEquals("it", strings[i]);
                break;
            case 3:
                assertEquals(6, i);
                assertEquals("it", strings[i]);
                break;
            case 4:
                assertEquals(4, i);
                assertEquals("of", strings[i]);
                break;
            case 5:
                assertEquals(8, i);
                assertEquals("the", strings[i]);
                break;
            case 6:
                assertEquals(2, i);
                assertEquals("the", strings[i]);
                break;
            case 7:
                assertEquals(5, i);
                assertEquals("times", strings[i]);
                break;
            case 8:
                assertEquals(7, i);
                assertEquals("was", strings[i]);
                break;
            case 9:
                assertEquals(1, i);
                assertEquals("was", strings[i]);
                break;
            case 10:
                assertEquals(9, i);
                assertEquals("worst", strings[i]);
                break;
            default:
                assertFalse(true);
        }
    }
}