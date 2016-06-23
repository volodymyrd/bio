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

    public static Map<String, Integer> frequentWords(String text, int k, int greaterThan) {
        Map<String, Integer> frequents = new HashMap<>();
        int max = getFrequentWordsMap(frequents, text, k);

        if (greaterThan >= 0) {
            return frequents.entrySet().stream().filter(e -> e.getValue() > greaterThan)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        } else {
            final int m = max;

            return frequents.entrySet().stream().filter(e -> e.getValue() == m)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }
    }

    public static Map<String, Integer> wordsWithGivenFrequency(String text, int k, int t) {
        Map<String, Integer> frequents = new HashMap<>();
        getFrequentWordsMap(frequents, text, k);

        return frequents.entrySet().stream().filter(e -> e.getValue() == t)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    private static int getFrequentWordsMap(Map<String, Integer> frequents, String text, int k) {
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

        return max;
    }

    public static Map<String, Integer> getClump(String genome, int k, int L, int t) {
        Map<String, Integer> frequents = new HashMap<>();

        for (int i = 0; i < (genome.length() - L); i++) {
            String text = genome.substring(i, i + L);

            wordsWithGivenFrequency(text, k, t).forEach((key, val) -> {
                Integer f = frequents.get(key);
                frequents.put(key, f == null ? val : f + val);
            });
        }

        return frequents;
    }

    public static Map<String, Integer> getClump_1(String genome, int k, int L, int t, boolean count) {
        Map<String, Integer> frequents = new HashMap<>();

        getFrequentWordsMap(frequents, genome.substring(0, L), k);

        Map<String, Integer> resultFrequents = filterFrequensy(frequents, t);

        for (int i = 1; i < (genome.length() - L); i++) {
            String remove = genome.substring(i - 1, i + k - 1);
            String add = genome.substring(i + L - 1 - k, i + L - 1);

            Integer r = frequents.get(remove);
            frequents.put(remove, (r != null && r > 0) ? --r : 0);
            Integer a = frequents.get(add);
            frequents.put(add, a != null ? ++a : 1);

            if (count)
                resultFrequents.keySet().parallelStream().filter(e -> !e.equals(add)).forEach(e -> {
                    Integer v = frequents.get(e);
                    if (v != null && v == t) {
                        resultFrequents.put(e, v + resultFrequents.get(e));
                    }
                });

            if (a != null && a == t) {
                Integer v = resultFrequents.get(add);
                resultFrequents.put(add, (a + (v != null ? v : 0)));
            }
        }

        return resultFrequents;
    }

    private static Map<String, Integer> filterFrequensy(Map<String, Integer> frequents, int frequency) {
        return frequents.entrySet().parallelStream().filter(e -> e.getValue() == frequency)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}