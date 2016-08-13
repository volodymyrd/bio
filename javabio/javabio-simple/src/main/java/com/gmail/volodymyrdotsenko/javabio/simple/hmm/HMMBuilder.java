package com.gmail.volodymyrdotsenko.javabio.simple.hmm;

import org.biojava.bio.dist.DistributionFactory;
import org.biojava.bio.dp.*;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.db.HashSequenceDB;
import org.biojava.bio.seq.db.SequenceDB;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.Symbol;

import java.util.Iterator;

/**
 * Created by Volodymyr Dotsenko on 8/11/16.
 */
public class HMMBuilder {
    public void createProfile() {
        /**
         * Make a profile HMM over the DNA Alphabet with 12 'columns' and default
         * DistributionFactories to construct the transition and emmission
         * Distributions
         */
        ProfileHMM hmm = null;
        try {
            FiniteAlphabet dna = DNATools.getDNA();

            hmm = new ProfileHMM(dna,
                    12, DistributionFactory.DEFAULT, DistributionFactory.DEFAULT, "my profilehmm");
            //create the Dynamic Programming matrix for the model.
            DP dp = DPFactory.DEFAULT.createDP(hmm);
            System.out.print(dp.getModel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void train() {
        /**
         * Make a profile HMM over the DNA Alphabet with 12 'columns' and default
         * DistributionFactories to construct the transition and emmission
         * Distributions
         */
        ProfileHMM hmm = null;
        try {
            FiniteAlphabet dna = DNATools.getDNA();
            Iterator<Symbol> iterator = dna.iterator();
            while (iterator.hasNext())
                System.out.println(iterator.next().getName());
            hmm = new ProfileHMM(dna,
                    12, DistributionFactory.DEFAULT, DistributionFactory.DEFAULT, "my profilehmm");
            //create the Dynamic Programming matrix for the model.
            DP dp = DPFactory.DEFAULT.createDP(hmm);
            System.out.print(dp.getModel());

            //Database to hold the training set
            SequenceDB db = new HashSequenceDB();
            //code here to load the training set
            //....

            //train the model to have uniform parameters
            ModelTrainer mt = new SimpleModelTrainer();
            //register the model to train
            mt.registerModel(hmm);
            //as no other counts are being used the null weight will cause everything to be uniform
            mt.setNullModelWeight(1.0);
            mt.train();
            //create a BW trainer for the dp matrix generated from the HMM
            BaumWelchTrainer bwt = new BaumWelchTrainer(dp);

            //anonymous implementation of the stopping criteria interface to stop after 20 iterations
            StoppingCriteria stopper = new StoppingCriteria() {
                public boolean isTrainingComplete(TrainingAlgorithm ta) {
                    return (ta.getCycle() > 20);
                }
            };

            /**
             * optimize the dp matrix to reflect the training set in db using a null model
             * weight of 1.0 and the Stopping criteria defined above.
             */
            bwt.train(db, 1.0, stopper);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}