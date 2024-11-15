package com.example.sample.quiz.bruteforce;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AllNumberResolverTests {

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
            Arguments.of(new int[]{3, 7, 1, 2, 8}, 5)
        // @formatter:on
        );
    }

    /* quiz : greedy example
     * retuen if it is possible to make number n with numbers in array a.
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void numberResolver(int[] a, int n) {
        boolean result = false;
        // TODO:

        System.out.println(result);
    }

    /* answer
     * hint: combination entire index
     */
    boolean isResolved;
    int target;

    boolean numberResolverA(int[] a, int n) {
        target = n;
        for (int r = 1; r < a.length; r++) {
            int[] out = new int[r];
            combA(a, out, r, 0, 0);
        }
        return isResolved;
    }

    void combA(int[] data, int[] out, int r, int depth, int start) {
        if (depth == r) {
            int sum = 0;
            for (var a : out) {
                sum += a;
            }

            if (sum == target)
                isResolved = true;

            return;
        }

        for (int i = start; i < data.length; i++) {
            out[depth] = data[i];
            combA(data, out, r, depth + 1, i + 1);
            if (isResolved == true)
                break;
        }
    }

    @Test
    void runA() {
        int[] a = {3, 7, 1, 2, 8};
        int num = 5;
        long start = System.nanoTime();
        System.out.println(numberResolverA(a, num));
        System.out.println(System.nanoTime() - start);
    }
}
