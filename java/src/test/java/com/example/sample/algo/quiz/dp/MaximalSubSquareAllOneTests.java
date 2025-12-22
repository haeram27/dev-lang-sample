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

    void printMaxSubSquareTestB(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            System.out.println("입력된 행렬이 비어있습니다.");
            return;
        }

        int rlen = m.length;
        int clen = m[0].length;
        int[][] s = new int[rlen][clen];
        int max = 0, maxr = 0, maxc = 0;
        int r, c;

        // 1. 첫 번째 열 초기화
        for (r = 0; r < rlen; r++) {
            s[r][0] = m[r][0];
            if (s[r][0] > max) {
                max = s[r][0];
                maxr = r;
                maxc = 0;
            }
        }

        // 2. 첫 번째 행 초기화
        for (c = 1; c < clen; c++) {
            s[0][c] = m[0][c];
            if (s[0][c] > max) {
                max = s[0][c];
                maxr = 0;
                maxc = c;
            }
        }

        // 3. 나머지 DP 테이블(s) 채우기
        for (r = 1; r < rlen; r++) {
            for (c = 1; c < clen; c++) {
                if (m[r][c] == 1) {
                    // 왼쪽, 위, 왼쪽-위 대각선 값 중 가장 작은 값 + 1
                    s[r][c] = Math.min(s[r - 1][c - 1], Math.min(s[r][c - 1], s[r - 1][c])) + 1;
                } else {
                    s[r][c] = 0;
                }

                // 4. 최대값 및 위치 갱신
                if (s[r][c] > max) {
                    max = s[r][c];
                    maxr = r;
                    maxc = c;
                }
            }
        }

        System.out.println(String.format("가장 큰 정사각형의 크기: %d x %d", max, max));
        System.out.println(String.format("끝나는 위치: (row=%d, col=%d)", maxr, maxc));
        System.out.println();

        System.out.println("DP 테이블 (S):");
        printm(s);

        System.out.println("가장 큰 정사각형:");
        // 5. 결과 정사각형 출력
        for (r = maxr - max + 1; r <= maxr; r++) {
            for (c = maxc - max + 1; c <= maxc; c++) {
                System.out.print(m[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------\n");
    }

    /*
        공간 복잡도 개선 (O(M×N) → O(N))
        현재 구현은 원본 행렬 m과 동일한 크기의 DP 테이블 s를 추가로 사용하고 있음, 따라서 **공간 복잡도는 O(M×N)**
        하지만 DP 테이블을 채우는 공식을 다시 보면, s[r][c]를 계산할 때 필요한 것은 바로 윗 행(r-1)과 현재 행(r)의 데이터뿐이며, 그 이전 행(r-2, r-3, ...)의 데이터는 필요하지 않음
        이 점을 이용하여 공간 사용량을 O(N) (여기서 N은 열의 개수)으로 최적화할 수 있음

        코딩 테스트에서 이 문제를 만났을 때, 먼저 O(M×N) 공간을 사용하는 표준 DP 방식으로 푼 뒤, 면접관이 공간 최적화를 요구하면 O(N) 방식으로 개선하는 것이 가장 좋은 접근법
        현재 작성하신 코드는 문제 해결을 위한 매우 훌륭하고 정확한 첫 번째 솔루션
     */

    void printMaxSubSquareTestOptimized(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            System.out.println("입력된 행렬이 비어있습니다.");
            return;
        }

        int rlen = m.length;
        int clen = m[0].length;
        int[] dp = new int[clen]; // 1D DP 배열
        int max = 0, maxr = 0, maxc = 0;
        int prev = 0; // 왼쪽 위 대각선 값을 저장할 변수 (s[r-1][c-1])

        for (int r = 0; r < rlen; r++) {
            for (int c = 0; c < clen; c++) {
                int temp = dp[c]; // 현재 값을 임시 저장 (다음 루프에서 prev가 됨)
                if (r == 0 || c == 0) {
                    dp[c] = m[r][c];
                } else if (m[r][c] == 1) {
                    // dp[c]는 윗 행 값, dp[c-1]은 현재 행의 왼쪽 값, prev는 대각선 값
                    dp[c] = Math.min(prev, Math.min(dp[c], dp[c - 1])) + 1;
                } else {
                    dp[c] = 0;
                }
                
                if (dp[c] > max) {
                    max = dp[c];
                    // 끝나는 위치를 저장하려면 행 정보도 필요
                    maxr = r;
                    maxc = c;
                }
                prev = temp; // 대각선 값 업데이트
            }
        }

        // 결과 출력
        System.out.println(String.format("가장 큰 정사각형의 크기: %d x %d", max, max));
        System.out.println(String.format("끝나는 위치: (row=%d, col=%d)", maxr, maxc));
        System.out.println();

        System.out.println("DP 테이블 (S):");
        System.out.println(dp);

        System.out.println("가장 큰 정사각형:");
        // 5. 결과 정사각형 출력
        for (int r = maxr - max + 1; r <= maxr; r++) {
            for (int c = maxc - max + 1; c <= maxc; c++) {
                System.out.print(m[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------\n");
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
        printMaxSubSquareTestB(m1);

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
        printMaxSubSquareTestB(m2);
    }
}
