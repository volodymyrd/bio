package com.gmail.volodymyrdotsenko.javabio.algorithms.hmm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Volodymyr Dotsenko on 8/18/16.
 */
public class ForwardSequenceProbabilitySolverImplTest {
    @Test
    public void solve() throws Exception {
        double[][] A = {{0.7, 0.3}, {0.4, 0.6}};
        double[][] B = {{0.1, 0.4, 0.5}, {0.7, 0.2, 0.1}};
        double[] pi = {0.6, 0.4};
        String[] Q = {"H", "C"};
        String[] V = {"S", "M", "L"};
        String[] O = {"S", "M", "S", "L"};

        HiddenMarkovModel<String, String> hmm = new HiddenMarkovModel<>(Q, V, A, B, pi, O);

        SequenceProbabilitySolver solver = new ForwardSequenceProbabilitySolverImpl();
        assertEquals(0.0096296, solver.solve(hmm), 0.0000000001);
    }

    @Test
    public void solve2() throws Exception {
        double[][] A = {{0.6, 0.4}, {0.3, 0.7}};
        double[][] B = {{0.3, 0.4, 0.3}, {0.4, 0.3, 0.3}};
        double[] pi = {0.8, 0.2};
        String[] Q = {"S1", "S2"};
        String[] V = {"R", "W", "B"};
        String[] O = {"R", "W", "B", "B"};

        HiddenMarkovModel<String, String> hmm = new HiddenMarkovModel<>(Q, V, A, B, pi, O);

        SequenceProbabilitySolver solver = new ForwardSequenceProbabilitySolverImpl();
        assertEquals(0.010152, solver.solve(hmm), 0.00000001);
    }
}