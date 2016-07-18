package com.gmail.volodymyrdotsenko.javabio.simple;

import com.gmail.volodymyrdotsenko.javabio.algorithms.graph.SymbolDigraph;

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
}