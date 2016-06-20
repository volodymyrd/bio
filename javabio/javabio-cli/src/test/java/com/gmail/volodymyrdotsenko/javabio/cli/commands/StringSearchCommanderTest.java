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
    public void testFrequentWords() {
        CommandResult cr = shell.executeCommand("frequentWords --text ACGTTGCATGTCGCATGATGCATGAGAGCT --l 4");
        assertEquals(true, cr.isSuccess());
        assertEquals("CATG GCAT", cr.getResult());
    }
}