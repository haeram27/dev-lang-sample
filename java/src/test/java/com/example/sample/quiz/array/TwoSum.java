package com.example.sample.quiz.array;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TwoSum {

    private static Stream<Arguments> argsSupplier() {
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
     * Hint: All combination of two index in a array
     */
    @ParameterizedTest
    @MethodSource("argsSupplier")
    void quest(int[] a, int target) {
        // TODO:
    }

    @ParameterizedTest
    @MethodSource("argsSupplier")
    void answer(int[] a, int target) {
        var len = a.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (a[i] + a[j] == target) {
                    System.out.println(i + ", " + j);
                    return;
                } else if (a[len - 1 - i] + a[len - 1 - j] == target) {
                    System.out.println((len - 1 - i) + ", " + (len - 1 - j));
                    return;
                }
            }
        }
    }
}
