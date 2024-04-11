package com.example.sample.quiz.twopivot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ThreeSumGreedyTests {
    /*
        Quest: https://leetcode.com/problems/3sum/description/
        
        Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
        Notice that the solution set must not contain duplicate triplets.
        
        
        Example 1:
        
        Input: nums = [-1,0,1,2,-1,-4]
        Output: [[-1,-1,2],[-1,0,1]]
        Explanation: 
        nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
        nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
        nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
        The distinct triplets are [-1,0,1] and [-1,-1,2].
        Notice that the order of the output and the order of the triplets does not matter.
        Example 2:
        
        Input: nums = [0,1,1]
        Output: []
        Explanation: The only possible triplet does not sum up to 0.
        Example 3:
        
        Input: nums = [0,0,0]
        Output: [[0,0,0]]
        Explanation: The only possible triplet sums up to 0.
        
        
        Constraints:
        
        3 <= nums.length <= 3000
        -105 <= nums[i] <= 105
    */

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
            // Arguments.of(nums[],target)
                Arguments.of(new int[]{-1, 0, 1, 2, -1, -4}),
                Arguments.of(new int[]{0, 1, 1}),
                Arguments.of(new int[]{0, 0, 0})
            // @formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    void quest(int[] nums) {
        // TODO:
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void threeSum(int[] nums) {
        Arrays.sort(nums); // need sort!!! Greedy
        Set<List<Integer>> set = new HashSet<>();
        int i = 0, l = 0, r = 0;
        for (i = 0; i < nums.length - 2; i++) {
            l = i + 1;
            r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    set.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l] == nums[l + 1])
                        l++;
                    while (l < r && nums[r] == nums[r - 1])
                        r--;
                    l++;
                    r--;
                } else if (sum < 0)
                    l++;
                else
                    r--;
            }
        }

        set.forEach(list -> {
            list.forEach(e -> System.out.print(e + " "));
            System.out.println();
        });
    }
}
