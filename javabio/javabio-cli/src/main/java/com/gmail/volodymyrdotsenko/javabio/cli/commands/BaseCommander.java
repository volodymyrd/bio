package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import com.gmail.volodymyrdotsenko.javabio.cli.utils.FileUtils;
import org.springframework.shell.core.ExecutionProcessor;
import org.springframework.shell.event.ParseResult;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Volodymyr Dotsenko on 6/20/16.
 */
public class BaseCommander implements ExecutionProcessor {
    private static final java.util.logging.Logger LOGGER = HandlerUtils
            .getLogger(BaseCommander.class);

    private long startTime;

    @Override
    public ParseResult beforeInvocation(ParseResult invocationContext) {

        startTime = System.nanoTime();

        return invocationContext;
    }

    @Override
    public void afterReturningInvocation(ParseResult invocationContext, Object result) {
        LOGGER.info("Command execution took " + (System.nanoTime() - startTime) / 1000000.0 + "ms");
    }

    @Override
    public void afterThrowingInvocation(ParseResult invocationContext, Throwable thrown) {

    }

    protected boolean isEmpty(String text) {
        return text == null || text.isEmpty() ? true : false;
    }

    protected String extractText(String text, String fileName) {
        if (!isEmpty(text))
            return text;
        else {
            try {
                return FileUtils.getStringFromFile(fileName);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());

                return null;
            }
        }
    }

    protected static void saveTextFile(String filePath, String content) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filePath)) {
            out.println(content);
        }
    }
}