package com.gmail.volodymyrdotsenko.javabio.cli.commands;

import org.springframework.shell.core.ExecutionProcessor;
import org.springframework.shell.event.ParseResult;
import org.springframework.shell.support.logging.HandlerUtils;

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
}