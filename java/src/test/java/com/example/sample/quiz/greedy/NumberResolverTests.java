package com.example.sample.quiz.greedy;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class NumberResolverTests {
    /* quiz : greedy example
     * retuen if it is possible to make number n with numbers in array a.
     */
    boolean numberResolver(int[] a, int n) {
        boolean result = false;
        // TODO:

        return result;
    }

    @Test
    void run(String[] args) {
        int[] a = { 3, 7, 1, 2, 8 };
        int num = 5;

        System.out.println(numberResolver(a, num));
    }

    /* answer */
    boolean numberResolverA(int[] a, int n) {
        boolean result = false;

        // greedy: sort a as decending order to operate biggest first
        Arrays.parallelSort(a);
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            int temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }

        int sum = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] > n) {
                continue;
            }

            if (a[i] == n) {
                result = true;
                break;
            }

            int temp = sum + a[i];
            if (temp > n) {
                continue;
            }

            if (temp < n) {
                System.out.println(a[i]);
                sum += a[i];
            }

            if (temp == n) {
                System.out.println(a[i]);
                result = true;
                break;
            }
        }
        return result;
    }

    @Test
    void runA() {
        int[] a = { 3, 7, 1, 2, 8 };
        int num = 5;

        System.out.println(numberResolverA(a, num));
    }
}
