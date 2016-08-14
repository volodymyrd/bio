package com.gmail.volodymyrdotsenko.javabio.algorithms.hmm;

import com.gmail.volodymyrdotsenko.javabio.algorithms.utils.PermutationsWithRepetitionsIterator;

/**
 * Naive implementation.
 * Let λ = (A, B, π) be a given model and
 * let O = (O0, O1, . . . , OT −1) be a series of observations. We want to find P(O|λ)
 * <p>
 * P(O|λ) = SumAllPossibleX(πx0*bx0(O0)*ax0,x1*bx1(O1)...axT −2,xT −1*bxT −1(OT −1)).
 * <p>
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public class DirectSequenceProbabilitySolverImpl<TQ, TO> implements SequenceProbabilitySolver<TQ, TO> {
    @Override
    public double solve(HiddenMarkovModel<TQ, TO> hmm) {
        double prob = 0.0;
        PermutationsWithRepetitionsIterator it = new PermutationsWithRepetitionsIterator(hmm.getN(), hmm.getT());
        while (it.hasNext()) {
            int[] indexes = it.next();
            int previous = 0;
            double probTransition = 0;
            for (int i = 0; i < indexes.length; i++) {
                if (i == 0) {
                    probTransition = hmm.getPi()[indexes[i]] * hmm.getB()[indexes[i]][i];
                } else {
                    probTransition *= hmm.getA()[previous][indexes[i]] *
                            hmm.getB()[indexes[i]][hmm.getPossibleObservationIndexByName(hmm.getO()[i])];
                }
                previous = indexes[i];
            }

            prob += probTransition;
        }

        return prob;
    }
}