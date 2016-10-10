package com.gmail.volodymyrdotsenko.javabio.algorithms.tasks;

public class ArrayUtils {

    public static <T> T[] reverse(T[] a, int start, int end) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("Array 'a' must be filled");

        if (start < 0)
            throw new IllegalArgumentException("Argument 'start' must be gteater or equal zero");

        if (end > a.length)
            throw new IllegalArgumentException(
                    "Argument 'end' must be less or equal length of array");

        if (start > end)
            throw new IllegalArgumentException("Argument 'start' must be less than agrument 'end'");

        for (int i = start, j = end - 1; i < (start + end) / 2; i++, j--) {
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        return a;
    }

    public static <T> T[] rotate(T[] a, int times) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("Array 'a' must be filled");

        int n;
        if (times > a.length)
            n = times % a.length;
        else
            n = times;

        a = reverse(a, 0, a.length - n);
        a = reverse(a, a.length - n, a.length);
        a = reverse(a, 0, a.length);
        
        return a;
    }
}
