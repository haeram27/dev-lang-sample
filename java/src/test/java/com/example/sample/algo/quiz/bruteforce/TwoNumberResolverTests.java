package com.example.sample.algo.quiz.bruteforce;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TwoNumberResolverTests {

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
         // Arguments.of(nums[],target)
            Arguments.of(new int[]{2, 7, 11, 15}, 9), // 0, 1
            Arguments.of(new int[]{3, 2, 4}, 6), // 1, 2
            Arguments.of(new int[]{3, 3}, 6) // 0, 1

        // @formatter:on
        );
    }

    /* 
     * Quest: https://leetcode.com/problems/two-sum/description/
     *   check there is combination of two number which has value of target
     *   @return print two index of array have values can make target number
     * Hint: combination two index
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void quest(int[] a, int target) {
        // TODO:
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    void answer(int[] a, int target) {
        var len = a.length;

        System.out.println("====================");
        // @formatter:off
        label: 
        // @formatter:on
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (a[i] + a[j] == target) {
                    System.out.println(String.format("%d(%d) %d(%d)", i, a[i], j, a[j]));
                    break label;
                } else if (a[len - 1 - i] + a[len - 1 - j] == target) {
                    System.out.println(
                            String.format("%d(%d) %d(%d)", len - 1 - i, a[len - 1 - i], len - 1 - j, a[len - 1 - j]));
                    break label;
                }
            }
        }
    }
}
