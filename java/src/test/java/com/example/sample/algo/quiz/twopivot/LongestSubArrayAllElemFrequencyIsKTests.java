package com.example.sample.algo.quiz.twopivot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LongestSubArrayAllElemFrequencyIsKTests {

    void printa(int[] a, int s, int e) {
        if (e >= a.length)
            return;

        while (s <= e) {
            System.out.print(a[s] + " ");
            s++;
        }
        System.out.println();
    }

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
         // Arguments.of(nums[], k, expected)
            Arguments.of(new int[]{1,2,3,1,2,3,1,2}, 2, 6),
            Arguments.of(new int[]{1,2,1,2,1,2,1,2}, 1, 2),
            Arguments.of(new int[]{5,5,5,5,5,5,5}, 4, 4)
        // @formatter:on
        );
    }

    /*
     * https://leetcode.com/problems/length-of-longest-subarray-with-at-most-k-frequency/description
     * find longest sub array's length, frequency of each elements SHOULD be less or equal 'k'
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    public void maxSubarrayLength(int[] nums, int k, int expected) {
        int max = 0;

        System.out.println(max);
        assertEquals(expected, max);
    }

    /*
     * https://leetcode.com/problems/length-of-longest-subarray-with-at-most-k-frequency/description
     * find longest sub array's length, frequency of each elements SHOULD be less or equal 'k'
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    public void maxSubarrayLengthA(int[] nums, int k, int expected) {
        int max = 0;
        int l = 0, r = 0; //left, right
        HashMap<Integer, Integer> m = new HashMap<>();

        for (r = 0; r < nums.length; r++) {
            m.put(nums[r], m.getOrDefault(nums[r], 0) + 1);

            // find l that is NOT "nums[r] > k"
            while (m.get(nums[r]) > k) {
                m.put(nums[l], m.get(nums[l]) - 1);
                l++;
                printa(nums, l, r);
            }
            max = Math.max(max, r - l + 1);
        }

        System.out.println(max);
        assertEquals(expected, max);
    }
}
