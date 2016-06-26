package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class StringSearchCommanderTest {

    private Bootstrap bootstrap;
    private JLineShellComponent shell;

    @Before
    public void setUp() {
        bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
    }

    @Test
    public void testPatternCount() {
        CommandResult cr = shell.executeCommand("patternCount --text GCGCG --pattern GCG");
        assertEquals(true, cr.isSuccess());
        assertEquals("1", cr.getResult());
    }

    @Test
    public void testPatternCounts() {
        CommandResult cr = shell.executeCommand("patternCount --text GATATATGCATATACTT --pattern ATAT --startingPosition true");
        assertEquals(true, cr.isSuccess());
        assertEquals("1 3 9", cr.getResult());
    }

    @Test
    public void testFrequentWords() {
        CommandResult cr = shell.executeCommand("frequentWords --text ACGTTGCATGTCGCATGATGCATGAGAGCT --kmer 4 --frequency -1");
        assertEquals(true, cr.isSuccess());
        assertEquals("CATG GCAT", cr.getResult());
    }

    @Test
    public void testFrequentWordsWithMismatches() {
        CommandResult cr = shell.executeCommand("frequentWords --text ACGTTGCATGTCGCATGATGCATGAGAGCT --kmer 4 --frequency -1 --d 1");
        assertEquals(true, cr.isSuccess());
        assertEquals("ATGC ATGT GATG", cr.getResult());
    }

    @Test
    public void testFrequentWordsWithMismatches1() {
        CommandResult cr = shell.executeCommand("frequentWords --text AAAAAAAAAA --kmer 2 --frequency -1 --d 1");
        assertEquals(true, cr.isSuccess());
        assertEquals("AA AC AG CA AT GA TA", cr.getResult());
    }
}