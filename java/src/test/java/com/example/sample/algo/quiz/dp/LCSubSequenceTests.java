package com.example.sample.algo.quiz.dp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/* Longest Sub String */
public class LCSubSequenceTests {
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
            Arguments.of("ABACCD", "ACDF"),
            Arguments.of("CRAB", "TCMNA"),
            Arguments.of("12bcd", "zxy21")
        // @formatter:on
        );
    }

    /* QUIZ */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void lcsubsequence(String s1, String s2) {
        // TODO:


        System.out.println("length: ");
    }

    /* ANSER */
    // https://youtu.be/z8KVLz9BFIo?si=T30mZQFvVbUPV6kN
    @ParameterizedTest
    @MethodSource("argSupplier")
    void lcsubseqLength(String s1, String s2) {
        int[][] LCS = new int[s1.length() + 1][s2.length() + 1];

        for (int r = 1; r <= s1.length(); r++) {
            for (int c = 1; c <= s2.length(); c++) {
                if (s1.charAt(r - 1) == s2.charAt(c - 1))
                    LCS[r][c] = LCS[r - 1][c - 1] + 1;
                else
                    LCS[r][c] = Math.max(LCS[r - 1][c], LCS[r][c - 1]);
            }
        }

        System.out.println(Arrays.deepToString(LCS).replace("],", "],\n"));
        System.out.println(String.format("length: %d", LCS[s1.length()][s2.length()]));
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    void lcsubseqString(String s1, String s2) {
        int[][] LCS = new int[s1.length() + 1][s2.length() + 1];
        int[][] trace = new int[s1.length() + 1][s2.length() + 1];

        for (int r = 1; r <= s1.length(); r++) {
            for (int c = 1; c <= s2.length(); c++) {
                if (s1.charAt(r - 1) == s2.charAt(c - 1)) {
                    LCS[r][c] = LCS[r - 1][c - 1] + 1;
                    trace[r][c] = 1; // come from up+left
                } else {
                    if (LCS[r - 1][c] > LCS[r][c - 1]) {
                        LCS[r][c] = LCS[r - 1][c];
                        trace[r][c] = 2; // come from up
                    } else {
                        LCS[r][c] = LCS[r][c - 1];
                        trace[r][c] = 3; // come from left
                    }
                }
            }
        }
        System.out.println("-----------------");
        System.out.println(String.format("length: %d", LCS[s1.length()][s2.length()]));
        System.out.println(Arrays.deepToString(LCS).replace("],", "],\n"));
        System.out.println("trace:");
        System.out.println(Arrays.deepToString(trace).replace("],", "],\n"));
        printSubSequence1(trace, s1, s2);
        printSubSequence2(LCS, s1, s2);
    }

    void printSubSequence1(int[][] t, String s1, String s2) {
        int rlen = t.length;
        int clen = t[0].length;
        int r = rlen - 1;
        int c = clen - 1;
        var st = new ArrayDeque<Character>();

        while (!(t[r][c] == 0 && t[r][c] == 0 && t[r][c] == 0)) {
            if (t[r][c] == 1) {
                st.push(s1.charAt(r - 1));
                r = r - 1;
                c = c - 1;
            } else if (t[r][c] == 2) {
                r = r - 1;

            } else if (t[r][c] == 3) {
                c = c - 1;
            }
        }

        System.out.println("subsequence1:");
        while (!st.isEmpty()) {
            System.out.print(st.pop() + " ");
        }
        System.out.println();
    }

    void printSubSequence2(int[][] L, String X, String Y) {
        int i = X.length();
        int j = Y.length();

        int index = L[i][j];
        int temp = index;

        // Create a character array to store the lcs string
        char[] lcs = new char[index + 1];
        lcs[index] = '\u0000'; // Set the terminating character

        // Start from the right-most-bottom-most corner and
        // one by one store characters in lcs[]

        while (i > 0 && j > 0) {
            // If current character in X[] and Y are same,
            // then current character is part of LCS
            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                // Put current character in result
                lcs[index - 1] = X.charAt(i - 1);

                // reduce values of i, j and index
                i--;
                j--;
                index--;
            }

            // If not same, then find the larger of two and
            // go in the direction of larger value
            else if (L[i - 1][j] > L[i][j - 1])
                i--;
            else
                j--;
        }

        // Print the lcs
        System.out.print("LCS of " + X + " and " + Y + " is ");
        for (int k = 0; k <= temp; k++)
            System.out.print(lcs[k]);
        System.out.println();
    }
}
