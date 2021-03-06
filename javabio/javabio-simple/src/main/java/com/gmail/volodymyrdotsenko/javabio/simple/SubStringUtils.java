package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.simple.dna.DNANucleotide;
import com.gmail.volodymyrdotsenko.javabio.simple.dna.DNAProfileMatrix;
import com.gmail.volodymyrdotsenko.javabio.simple.dna.MotifsHolder;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return frequents.entrySet().stream().filter(e -> e.getValue() == max)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    public static Map<String, Integer> wordsWithGivenFrequency(String text, int k, int t) {
        Map<String, Integer> frequents = new HashMap<>();
        getFrequentWordsMap(frequents, text, k, 0);

        return frequents.entrySet().stream().filter(e -> e.getValue() == t)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        KmerIterator iterator = new KmerIterator(text, pattern.length());
        int i = 0;
        while (iterator.hasNext()) {
            if (hammingDistance(iterator.next(), pattern) <= d) {
                indexes.add(i);
            }
            i++;
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
                .map(Map.Entry::getKey).collect(Collectors.joining(" "));
    }

    public static Set<String> dNeighbors(String pattern, int d) {
        return dNeighbors(pattern, d, permutationsWithRepetitions(pattern.length(), new char[]{'A', 'T', 'C', 'G'}));
    }

    public static Set<String> dNeighbors(String pattern, int d, Set<String> permutationsWithRepetitions) {
        return permutationsWithRepetitions.stream()
                .filter(e -> hammingDistance(pattern, e) <= d)
                .collect(Collectors.toSet());
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

    public static Set<String> motifEnumerationBruteForce(List<String> dnas, int k, int d) {
        Set<String> motifs = new HashSet<>();

        Set<String> permutationsWithRepetitions = permutationsWithRepetitions(k, new char[]{'A', 'T', 'C', 'G'});

        for (String p : permutationsWithRepetitions) {
            for (String n : dNeighbors(p, d, permutationsWithRepetitions)) {
                if (!dnas.stream().filter(s -> approximatePatternCount(s, n, d) == 0).findFirst().isPresent()) {
                    motifs.add(n);
                }
            }
        }

        return motifs;
    }

    public static long motifsWithMinHammingDistance(String pattern, String text) {
        return motifsWithMinHammingDistance(pattern, text, new HashMap<>());
    }

    public static long motifsWithMinHammingDistance(String pattern, String text, Map<String, Long> motifsCount) {
        int n = text.length();
        int k = pattern.length();
        long min = Long.MAX_VALUE;
        KmerIterator iterator = new KmerIterator(text, k);
        while (iterator.hasNext()) {
            String motif = iterator.next();
            Long l = motifsCount.get(motif);

            if (l == null) {
                long d = hammingDistance(motif, pattern);
                if (d < min) {
                    motifsCount.put(motif, d);
                    min = d;
                }
            }
        }

        return min;
    }

    public static List<String> getKmers(String text, int k) {
        int n = text.length();
        List<String> kmers = new ArrayList<>(n - k + 1);
        kmers.add(text.substring(0, k));
        StringBuilder builder = new StringBuilder(kmers.get(0));
        for (int i = 1; i < n - k + 1; i++) {
            builder.delete(0, 1);
            builder.append(text.charAt(i + k - 1));
            kmers.add(builder.toString());
        }

        return kmers;
    }

    public static Set<String> medianString(List<String> dnas, int k) {
        Set<String> medians = new HashSet<>();
        long min = Long.MAX_VALUE;
        for (String p : permutationsWithRepetitions(k, new char[]{'A', 'T', 'C', 'G'})) {
            long sum = 0;
            for (String dna : dnas) {
                //Map<String, Long> temp = new HashMap<>();
                sum += motifsWithMinHammingDistance(p, dna);
            }

            if (sum < min) {
                min = sum;
                medians.clear();
                medians.add(p);
            } else if (sum == min) {
                medians.add(p);
            }
        }

        return medians;
    }

    public static double getProfileKmerProbability(String kmer, DNAProfileMatrix profile) {
        double p = 1;
        for (int j = 0; j < kmer.length(); j++) {
            p *= profile.getProbability(j, DNANucleotide.valueOf(kmer.charAt(j)));
        }

        return p;
    }

    public static String findProfileMostProbableKmer(String text, int k, DNAProfileMatrix profile) {
        String result = text.substring(0, k);

        int n = text.length();
        double max = 0;
        KmerIterator iterator = new KmerIterator(text, k);
        while (iterator.hasNext()) {
            String kmer = iterator.next();
            double p = getProfileKmerProbability(kmer, profile);

            if (p > max) {
                max = p;
                result = kmer;
            }
        }

        return result;
    }

    public static List<String> greedyMotifSearch(List<String> dnas, int k, boolean pseudocounts) {
        int pseudocountValue = 0;
        if (pseudocounts)
            pseudocountValue = 1;

        MotifsHolder bestMotifs = new MotifsHolder(k);
        for (String dna : dnas) {
            bestMotifs.add(dna.substring(0, k));
        }

        String dna0 = dnas.get(0);

        for (int i = 0; i < dna0.length() - k + 1; i++) {
            String motif = dna0.substring(i, i + k);

            MotifsHolder motifs = new MotifsHolder(k).add(motif);
            DNAProfileMatrix profile = motifs.getProfileMatrix(pseudocountValue);

            for (int j = 1; j < dnas.size(); j++) {
                motifs.add(findProfileMostProbableKmer(dnas.get(j), k, profile));
                profile = motifs.getProfileMatrix(pseudocountValue);
            }

            if (motifs.score() < bestMotifs.score())
                bestMotifs = motifs;
        }

        return bestMotifs.getMotifs();
    }

    public static long distanceBetweenPatternAndStrings(String pattern, List<String> dnas) {
        long distance = 0;
        int k = pattern.length();
        for (String dna : dnas) {
            int min = Integer.MAX_VALUE;
            KmerIterator iterator = new KmerIterator(dna, k);
            while (iterator.hasNext()) {
                int d = hammingDistance(pattern, iterator.next());
                if (d < min)
                    min = d;
            }
            distance += min;
        }

        return distance;
    }

    public static MotifsHolder motifs(DNAProfileMatrix profile, List<String> dnas, int k) {
        MotifsHolder holder = new MotifsHolder(k);
        dnas.forEach(dna -> {
            holder.add(findProfileMostProbableKmer(dna, k, profile));
        });
        return holder;
    }

    public static List<String> randomizedMotifSearch(List<String> dnas, int k, int steps, int attempts) {
        Map<MotifsHolder, Integer> theBestMotifsMap = new HashMap<>();

        int max = randomizedMotifSearch(dnas, k, steps, attempts, theBestMotifsMap);

        return theBestMotifsMap.entrySet().stream()
                .filter(e -> e.getValue() >= max)
                .findFirst().get().getKey()
                .getMotifs();
    }

    public static int randomizedMotifSearch(List<String> dnas, int k, int steps, int attempts,
                                            Map<MotifsHolder, Integer> theBestMotifsMap) {
        int pseudocountValue = 1;

        int max = 0;

        while (attempts-- > 0) {
            MotifsHolder theBestMotifs = new MotifsHolder(k);

            int st = steps;
            while (st-- > 0) {
                MotifsHolder bestMotifs = new MotifsHolder(k);

                for (String dna : dnas) {
                    int i = ThreadLocalRandom.current().nextInt(0, dna.length() - k + 1);
                    bestMotifs.add(dna.substring(i, k + i));
                }

                for (; ; ) {
                    DNAProfileMatrix profile = bestMotifs.getProfileMatrix(pseudocountValue);

                    MotifsHolder motifs = motifs(profile, dnas, k);

                    if (motifs.score() < bestMotifs.score())
                        bestMotifs = motifs;
                    else
                        break;
                }

                if (bestMotifs.score() < theBestMotifs.score())
                    theBestMotifs = bestMotifs;
            }

            Integer num = theBestMotifsMap.get(theBestMotifs);

            if (num == null) {
                num = 1;
            } else {
                ++num;
            }

            theBestMotifsMap.put(theBestMotifs, num);

            if (max < num) {
                max = num;
            }
        }

        return max;
    }

    public static MotifsHolder gibbsSampler(List<String> dnas, int k, int steps, int attempts) {
        int pseudocountValue = 1;

        int minScore = Integer.MAX_VALUE;

        MotifsHolder theBestMotifs = null;

        while (attempts-- > 0) {
            MotifsHolder bestMotifs = new MotifsHolder(k);

            for (String dna : dnas) {
                int i = ThreadLocalRandom.current().nextInt(0, dna.length() - k + 1);
                bestMotifs.add(dna.substring(i, k + i));
            }

            int st = steps;
            long bestScore = Integer.MAX_VALUE;
            while (st-- > 0) {
                int i = ThreadLocalRandom.current().nextInt(0, dnas.size());
                List<String> old = new LinkedList<>(bestMotifs.getMotifs());
                old.remove(i);
                MotifsHolder motifs = new MotifsHolder(k, old);
                String motif = findProfileMostProbableKmer(dnas.get(i), k, motifs.getProfileMatrix(pseudocountValue));
                old.add(i, motif);
                motifs = new MotifsHolder(k, old);

                long motifScore = motifs.score();
                bestScore = bestMotifs.score();

                if (motifScore < bestScore) {
                    bestMotifs = motifs;
                    bestScore = motifScore;
                }
            }

            if (bestScore < minScore) {
                minScore = (int) bestScore;

                theBestMotifs = bestMotifs;
            }
        }

        return theBestMotifs;
    }
}