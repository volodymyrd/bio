package com.gmail.volodymyrdotsenko.javabio.algorithms.tasks;

/**
 * Created by Volodymyr_Dotsenko on 9/29/2016.
 */
public class ProjecteulerNet {

    public static boolean isPolindrome(long value) {
        if (value == reverse(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPolindrome(String value) {
        int n = value.length() / 2;
        for (int i = 0; i < n; i++) {
            if (value.charAt(i) != value.charAt(value.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static long reverse(long value) {
        long reverse = 0;
        while (value > 0) {
            long lastDigit = value % 10;
            reverse = reverse * 10 + lastDigit;
            value /= 10;
        }

        return reverse;
    }


    public static long theLargestPalindromeFromTheProductOfNDigitNumbers(int n) {
        long product = 0;

        do {
            for (int d = 9; d > 0; d--) {
                int[] numbers = new int[n];
                for(int i = 0; i < n; i++){
                    //numbers[]
                }
                //long digit1 = Long.valueOf(buildDigit());
            }
        } while (isPolindrome(product));

        return product;
    }

    private String buildDigit(int[] numbers) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            builder.append(numbers[i]);
        }

        return builder.toString();
    }
}
