package com.example.sample.quiz.etc;

import java.util.HashSet;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FindingPrimeTests {
    /*
     * Finding primes
     * https://school.programmers.co.kr/learn/courses/30/lessons/42839
     * find all primes using every character of given string
     */

    @ParameterizedTest
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @ValueSource(strings = {"17", "011"}) // 3, 2
    public void solution(String numbers) {
        HashSet<Integer> set = new HashSet<>();
        permutation("", numbers, set);

        int count = 0;
        for (var a : set) {
            // System.out.println(a);

            if (isPrime(a))
                count++;
        }

        System.err.println(count);
    }

    boolean isPrime(int x) {
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

    public void permutation(String prefix, String str, HashSet<Integer> set) {
        int n = str.length();
        // if (n == 0)
        //     System.out.println(prefix);

        if (!prefix.equals(""))
            set.add(Integer.valueOf(prefix));

        for (int i = 0; i < n; i++)
            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), set);
    }

}
