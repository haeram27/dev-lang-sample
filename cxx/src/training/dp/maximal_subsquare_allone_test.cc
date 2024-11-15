#include <bits/stdc++.h>

#include "gtest/gtest.h"

using namespace std;
using matrix_t = vector<vector<int>>;

namespace {

/***** DO NOT REMOVE *****/
#define STARTTIME ElapsedTime et;
#define ENDTIME et.elapsed();
using namespace std::chrono;
class ElapsedTime {
public:
    ElapsedTime() { steadyclk_start = steady_clock::now(); }
    void elapsed() {
        steadyclk_end = steady_clock::now();
        std::cout << "[**********] Elapsed time: "
            << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
            << " us.\n";
    }

private:
    steady_clock::time_point steadyclk_start;
    steady_clock::time_point steadyclk_end;
};


/***** GLOBAL UNIT CONTEXT UNDER HERE *****/

class DPMaximalSubSquareAllOneTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    DPMaximalSubSquareAllOneTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~DPMaximalSubSquareAllOneTest() {
        // You can do clean-up work that doesn't throw exceptions here.
    }

    virtual void SetUp() override {
        // Code here will be called immediately after each test
        // (right before each test).
    }

    virtual void TearDown() override {
        // Code here will be called immediately after each test
        // (right before the destructor).
        // No exception handling allowed here.
    }
};


/***** TEST UNIT CONTEXT UNDER HERE *****/

void printMaxSubSquareD(matrix_t M) {
    // TODO under here

    /* Set first column of S[][] */


    /* Set first row of S[][] */


    /* Construct other entries of S[][] */


    /* Find the maximum entry, and indexes of maximum entry in S[][] */
    // 3 4 3
    // cout << max << " " << maxr << " " << maxc << " " << endl;

    /* Print result */

}

TEST_F(DPMaximalSubSquareAllOneTest, rund)
{STARTTIME

    matrix_t M = {{0, 1, 1, 0, 1},
                  {1, 1, 0, 1, 0},
                  {0, 1, 1, 1, 0},
                  {1, 1, 1, 1, 0},
                  {1, 1, 1, 1, 1},
                  {0, 0, 0, 0, 0}};

    printMaxSubSquareD(M);

ENDTIME}


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
void printMaxSubSquareA(matrix_t M) {
    uint rlen = M.size();
    uint clen = M[0].size();
    matrix_t S(rlen, vector<int>(clen));


    int r, c;
    int max, maxr, maxc;

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
                S[r][c] = min({S[r][c - 1], S[r - 1][c], S[r - 1][c - 1]}) + 1;

                if (S[r][c] > max) {
                    max = S[r][c];
                    maxr = r;
                    maxc = c;
                }
            }
        }
    }

    /* Print maximum entry in S[][] */
    cout << max << " " << maxr << " " << maxc << " " << endl;
    cout << endl;

    /* Print result */
    for (r = 0; r < rlen; r++) {
        for (c = 0; c < clen; c++) {
            cout << S[r][c] << " ";
        }
        cout << endl;
    }
    cout << endl;

    /* Print maximal sub-matrix in M of all 1 */
    for(int r = maxr; r > maxr - max; r--) {
        for(int c = maxc; c > maxc - max; c--) {
            cout << M[r][c] << " ";
        }
        cout << endl;
    }
    cout << endl;
}


/* Driver code */
TEST_F(DPMaximalSubSquareAllOneTest, runa)
{STARTTIME

    matrix_t M = {{0, 1, 1, 0, 1},
                  {1, 1, 0, 1, 0},
                  {0, 1, 1, 1, 0},
                  {1, 1, 1, 1, 0},
                  {1, 1, 1, 1, 1},
                  {0, 0, 0, 0, 0}};

    printMaxSubSquareA(M);

ENDTIME}

} ///namespace

