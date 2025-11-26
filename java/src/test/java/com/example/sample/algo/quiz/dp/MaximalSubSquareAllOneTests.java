package com.example.sample.algo.quiz.dp;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class MaximalSubSquareAllOneTests {

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

    void printMaxSubSquareTest(int[][] m) {
        // TODO under here
        int rlen = 0; // TODO
        int clen = 0; // TODO
        int[][] s = new int[rlen][clen];
        int max = 0, maxr = 0, maxc = 0;
        int r, c;

        // TODO:: Set first column of S[][]

        // TODO:: Set first row of S[][]

        // TODO:: Construct other entries of S[][]

        // TODO:: Find the maximum entry, and indexes of maximum entry in s[][]
        // 3 4 3
        /* Print maximum entry in s[][] */
        System.out.println(String.format("max: %d, maxr: %d, maxc: %d", max, maxr, maxc));
        System.out.println();

        /* Print result */
        for (r = 0; r < rlen; r++) {
            for (c = 0; c < clen; c++) {
                System.out.print(s[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();

        // TODO:: Print maximal sub-matrix in M of all 1
        // for (r ...) {
        //     for (c ...) {
        //         System.out.print(s[r][c] + " ");
        //     }
        //     System.out.println();
        // }
        System.out.println();

    }

    /* Driver code */
    @Test
    void run() {
        int[][] m1 = {
            // @formatter:off
            {0, 1, 1, 0, 1},
            {1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0}
            // @formatter:on
        };
        printMaxSubSquareTest(m1);

        int m2[][] = {
            // @formatter:off
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1}
            // @formatter:on
        };
        printMaxSubSquareTest(m2);
    }

    /**
    Solution:
    https://www.geeksforgeeks.org/maximum-size-sub-matrix-with-all-1s-in-a-binary-matrix/
    
    1) Construct a sum matrix S[R][C] for the given M[R][C].
     a) Copy first row and first columns as it is from M[][] to S[][]
     b) For other entries, use following expressions to construct S[][]
        If M[i][j] is 0
            S[i][j] = 0 
        Else // If M[i][j] is 1 then
            S[i][j] = min(S[i][j-1], S[i-1][j], S[i-1][j-1]) + 1
    2) Find the maximum entry in S[R][C]
    3) Using the value and coordinates of maximum entry in S[i], print sub-matrix of M[][]
    
    figure1. minimal square
    S[i-1][j-1] | S[i][j-1]
    ------------------------
    S[i-1][j]   | S[i][j]
    
    endxy position of minimal subsqure = S[i][j]
    non-endxy position of minimal subsqure = S[i][j-1], S[i-1][j], S[i-1][j-1]
    
    
    if S[i][j] > 0 then M[i][j] == 1
    if S[i][j] == N then min(S[i][j-1], S[i-1][j], S[i-1][j-1]) == N-1
    if S[i][j] == 1 then S[i][j-1], S[i-1][j], S[i-1][j-1] >= 0 
              this means minimal square's one of non-endxy position member has 0
    if S[i][j] == 2 then S[i][j-1], S[i-1][j], S[i-1][j-1] >= 1 
              this means minimal square's all non-endxy position has at least 1
    if S[i][j] == 3 then S[i][j-1], S[i-1][j], S[i-1][j-1] >= 2
              this means minimal square's all non-endxy position has at least 2
    S[i][j] >= N (N>=2) means start-point::S[i-(N-1)][j-(N-1)] end-point::S[i][j] square fills 1
    
    given:
    M[][] = {{0, 1, 1, 0, 1},
         {1, 1, 0, 1, 0},
         {0, 1, 1, 1, 0},
         {1, 1, 1, 1, 0},
         {1, 1, 1, 1, 1},
         {0, 0, 0, 0, 0}};
    
    For the given M[][] in the above example, constructed S[][] would be:
       0  1  1  0  1
       1  1  0  1  0
       0  1  1  1  0
       1  1  2  2  0
       1  2  2  3  1
       0  0  0  0  0
     */
    void printMaxSubSquareTestA(int[][] M) {
        int rlen = M.length;
        int clen = M[0].length;

        int[][] S = new int[rlen][clen];

        int r = 0, c = 0;
        int max = 0, maxr = 0, maxc = 0;

        /* Set first column of S[][] */
        for (c = 0; c < clen; c++) {
            S[0][c] = M[0][c];
        }

        /* Set first row of S[][] */
        for (r = 0; r < rlen; r++) {
            S[r][0] = M[r][0];
        }

        /* Construct other entries of S[][] and get position of S has max count  */
        max = 0;
        for (r = 1; r < rlen; r++) {
            for (c = 1; c < clen; c++) {
                if (M[r][c] == 0) {
                    S[r][c] = 0;
                } else {
                    S[r][c] =
                            Stream.of(S[r][c - 1], S[r - 1][c], S[r - 1][c - 1]).min((o1, o2) -> o1 - o2).orElse(0) + 1;

                    if (S[r][c] > max) {
                        max = S[r][c];
                        maxr = r;
                        maxc = c;
                    }
                }
            }
        }

        /* Print maximum entry in S[][] */
        System.out.println(String.format("max: %d, maxr: %d, maxc: %d", max, maxr, maxc));
        System.out.println();

        /* Print result */
        printm(S);

        /* Print maximal sub-matrix in M of all 1 */
        for (r = maxr; r > maxr - max; r--) {
            for (c = maxc; c > maxc - max; c--) {
                System.out.print(S[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /* Driver code */
    @Test
    void runAnswer() {
        int[][] m1 = {
            // @formatter:off
            {0, 1, 1, 0, 1},
            {1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0}
            // @formatter:on
        };
        printMaxSubSquareTestA(m1);

        int m2[][] = {
            // @formatter:off
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1}
            // @formatter:on
        };
        printMaxSubSquareTestA(m2);
    }
}
