package com.gmail.volodymyrdotsenko.javabio.algorithms.utils;

import java.math.BigDecimal;

/**
 * Created by Volodymyr_Dotsenko on 9/30/2016.
 */
public class Factorial {

    public static double calculate(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        BigDecimal factorial = new BigDecimal(1);

        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(new BigDecimal(i));
        }

        return factorial.doubleValue();
    }
}
