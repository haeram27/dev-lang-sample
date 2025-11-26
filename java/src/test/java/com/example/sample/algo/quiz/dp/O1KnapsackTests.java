package com.example.sample.algo.quiz.dp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class O1KnapsackTests {

    private static Stream<Arguments> argSupplier() {
        return Stream.of(
        // @formatter:off
        // Arguments.of(items[][], answer(value))
        // items[number of items][max available weight of knapsack] row == 0
        // items[weight of each item][value of each item] row > 0_, answer(value))
            Arguments.of(new int[][]{{4, 7}, {6, 13}, {4, 8}, {3, 6}, {5, 12}}, 14)
        // @formatter:on
        );
    }

    void printm(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(String.format("%3d ", m[i][j]));
            }
            System.out.println();
        }
    }

    /* 
     * Quest: https://www.acmicpc.net/problem/12865
     */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void quest(int[][] input, int target) {
        // TODO:

    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    void solution(int[][] input, int answer) {
        int n = input[0][0]; // number of items
        int maxw = input[0][1]; // max weight of given knapsack
        int[][] maxv = new int[n + 1][maxw + 1]; // max value in a knapsack table of each condition

        for (int i = 0; i < maxv.length; i++) {
            maxv[i][0] = 0;
        }

        for (int i = 0; i < maxv[0].length; i++) {
            maxv[0][i] = 0;
        }

        // i is item
        for (int i = 1; i < maxv.length; i++) {
            // w is max weight case for knapsack(1~maxw)
            for (int w = 1; w < maxv[0].length; w++) {
                int iw = input[i][0]; // current item's weight
                int iv = input[i][1]; // current item's value

                // if current available weight of knapsack(w) is smaller than current items weight(iw)
                // then can not put curren item to current knapsack
                // and keep previous items case's value
                // if current available weight of knapsack(w) is greater or equal than current items weight(iw)
                // then determine whether put i-th item into knapsack or not
                maxv[i][w] = (iw > w) ? maxv[i - 1][w] : Math.max(maxv[i - 1][w], maxv[i - 1][w - iw] + iv);
            }
        }

        // @formatter:off
        /* maxv[item index][knapsac weights]
           answer is maxv[4][7]
            -   1   2   3   4   5   6   7
          ---------------------------------
         -| 0   0   0   0   0   0   0   0 
         1| 0   0   0   0   0   0  13  13 
         2| 0   0   0   0   8   8  13  13 
         3| 0   0   0   6   8   8  13  14 
         4| 0   0   0   6   8  12  13  14
         */
        // @formatter:on

        printm(maxv);
        assertEquals(answer, maxv[n][maxw]);
    }

}
