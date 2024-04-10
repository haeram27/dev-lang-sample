package com.example.sample.quiz.array;

import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LongestSubArrayAllElemFrequencyIsOneTests {
    private static Stream<Arguments> argsSupplier() {
        return Stream.of(
        // @formatter:off
         // Arguments.of(nums[], k, expected)
            Arguments.of("abcabcbb", 3),
            Arguments.of("bbbbb", 1),
            Arguments.of("pwwkew", 3),
            Arguments.of("aab", 2),
            Arguments.of("a", 1)
        // @formatter:on
        );
    }

    /* 
     * Quest: find longest sub array's length, frequency of each elements SHOULD be 1
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
     */
    @ParameterizedTest
    @MethodSource("argsSupplier")
    void quest(String s, int answer) {
        int max = 0;
        // TODO:

        System.out.println(max + " " + answer);
    }

    @ParameterizedTest
    @MethodSource("argsSupplier")
    void answer(String s, int answer) {
        var a = s.toCharArray();
        var len = a.length;
        int l = 0, max = 0;
        var m = new HashMap<Character, Integer>();

        for (int r = 0; r < len; r++) {
            m.put(a[r], m.getOrDefault(a[r], 0) + 1);
            while (m.get(a[r]) > 1) {
                m.put(a[l], m.get(a[l]) - 1);
                l++;
            }
            max = Math.max(max, (r - l + 1));
        }

        System.out.println(max + " " + answer);
    }
}
