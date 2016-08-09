package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.EulerianDigraph;
import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.SymbolDigraph;

import java.util.*;

/**
 * Created by Volodymyr Dotsenko on 12.07.16.
 */
public class SequencesUtil {
    public static String prefix(String text) {
        return text.substring(0, text.length() - 1);
    }

    public static String suffix(String text) {
        return text.substring(1, text.length());
    }

    public static String stringSpelledGenomePathProblem(String[] texts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < texts.length - 1; i++) {
            if (suffix(texts[i]).equals(prefix(texts[i + 1]))) {
                if (builder.length() == 0)
                    builder.append(texts[i]);

                builder.append(texts[i + 1].charAt(texts[i + 1].length() - 1));
            }
        }

        return builder.toString();
    }

    public static String stringSpelledGenomePathProblem(KDmer[] kDmers, int k, int d) {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();

        for (int i = 0; i < kDmers.length - 1; i++) {
            if (kDmers[i].getSuffix().equals(kDmers[i + 1].getPrefix())) {
                if (builder.length() == 0)
                    builder.append(kDmers[i].getPattern1());

                builder.append(kDmers[i + 1].getPattern1().charAt(kDmers[i + 1].getPattern1().length() - 1));

                int remain = kDmers.length - i - 1;
                if (remain == d + 1) {
                    builder2.append(kDmers[i + 1].getPattern2());
                } else if (remain < d + 1) {
                    builder2.append(kDmers[i + 1].getPattern2().charAt(kDmers[i + 1].getPattern2().length() - 1));
                }
            }
        }

        //builder2.append(kDmers[kDmers.length - 1].getPattern2().charAt(kDmers[kDmers.length - 1].getPattern2().length() - 1));

        return builder.append(builder2).toString();
    }

    public static SymbolDigraph<String> overlapGraph(String[] texts) {
        SymbolDigraph<String> digraph = new SymbolDigraph();

        for (int i = 0; i < texts.length; i++)
            for (int j = 0; j < texts.length; j++) {
                if (i == j)
                    continue;

                if (suffix(texts[i]).equals(prefix(texts[j]))) {
                    digraph.addEdge(texts[i], texts[j]);
                }
            }

        return digraph;
    }


    public static SymbolDigraph<String> buildBruijnGraph(int k) {
        Set<String> set = SubStringUtils.permutationsWithRepetitions(k, new char[]{'0', '1'});
        return overlapGraph(set.toArray(new String[set.size()]));
    }

    public static SymbolDigraph<String> buildBruijnGraph(String text, int k) {
        KmerIterator it = new KmerIterator(text, k);
        SymbolDigraph<String> digraph = new SymbolDigraph();
        while (it.hasNext()) {
            String kmer = it.next();
            digraph.addEdge(prefix(kmer), suffix(kmer));

        }
        return digraph;
    }

    public static SymbolDigraph<String> buildBruijnGraph(String[] texts) {
        SymbolDigraph<String> digraph = new SymbolDigraph();

        for (int i = 0; i < texts.length; i++)
            digraph.addEdge(prefix(texts[i]), suffix(texts[i]));

        return digraph;
    }

    public static EulerianDigraph<String> buildEulerianDigraph(String[] texts) {
        EulerianDigraph<String> digraph = new EulerianDigraph();

        for (int i = 0; i < texts.length; i++) {
            digraph.addEdge(prefix(texts[i]), suffix(texts[i]));
        }

        return digraph;
    }

    public static EulerianDigraph<KDmer> buildEulerianDigraph(KDmer[] texts) {
        EulerianDigraph<KDmer> digraph = new EulerianDigraph();

        for (int i = 0; i < texts.length; i++) {
            digraph.addEdge(texts[i].getPrefix(), texts[i].getSuffix());
        }

        return digraph;
    }

    public static String stringReconstruction(String[] texts) {
        EulerianDigraph digraph = buildEulerianDigraph(texts);
        List<String> parts = digraph.toSymbols(digraph.findPath());
        return stringSpelledGenomePathProblem(parts.toArray(new String[parts.size()]));
    }

    public static String stringReconstruction(KDmer[] kDmers, int k, int d) {
        EulerianDigraph<KDmer> digraph = buildEulerianDigraph(kDmers);
        System.out.println(digraph);
        List<KDmer> parts = toEdgeSymbols(digraph, digraph.findPath());
        System.out.println(parts);
        return stringSpelledGenomePathProblem(parts.toArray(new KDmer[parts.size()]), k, d);
    }

    public static List toEdgeSymbols(SymbolDigraph digraph, Deque<Integer> vertices) {
        System.out.println(vertices);
        List list = new ArrayList();
        int n = vertices.size();
        for (int i = 0; i < n - 1; i++) {
            Object v1 = digraph.getKeys().get(vertices.pop());
            Object v2 = digraph.getKeys().get(vertices.peek());
            if (v1 instanceof KDmer && v2 instanceof KDmer) {
                list.add(new KDmer(((KDmer) v1).getPattern1() + ((KDmer) v2).getPattern1(),
                        ((KDmer) v1).getPattern2() + ((KDmer) v2).getPattern2()));
            } else {
                list.add(v1.toString() + v2.toString());
            }
        }

        return list;
    }

    public static String stringReconstructionForKUniversal(int k) {
        Set<String> texts = SubStringUtils.permutationsWithRepetitions(k, new char[]{'0', '1'});
        EulerianDigraph<String> digraph = buildEulerianDigraph(texts.toArray(new String[texts.size()]));
        List<String> parts = digraph.toSymbols(digraph.findPath());
        String res = stringSpelledGenomePathProblem(parts.toArray(new String[parts.size()]));
        return res.substring(0, res.length() - (k - 1));
    }

    public static String findHamiltonianPathInBruijnGraph(int k) {
        SymbolDigraph<String> graph = buildBruijnGraph(k);

        Deque<String> nodes = new LinkedList<>();

        findPath(graph, graph.getKeys().get(0), nodes);

        return stringSpelledGenomePathProblem(nodes.toArray(new String[nodes.size()]));
    }

    private static boolean findPath(final SymbolDigraph<String> graph, String v, final Deque<String> nodes) {
        if (nodes.contains(v))
            return false;

        nodes.add(v);

        if (nodes.size() == graph.V())
            return true;

        boolean flag = false;
        for (String e : graph.adj(v)) {
            flag = findPath(graph, e, nodes);
            if (flag)
                break;
        }
        if (!flag) {
            nodes.removeLast();

            return false;
        }

        return true;
    }
}