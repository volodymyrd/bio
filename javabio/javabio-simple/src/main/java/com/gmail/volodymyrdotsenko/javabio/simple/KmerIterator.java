package com.gmail.volodymyrdotsenko.javabio.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator for k-mer
 * <p>
 * Created by Volodymyr Dotsenko on 04.07.16.
 */
public class KmerIterator implements Iterator<String> {

    private final String text;
    private final int k;
    private final int n;
    private final StringBuilder builder;
    private int i = 1;

    public KmerIterator(String text, int k) {
        n = text.length() - k + 1;
        if (n <= 0)
            throw new IllegalArgumentException("Parameter 'k' must be less or equal than length of parameter 'text'");

        this.text = text;
        this.k = k;
        this.builder = new StringBuilder(text.substring(0, k));
    }

    @Override
    public boolean hasNext() {
        return i <= n;
    }

    @Override
    public String next() {
        String s = builder.toString();
        int next = i++ + k - 1;
        if (next < text.length())
            builder.delete(0, 1).append(text.charAt(next));
        return s;
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>(n);

        while (hasNext()) {
            list.add(next());
        }

        return list;
    }

    public String[] getArray() {
        String[] list = new String[n];

        for (int j = 0; hasNext(); j++) {
            list[j] = next();
        }

        return list;
    }
}