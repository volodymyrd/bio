package com.gmail.volodymyrdotsenko.javabio.simple;

import java.util.*;
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

    public static Map<String, Integer> frequentWords(String text, int k, int greaterThan, int d) {
        Map<String, Integer> frequents = new HashMap<>();
        final int max = getFrequentWordsMap(frequents, text, k, d);

        if (greaterThan >= 0) {
            return frequents.entrySet().stream().filter(e -> e.getValue() > greaterThan)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        } else {
            return frequents.entrySet().stream().filter(e -> e.getValue() == max)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }
    }

    public static Map<String, Integer> wordsWithGivenFrequency(String text, int k, int t) {
        Map<String, Integer> frequents = new HashMap<>();
        getFrequentWordsMap(frequents, text, k, 0);

        return frequents.entrySet().stream().filter(e -> e.getValue() == t)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    private static int getFrequentWordsMap(Map<String, Integer> frequents, String text, int k, int d) {
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

        if (d > 0) {
            for (int i = 0; i < (text.length() - k); i++) {
                String pattern = text.substring(i, i + k);
                for (Map.Entry<String, Integer> e : frequents.entrySet()) {
                    if (!e.getKey().equals(pattern)) {
                        if (hammingDistance(e.getKey(), pattern) <= d) {
                            int f = e.getValue() + 1;
                            frequents.put(e.getKey(), f);
                            if (f > max)
                                max = f;
                        }
                    }
                }
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

        getFrequentWordsMap(frequents, genome.substring(0, L), k, 0);

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

    public static int hammingDistance(String s1, String s2) {
        int count = 0;
        int l = s1.length() <= s2.length() ? s1.length() : s2.length();
        for (int i = 0; i < l; i++)
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
            }

        return count;
    }

    public static List<Integer> approximatePatternCountList(String text, String pattern, int d) {
        List<Integer> indexes = new ArrayList<>();
        int n = text.length();
        int k = pattern.length();
        for (int i = 0; i < n - k + 1; i++) {
            if (hammingDistance(text.substring(i, i + k), pattern) <= d) {
                indexes.add(i);
            }
        }

        return indexes;
    }

    public static long approximatePatternCount(String text, String pattern, int d) {
        return approximatePatternCountList(text, pattern, d).size();
    }

    public static Set<String> permutations(String str) {
        Set<String> set = new HashSet<>();

        permutations("", str, set);

        return set;
    }

    private static void permutations(String prefix, String str, Set<String> set) {
        int n = str.length();

        if (n == 0)
            set.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), set);
        }
    }

    public static Set<String> getAllKMerPatterns(int K, List<String> abc) {
        Set<String> p = new HashSet<>();
        int L = abc.size();

        Attempts attempts = new Attempts(L, K);

        EXIT:
        for (; ; ) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < K; j++) {
                int i = attempts.next();
                if (i == -1)
                    break EXIT;

                sb.append(abc.get(i));
            }

            add(abc, p, sb, attempts);
        }

        return p;
    }

    private static void add(List<String> abc, Set<String> p, StringBuilder sb, Attempts attempts) {
        if (!p.add(sb.toString())) {
            attempts.back();
            sb.delete(sb.length() - 1, sb.length());
            sb.append(abc.get(attempts.next()));
            add(abc, p, sb, attempts);
        }
    }

    private static class Attempts {
        private final Map<Integer, Long> attempts = new HashMap<>();
        private final int L;
        private int pos;

        public Attempts(int L, int k) {
            long attempt = (long) Math.pow(L, k);
            this.L = L;
            for (int i = 0; i < L; i++) {
                attempts.put(i, attempt);
            }
        }

        public int next() {
            if (attempts.values().stream().filter(v -> v > 0).findFirst().isPresent()) {
                long i = attempts.get(pos);

                if (i <= 0) {
                    nextPos();
                    return next();
                }

                attempts.put(pos, --i);

                int ind = nextPos();

                return ind;
            } else
                return -1;
        }

        private int nextPos() {
            int ind = pos++;
            if (pos == L)
                pos = 0;

            return ind;
        }

        public void back() {
            int ind = pos - 1;
            if (ind < 0)
                ind = L - 1;

            attempts.put(ind, attempts.get(ind) + 1);
        }
    }
}