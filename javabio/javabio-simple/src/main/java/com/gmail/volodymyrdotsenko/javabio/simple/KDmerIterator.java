package com.gmail.volodymyrdotsenko.javabio.simple;

import java.util.*;

/**
 * Iterator for (k,d)-mer {@link KDmer}
 * <p>
 * Created by Volodymyr Dotsenko on 04.07.16.
 */
public class KDmerIterator implements Iterator<KDmer> {

    private final String text;
    private final int k;
    private final int d;
    private final int n;
    private final StringBuilder builder1;
    private final StringBuilder builder2;
    private int i = 1;

    public KDmerIterator(String text, int k, int d) {
        n = text.length() - 2 * k - d + 1;
        if (n <= 0)
            throw new IllegalArgumentException("Sum of parameters 'k' and 'd' must be less or equal than length of parameter 'text'");

        this.text = text;
        this.k = k;
        this.d = d;
        this.builder1 = new StringBuilder(text.substring(0, k));
        this.builder2 = new StringBuilder(text.substring(k + d, 2 * k + d));
    }

    @Override
    public boolean hasNext() {
        return i <= n;
    }

    @Override
    public KDmer next() {
        String pattern1 = builder1.toString();
        String pattern2 = builder2.toString();
        int next1 = i++ + k - 1;
        int next2 = next1 + k + d;
        if (next2 < text.length()) {
            builder1.delete(0, 1).append(text.charAt(next1));
            builder2.delete(0, 1).append(text.charAt(next2));
        }

        return new KDmer(pattern1, pattern2);
    }

    public List<KDmer> getList(boolean sorted) {
        List<KDmer> list = new ArrayList<>(n);

        while (hasNext()) {
            list.add(next());
        }

        if (sorted)
            Collections.sort(list);

        return list;
    }

    public KDmer[] getArray() {
        KDmer[] list = new KDmer[n];

        for (int j = 0; hasNext(); j++) {
            list[j] = next();
        }

        return list;
    }
}