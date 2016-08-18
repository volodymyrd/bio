package com.gmail.volodymyrdotsenko.javabio.algorithms.hmm;

/**
 * Implementation of forward algorithm.
 * <p>
 * Created by Volodymyr Dotsenko on 8/18/16.
 */
public class ForwardSequenceProbabilitySolverImpl<TQ, TO> implements SequenceProbabilitySolver<TQ, TO> {
    @Override
    public double solve(HiddenMarkovModel<TQ, TO> hmm) {
        double prob = 0.0;


        double[][] alphaArray = new double[hmm.getN()][hmm.getT()];

        for (int i = 0; i < hmm.getN(); i++) {
            alphaArray[i][0] = hmm.getPi()[i] * hmm.getB()[i][0];
        }

        for (int t = 1; t < hmm.getT(); t++) {
            for (int i = 0; i < hmm.getN(); i++) {
                for (int j = 0; j < hmm.getN(); j++) {
                    alphaArray[i][t] += hmm.getA()[i][j] * alphaArray[i][t - 1];
                }
                alphaArray[i][t] *= hmm.getB()[i][hmm.getPossibleObservationIndexByName(hmm.getO()[t])];
            }
        }

        for (int i = 0; i < hmm.getN(); i++) {
            prob += alphaArray[i][hmm.getT() - 1];
        }

        return prob;
    }
}