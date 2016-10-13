package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Volodymyr_Dotsenko on 10/13/2016.
 */
public class BSTTest {

    private BST<Integer, Integer> one = new BST<>();
    private BST<Integer, Integer> two = new BST<>();
    private BST<Integer, Integer> three = new BST<>();
    private BST<Integer, Integer> five = new BST<>();
    private BST<Integer, Integer> fiveDisBalance = new BST<>();

    @Before
    public void setUp() {
        one.put(0, 0);

        two.put(0, 0);
        two.put(1, 1);

        three.put(2, 2);
        three.put(1, 1);
        three.put(3, 3);

        five.put(3, 3);
        five.put(1, 1);
        five.put(2, 2);
        five.put(4, 4);
        five.put(5, 5);

        fiveDisBalance.put(1, 1);
        fiveDisBalance.put(2, 2);
        fiveDisBalance.put(3, 3);
        fiveDisBalance.put(4, 4);
        fiveDisBalance.put(5, 5);
    }

    @Test
    public void shouldPrintTree() {
        System.out.println(fiveDisBalance.print());
    }
}