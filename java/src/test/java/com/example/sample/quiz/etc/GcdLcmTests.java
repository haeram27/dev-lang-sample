package com.example.sample.quiz.etc;

import org.junit.jupiter.api.Test;

public class GcdLcmTests {

    /* 
     * GCD: greatest common divisor
     * gcd(big, small) == gcd(small, rest), rest>0
     * 
     * LCM: leat common multiple
     * lcm = (a*b)/gcd(a,b)
     * 
     *    a / b = c
     *    a % b = d
     *    a : dividend
     *    b : divisor
     *    c : quotient
     *    d : rest
     */


    /** 
    * QUIZ
    */
    int greatestCommonDivisor(int a, int b) {
        // TODO:

        return 0;
    }

    int leastCommonMultiple(int a, int b, int gcd) {
        // TODO:

        return 0;
    }

    @Test
    void run() {
        int a = 12, b = 8;

        // GCD: greatest common divisor
        int gcd = greatestCommonDivisor(a, b);
        System.out.println(String.format("GCD of %d and %d is %d.", a, b, gcd));

        // LCM: leat common multiple
        int lcm = leastCommonMultiple(a, b, gcd);
        System.out.println(String.format("LCM of %d and %d is %d.", a, b, lcm));
    }

    /**
     * ANSWER
     */
    public static int greatestCommonDivisorA(int a, int b) {
        int big, small;
        int rest;

        if ((a <= 0) && (b <= 0)) {
            return 0;
        }

        if (a > b) {
            big = a;
            small = b;
        } else {
            big = b;
            small = a;
        }

        if (small == 0) {
            return big;
        }

        do {
            rest = big % small;
            if (rest > 0) {
                big = small;
                small = rest;
            }
        } while (rest > 0);

        return small;
    }

    /* 
     * LCM: leat common multiple
     * lcm = (a*b)/gcd(a,b)
     */
    public static int leastCommonMultipleA(int a, int b, int gcd) {
        if ((a <= 0) || (b <= 0) || (gcd <= 0)) {
            return 0;
        }
        return (a * b) / gcd;
    }

    @Test
    void runA() {
        int a = 12, b = 8;

        // GCD: greatest common divisor
        int gcd = greatestCommonDivisorA(a, b);
        System.out.println(String.format("GCD of %d and %d is %d.", a, b, gcd));

        // LCM: leat common multiple
        int lcm = leastCommonMultipleA(a, b, gcd);
        System.out.println(String.format("LCM of %d and %d is %d.", a, b, lcm));
    }
}
