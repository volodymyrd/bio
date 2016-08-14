package com.gmail.volodymyrdotsenko.javabio.algorithms.hmm;

/**
 * Given the HMM model λ = (A, B, π) and a sequence of observations O,
 * find P (O | λ).
 * Here, we want to determine the likelihood of the observed sequence O, given the model
 * <p>
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public interface SequenceProbabilitySolver<TQ, TO> {
    double solve(HiddenMarkovModel<TQ, TO> hmm);
}