package com.gmail.volodymyrdotsenko.javabio.algorithms.tasks;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 9/29/2016.
 */
public class ProjecteulerNetTest {

    @Test
    public void sholdBePolindrome9009() {
        assertTrue(ProjecteulerNet.isPolindrome(9009));
    }

    @Test
    public void sholdBePolindromeString9009() {
        assertTrue(ProjecteulerNet.isPolindrome("9009"));
    }

    @Test
    public void shouldReverse12() {
        assertEquals(21, ProjecteulerNet.reverse(12));
    }

    @Test
    public void shouldReverse123456789() {
        assertEquals(987654321, ProjecteulerNet.reverse(123456789));
    }

}
