package com.gmail.volodymyrdotsenko.javabio.algorithms.hmm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Volodymyr Dotsenko on 8/14/16.
 */
public class HiddenMarkovModel<TQ, TO> {
    final int T;        //length of the observation sequence
    final int N;        //number of states in the model
    final int M;        //number of observation symbols

    final TQ Q[];       //{q0, q1, . . . , qN−1} - distinct states of the Markov process
    final TO V[];       //{0,1,...,M−1} - set of possible observations
    final Map<TO, Integer> vk;
    final double A[][]; //state transition probabilities
    final double B[][]; //observation probability matrix
    final double pi[];  //initial state distribution
    final TO O[];       //(O0, O1, . . . , OT −1) - observation sequence

    public HiddenMarkovModel(TQ[] Q, TO[] V, double[][] A, double[][] B, double[] pi, TO[] O) {
        this.Q = Q;
        this.V = V;
        this.A = A;
        this.B = B;
        this.pi = pi;
        this.O = O;

        T = O.length;
        N = Q.length;
        M = V.length;

        vk = new HashMap<>(M);
        for (int i = 0; i < V.length; i++)
            vk.put(V[i], i);
    }

    public int getPossibleObservationIndexByName(TO observation) {
        return vk.get(observation);
    }

    public int getT() {
        return T;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public TQ[] getQ() {
        return Q;
    }

    public TO[] getV() {
        return V;
    }

    public double[][] getA() {
        return A;
    }

    public double[][] getB() {
        return B;
    }

    public double[] getPi() {
        return pi;
    }

    public TO[] getO() {
        return O;
    }
}