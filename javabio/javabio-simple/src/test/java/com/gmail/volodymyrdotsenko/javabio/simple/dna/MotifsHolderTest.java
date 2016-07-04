package com.gmail.volodymyrdotsenko.javabio.simple.dna;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 04.07.16.
 */
public class MotifsHolderTest {
    @Test
    public void testScore() {
        MotifsHolder holder = new MotifsHolder(12);
        holder
                .add("TCGGGGgTTTtt")
                .add("cCGGtGAcTTaC")
                .add("aCGGGGATTTtC")
                .add("TtGGGGAcTTtt")
                .add("aaGGGGAcTTCC")
                .add("TtGGGGAcTTCC")
                .add("TCGGGGATTcat")
                .add("TCGGGGATTcCt")
                .add("TaGGGGAacTaC")
                .add("TCGGGtATaaCC");

        assertEquals(30, holder.score());
    }
}