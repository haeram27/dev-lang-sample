package com.example.sample.quiz.twopivot;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MaxSumSubArray {

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
            Arguments.of(new int[]{-2,1,-3,4,-1,2,1,-5,4}, 6) //4,-1,2,1
        // @formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void maxSumSubArray(int[] nums, int expected) {

    }

    /*
     * Kadane's Algorithme (maximum sum of subarray including negative number)
     * Kind: DP
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    public void maxSumSubArrayA(int[] nums, int expected) {
        int max, curSum;
        max = curSum = 0;

        for (int i = 0; i < nums.length; i++) {
            curSum = Math.max(curSum + nums[i], nums[i]);
            max = Math.max(max, curSum);
        }

        System.out.println(max);
    }

    /*
     * BruteForce (maximum sum of subarray including negative number)
     * Kind: DP
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    public void maxSumSubArrayB(int[] nums, int expected) {
        int max, curSum;
        max = curSum = 0;

        for (int i = 0; i < nums.length; i++) {
            curSum = 0;
            for (int j = i; j < nums.length; j++) {
                curSum += nums[j];
                max = Math.max(max, curSum);
            }
        }

        System.out.println(max);
    }
}
