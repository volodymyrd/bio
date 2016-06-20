package com.gmail;

import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class StringSearchCommanderTest {
    @Test
    public void testPatternCount() {
        Bootstrap bootstrap = new Bootstrap();

        JLineShellComponent shell = bootstrap.getJLineShellComponent();

        CommandResult cr = shell.executeCommand("patternCount --text GCGCG --pattern GCG");
        assertEquals(true, cr.isSuccess());
        assertEquals("1", cr.getResult());
    }
}