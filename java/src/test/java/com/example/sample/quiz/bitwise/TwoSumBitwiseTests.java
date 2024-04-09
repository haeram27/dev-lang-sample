package com.example.sample.quiz.bitwise;

import org.junit.jupiter.api.Test;

/* NOT IMPORTANT!!! */
public class TwoSumBitwiseTests {

    int add(int x, int y) {
        /*
         * Half Adder (binary add operation)
         * sum = x ^ y 
         * carry = (x & y) << 1
         */

        // Iterate till there is no carry 
        while (y != 0) {
            int sum = x ^ y;
            int carry = (x & y) << 1;

            x = sum; // sum
            y = carry; // carry

            System.out.println("sum  : " + sum);
            System.out.println("carry: " + carry);
            System.out.println(String.format("sum..:_%8s", Integer.toBinaryString(sum)).replace(" ", "0"));
            System.out.println(String.format("carry:_%8s", Integer.toBinaryString(carry)).replace(" ", "0"));
            System.out.println("---------------------");
        }
        return x;
    }

    @Test
    void run() {
        System.out.println(add(9, 3)); // 12
    }

    @Test
    void div() {
        int a = 499;
        int rest = a & (8 - 1); // == a % ((int)Math.pow(2,3)) 
        int quotient = a >>> 3; // == a / ((int)Math.pow(2,3)) 
    }



}
