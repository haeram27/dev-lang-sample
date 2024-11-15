package com.example.sample.quiz.bruteforce;

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

    /*
     * Quest
     */
    @ParameterizedTest
    @ValueSource(strings = {"17", "011"}) // 3, 2
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void solution(String numbers) {
        HashSet<Integer> primes = new HashSet<>();
        // TODO:

        System.err.println(primes.size());
    }


    /*
     * Answer
     */
    @ParameterizedTest
    @ValueSource(strings = {"17", "011"}) // 3, 2
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void solutionA(String numbers) {
        HashSet<Integer> primes = new HashSet<>();
        char[] data = numbers.toCharArray();
        for (int i = 1; i <= numbers.length(); i++) {
            char[] out = new char[i];
            boolean[] visited = new boolean[numbers.length()];
            permA(data, out, i, 0, visited, primes);
        }

        System.err.println(primes.size());
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

    void permA(char[] data, char[] out, int r, int depth, boolean[] visited, HashSet<Integer> primes) {
        if (depth == r) {
            int x = Integer.valueOf(new String(out));
            if (isPrimeA(x))
                primes.add(x);
            return;
        }
        for (int i = 0; i < data.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                out[depth] = data[i];
                permA(data, out, r, depth + 1, visited, primes);
                visited[i] = false;
            }
        }
    }

}
