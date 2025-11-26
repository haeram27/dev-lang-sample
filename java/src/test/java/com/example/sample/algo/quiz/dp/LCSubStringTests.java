package com.example.sample.algo.quiz.dp;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/* Longest Sub String */
public class LCSubStringTests {
    void printm(int[][] m) {
        int r = 0, c = 0, rlen = m.length, clen = m[0].length;
        for (r = 0; r < rlen; r++) {
            for (c = 0; c < clen; c++) {
                System.out.print(m[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
         // Arguments.of(nums[], k, expected)
            Arguments.of("ABCD", "DBCA"),
            Arguments.of("123commonHI", "HIcommon123")
        // @formatter:on
        );
    }

    /* QUIZ */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void lcSubstring(String s1, String s2) {
        int max = 0;
        // TODO:


        System.out.println(max);
    }

    /* ANSER */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void solution(String s1, String s2) {
        int[][] LCS = new int[s1.length() + 1][s2.length() + 1];

        int max = 0, maxr = 0, maxc = 0;
        for (int r = 1; r <= s1.length(); r++) {
            for (int c = 1; c <= s2.length(); c++) {
                if (s1.charAt(r - 1) == s2.charAt(c - 1)) {
                    LCS[r][c] = LCS[r - 1][c - 1] + 1;
                    if (max < LCS[r][c]) {
                        max = LCS[r][c];
                        maxr = r;
                        maxc = c;
                    }
                }
            }
        }

        System.out.println(Arrays.deepToString(LCS).replace("],", "],\n"));
        System.out.println(String.format("max: %d, maxr: %d, maxc: %d", max, maxr, maxc));
    }
}
