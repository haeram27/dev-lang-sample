package com.example.sample.quiz.bitwise;

import org.junit.jupiter.api.Test;

public class BitsAddNumberTests {

    int add(int x, int y) {
        int keep = (x & y) << 1;
        int res = x ^ y;
        System.out.println(String.format("%8s", Integer.toBinaryString(x)).replace(" ", "0"));
        System.out.println(String.format("%8s", Integer.toBinaryString(y)).replace(" ", "0"));
        System.out.println(String.format("%8s", Integer.toBinaryString(keep)).replace(" ", "0"));
        System.out.println(String.format("%8s", Integer.toBinaryString(res)).replace(" ", "0"));
        System.out.println("---------------------");

        // If bitwise & is 0, then there
        // is not going to be any carry. 
        // Hence result of XOR is addition.
        if (keep == 0)
            return res;

        return add(keep, res);
    }

    @Test
    void run() {
        System.out.println(add(9, 3));

    }

    @Test
    void div() {
        int a = 499;
        int rest = a & (8 - 1); // == a % ((int)Math.pow(2,3)) 
        int quotient = a >>> 3; // == a / ((int)Math.pow(2,3)) 
    }



}
