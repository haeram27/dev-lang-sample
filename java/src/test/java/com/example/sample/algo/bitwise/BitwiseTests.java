package com.example.sample.algo.bitwise;

import org.junit.jupiter.api.Test;

public class BitwiseTests {
    @Test
    void sum() {
        /*
         * Half Adder (binary add operation)
         * sum = x ^ y 
         * carry = (x & y) << 1
         */


        int x = 9, y = 3;
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
        System.out.println("sum: " + x);
    }

    @Test
    void multiple() {
        int a = 499;

        int multiple = a << 3; // == a * ((int)Math.pow(2,3))

        System.out.println("multiple: " + multiple);
    }

    @Test
    void div() {
        int a = 499;

        int quotient = a >>> 3; // == a / ((int)Math.pow(2,3))
        int rest = a & (8 - 1); // == a % ((int)Math.pow(2,3))

        System.out.println("quotient: " + quotient);
        System.out.println("rest: " + rest);
    }

    @Test
    void bitwiseAuthoriy() {
        final int PERMISSION_READ = 1 << 0; // 0001
        final int PERMISSION_WRITE = 1 << 1; // 0010
        final int PERMISSION_DELETE = 1 << 2; // 0100
        final int PERMISSION_EXECUTE = 1 << 3; // 1000

        int userPermission = PERMISSION_READ | PERMISSION_WRITE; // 0011
        int groupPermission = PERMISSION_READ | PERMISSION_EXECUTE; // 1001

        boolean hasReadPermission = (userPermission & PERMISSION_READ) != 0; // true
        boolean hasDeletePermission = (groupPermission & PERMISSION_DELETE) != 0; // false

        System.out.println(hasReadPermission);
        System.out.println(hasDeletePermission);
    }


    @Test
    void bitwiseSet() {
        int set = (1 << 3) | (1 << 5) | (1 << 7); // 1010 1000
                                                  // 7654 3210

        boolean hasElement5 = (set & (1 << 5)) != 0; // true
        boolean hasElement6 = (set & (1 << 6)) != 0; // false

        System.out.println(hasElement5);
        System.out.println(hasElement6);
    }


    @Test
    void bitwiseFlag() {
        final int FLAG_POWER_OF_TWO = 1 << 0; // 0001
        final int FLAG_NEGATIVE = 1 << 1; // 0010

        int number = 8; // power of 2
        int flags = 0;

        if ((number & (number - 1)) == 0) { // judge power of 2
            flags |= FLAG_POWER_OF_TWO; // set flag of power of 2
        }

        if (number < 0) {
            flags |= FLAG_NEGATIVE; // set flag of number is minus
        }

        if ((flags & FLAG_POWER_OF_TWO) != 0) {
            System.out.println(number + " is power of two");
        }

        if ((flags & FLAG_NEGATIVE) != 0) {
            System.out.println(number + " is negative");
        }
    }
}
