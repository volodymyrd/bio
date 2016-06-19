package com.gmail.volodymyrdotsenko.javabio.simple;

/**
 * Created by Volodymyr Dotsenko on 6/19/16.
 */
public class SubStringUtils {

    public static int slideWindowPatternCount(String text, String pattern) {
        int count = 0;
        for (int i = 0; i < (text.length() - pattern.length()); i++) {
            boolean equal = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    equal = false;
                    break;
                }
            }

            if(equal){
                count++;
            }
        }

        return count;
    }
}