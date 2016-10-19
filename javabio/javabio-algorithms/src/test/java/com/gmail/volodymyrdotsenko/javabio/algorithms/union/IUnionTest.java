package com.gmail.volodymyrdotsenko.javabio.algorithms.union;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Volodymyr_Dotsenko on 10/19/2016.
 */
public class IUnionTest {

    private final static int N = 10;
    private IUnion simple;
    private IUnion tree;
    private IUnion weightTree;

    @Before
    public void setUp() {
        simple = new QuickFindUF(N);
        simple.union(2, 9);
        simple.union(4, 9);
        simple.union(3, 4);
        simple.union(5, 6);

        tree = new QuickFindUFTree(N);
        tree.union(2, 9);
        tree.union(4, 9);
        tree.union(3, 4);
        tree.union(5, 6);

        weightTree = new QuickFindUFWeightTree(N);
        weightTree.union(2, 9);
        weightTree.union(4, 9);
        weightTree.union(3, 4);
        weightTree.union(5, 6);
    }

    @Test
    public void shouldUnion() throws Exception {
        tree.union(2, 9);
        tree.union(4, 9);
        tree.union(3, 4);
        tree.union(5, 6);

        weightTree.union(2, 9);
        weightTree.union(4, 9);
        weightTree.union(3, 4);
        weightTree.union(5, 6);

        System.out.println("tree size: " + Arrays.toString(tree.size()));
        System.out.println("weightTree size: " + Arrays.toString(weightTree.size()));
    }

    @Test
    public void shouldBeConnected() {
        assertTrue(simple.connected(2, 9));
        assertTrue(simple.connected(4, 9));
        assertTrue(simple.connected(3, 4));
        assertTrue(simple.connected(5, 6));
        assertFalse(simple.connected(1, 2));

        assertTrue(tree.connected(2, 9));
        assertTrue(tree.connected(4, 9));
        assertTrue(tree.connected(3, 4));
        assertTrue(tree.connected(5, 6));
        assertFalse(tree.connected(1, 2));

        assertTrue(weightTree.connected(2, 9));
        assertTrue(weightTree.connected(4, 9));
        assertTrue(weightTree.connected(3, 4));
        assertTrue(weightTree.connected(5, 6));
        assertFalse(weightTree.connected(1, 2));
    }

}