package com.example.sample.quiz.etc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PrimeTests {

    /* quest */
    boolean isPrime(int x) {
        // TODO:

        return false;
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 7, 11, 14})
    void runIsPrime(int num) {
        System.out.println(String.format("%d %s", num, isPrime(num)));
    }

    boolean isPrimeA(int x) {
        if (x <= 1)
            return false;
        else if (x == 2)
            return true;
        else if (x % 2 == 0)
            return false;

        // sqrt(x) is square root of x
        for (int i = 3; i <= Math.sqrt(x); i += 2) {
            if (x % i == 0)
                return false;
        }

        return true;
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 7, 11, 14})
    void runIsPrimeA(int num) {
        System.out.println(String.format("%d %s", num, isPrimeA(num)));
    }

    /* Sieve of Eratosthenes */
    public static void getChe(int num) {
        int i;
        int[] arr = new int[num + 1];

        // 0 and 1 is not prime
        if (num <= 1)
            return;

        // init array
        // start i is 2
        for (i = 2; i <= num; i++) {
            arr[i] = i;
        }

        // point!! if number i is not set to 0
        for (i = 2; i <= num; i++) {
            // pass items already set as 0
            if (arr[i] == 0)
                continue;
            // set as 0 to i's multiple number except i
            for (int j = i + i; j <= num; j += i) {
                arr[j] = 0;
            }
        }

        // print
        int count = 0, lastCount = count;
        for (i = 2; i <= num; ++i) {
            if (arr[i] != 0) {
                System.out.print(arr[i] + " ");
                ++count;
            }

            if ((count != 0) && (count % 10 == 0) && (count != lastCount)) {
                System.out.println("");
                lastCount = count;
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 50000})
    void runGetChe(int num) {
        getChe(num);
    }
}
