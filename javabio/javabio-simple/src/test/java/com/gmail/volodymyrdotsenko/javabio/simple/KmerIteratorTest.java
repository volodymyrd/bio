package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Volodymyr Dotsenko on 04.07.16.
 */
public class KmerIteratorTest {

    @Test
    public void shouldCreateLmerList() {
        assertEquals("[AB, BC, CD, DE, EF]", new KmerIterator("ABCDEF", 2).getList().toString());
    }

    @Test
    public void shouldCreateLmerArray() {
        assertEquals("[AB, BC, CD, DE, EF]", Arrays.toString(new KmerIterator("ABCDEF", 2).getArray()));
    }

    @Test
    public void shouldIterateKmer() {
        String text = "CGCCCGAATCCAGAACGCATTCCCATATTTCGGGACCACTGGCCTCCACGGTACGGACGTCAATCAAAT";
        KmerIterator iterator = new KmerIterator(text, 3);
        int n = text.length();
        for (int i = 0; i < n - 3 + 1; i++) {
            assertTrue(text.substring(i, i + 3).equals(iterator.next()));
        }
    }
}