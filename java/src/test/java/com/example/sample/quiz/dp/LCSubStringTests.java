package com.example.sample.quiz.dp;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LCSubStringTests {
    private static Stream<Arguments> argsSupplier() {
        return Stream.of(
        // @formatter:off
         // Arguments.of(nums[], k, expected)
            Arguments.of("ABCD", "DBCA"),
            Arguments.of("123commonHI", "HIcommon123")
        // @formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("argsSupplier")
    void lcSubstring(String s1, String s2) {
        int max = 0;
        // TODO:


        System.out.println(max);
    }

    @ParameterizedTest
    @MethodSource("argsSupplier")
    void solution(String s1, String s2) {
        int[][] LCS = new int[s1.length() + 1][s2.length() + 1];

        int max = 0, maxi = 0, maxj = 0;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    LCS[i][j] = LCS[i - 1][j - 1] + 1;
                    if (max < LCS[i][j]) {
                        max = LCS[i][j];
                        maxi = i;
                        maxj = j;
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(LCS).replace("],", "],\n"));

        System.out.println(max);
    }



}
