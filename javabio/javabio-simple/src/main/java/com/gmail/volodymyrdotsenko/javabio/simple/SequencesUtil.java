package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.EulerianDigraph;
import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.SymbolDigraph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    public static SymbolDigraph overlapGraph(String[] texts) {
        SymbolDigraph digraph = new SymbolDigraph();

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


    public static SymbolDigraph buildBruijnGraph(int k) {
        Set<String> set = SubStringUtils.permutationsWithRepetitions(k, new char[]{'0', '1'});
        return overlapGraph(set.toArray(new String[set.size()]));
    }

    public static SymbolDigraph buildBruijnGraph(String text, int k) {
        KmerIterator it = new KmerIterator(text, k);
        SymbolDigraph digraph = new SymbolDigraph();
        while (it.hasNext()) {
            String kmer = it.next();
            digraph.addEdge(prefix(kmer), suffix(kmer));

        }
        return digraph;
    }

    public static SymbolDigraph buildBruijnGraph(String[] texts) {
        SymbolDigraph digraph = new SymbolDigraph();

        for (int i = 0; i < texts.length; i++)
            digraph.addEdge(prefix(texts[i]), suffix(texts[i]));

        return digraph;
    }

    public static EulerianDigraph buildEulerianDigraph(String[] texts) {
        EulerianDigraph digraph = new EulerianDigraph();

        for (int i = 0; i < texts.length; i++) {
            digraph.addEdge(prefix(texts[i]), suffix(texts[i]));
        }

        return digraph;
    }

    public static String stringReconstruction(String[] texts) {
        EulerianDigraph digraph = buildEulerianDigraph(texts);
        List<String> parts = digraph.toSymbols(digraph.findPath());
        return stringSpelledGenomePathProblem(parts.toArray(new String[parts.size()]));
    }

    public static String stringReconstructionForKUniversal(int k) {
        Set<String> texts = SubStringUtils.permutationsWithRepetitions(k, new char[]{'0', '1'});
        EulerianDigraph digraph = buildEulerianDigraph(texts.toArray(new String[texts.size()]));
        List<String> parts = digraph.toSymbols(digraph.findPath());
        String res = stringSpelledGenomePathProblem(parts.toArray(new String[parts.size()]));
        return res.substring(0, res.length() - (k - 1));
    }

    public static String findHamiltonianPathInBruijnGraph(int k) {
        SymbolDigraph graph = buildBruijnGraph(k);

        Deque<String> nodes = new LinkedList<>();

        findPath(graph, graph.getKeys()[0], nodes);

        return stringSpelledGenomePathProblem(nodes.toArray(new String[nodes.size()]));
    }

    private static boolean findPath(final SymbolDigraph graph, String v, final Deque<String> nodes) {
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