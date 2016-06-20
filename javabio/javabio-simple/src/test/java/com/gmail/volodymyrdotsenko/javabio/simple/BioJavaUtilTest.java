package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class BioJavaUtilTest {

    @Test
    public void testReverseComplement() {
        assertEquals("ACCGGGTTTT", BioJavaUtil.reverseComplement("AAAACCCGGT"));
    }
}