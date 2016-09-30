package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 9/30/2016.
 */
public class FactorialTest {

    @Test
    public void shouldRetutn1() {
        assertEquals(1, Factorial.calculate(0), 0);
        assertEquals(1, Factorial.calculate(1), 0);
    }

    @Test
    public void shouldRetutn2() {
        assertEquals(2.0, Factorial.calculate(2), 0);
    }

    @Test
    public void shouldRetutn125Factorial() {
        assertEquals(1.882677176888926E209, Factorial.calculate(125), 0);
    }
}
