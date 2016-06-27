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

    public static long frequentWordsWithMismatches(Map<String, Long> counts, String text, int k, int d,
                                                   boolean complement) {
        Set<String> patterns = permutationsWithRepetitions(k, new char[]{'A', 'T', 'C', 'G'});
        long max = 0;
        for (String p : patterns) {
            long m = approximatePatternCount(text, p, d)
                    + (complement ? approximatePatternCount(text, BioJavaUtil.reverseComplement(p), d) : 0);
            if (m >= max) {
                max = m;
                counts.put(p, m);
            }
        }

        return max;
    }

    public static String frequentWordsWithMismatches(String text, int k, int d, boolean complement) {
        Map<String, Long> counts = new HashMap<>();
        long m = frequentWordsWithMismatches(counts, text, k, d, complement);
        return counts.entrySet().stream().filter(e -> e.getValue() >= m)
                .map(e -> e.getKey()).collect(Collectors.joining(" "));
    }

    public static Set<String> dNeighbors(String pattern, int d) {
        Set<String> neighborhoods = new HashSet<>();

        permutationsWithRepetitions(pattern.length(), new char[]{'A', 'T', 'C', 'G'})
                .forEach(e -> {
                    if (hammingDistance(pattern, e) <= d) {
                        neighborhoods.add(e);
                    }
                });

        return neighborhoods;
    }


    public static Set<String> permutationsWithRepetitions(int K, char[] abc) {
        int n = abc.length;
        if (K < 1)
            throw new IllegalArgumentException("Illegal number of positions.");

        int N = n >= K ? n : K;

        int[] indexes = new int[N];

        int total = (int) Math.pow(n, K);

        Set<String> set = new TreeSet<>();

        while (total-- > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N - (N - K); i++)
                sb.append(abc[indexes[i]]);

            set.add(sb.toString());

            for (int i = 0; i < N; i++) {
                if (indexes[i] >= n - 1) {
                    indexes[i] = 0;
                } else {
                    indexes[i]++;
                    break;
                }
            }
        }

        return set;
    }
}