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
public class SequencesCommanderTest {
    private Bootstrap bootstrap;
    private JLineShellComponent shell;

    @Before
    public void setUp() {
        bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
    }

    @Test
    public void testReverseComplement() {
        CommandResult cr = shell.executeCommand("sequences reverse complement --sequence AAAACCCGGT");
        assertEquals(true, cr.isSuccess());
        assertEquals("ACCGGGTTTT", cr.getResult());
    }
}