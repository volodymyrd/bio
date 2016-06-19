package com.gmail.volodymyrdotsenko.javabio.cli;

import org.springframework.shell.Bootstrap;

import java.io.IOException;

/**
 * Driver class to run the Java BIO CLI
 *
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
public class Main {

    /**
     * Main class that delegates to Spring Shell's Bootstrap class
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Bootstrap.main(args);
    }
}