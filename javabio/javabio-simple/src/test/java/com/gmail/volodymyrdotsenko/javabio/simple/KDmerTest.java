package com.gmail.volodymyrdotsenko.javabio.simple;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 8/3/16.
 */
public class KDmerTest {
    @Test
    public void getPrefix() throws Exception {
        assertEquals(new KDmer("GA", "TC"), new KDmer("GAC", "TCA").getPrefix());
    }

    @Test
    public void getSuffix() throws Exception {
        assertEquals(new KDmer("AC", "CA"), new KDmer("GAC", "TCA").getSuffix());
    }
}