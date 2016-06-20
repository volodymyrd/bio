package com.gmail.volodymyrdotsenko.javabio.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Dotsenko on 6/19/16.
 */
public class SubStringUtils {

    public static List<Integer> slideWindowPatternCount(String text, String pattern) {
        List<Integer> counts = new ArrayList<>();

        int n = text.length();
        int k = pattern.length();
        for (int i = 0; i < (n - k); i++) {
            boolean equal = true;
            for (int j = 0; j < k; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    equal = false;
                    break;
                }
            }

            if (equal) {
                counts.add(i);
            }
        }

        return counts;
    }

    public static Map<String, Integer> frequentWords(String text, int k) {
        Map<String, Integer> frequents = new HashMap<>();
        int max = 0;
        for (int i = 0; i < (text.length() - k); i++) {
            String pattern = text.substring(i, i + k);
            Integer f = frequents.get(pattern);
            if (f == null) {
                frequents.put(pattern, 1);
            } else {
                frequents.put(pattern, ++f);
                if (f > max)
                    max = f;
            }
        }

        final int m = max;

        return frequents.entrySet().stream().filter(e -> e.getValue() == m)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}