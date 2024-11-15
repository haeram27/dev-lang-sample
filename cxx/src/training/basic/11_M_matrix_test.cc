#include <bits/stdc++.h>

#include "gtest/gtest.h"


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

class MatrixTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    MatrixTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~MatrixTest() {
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

using namespace std;
using matrix_t = vector<vector<int>>;

/**
 * flip row of matrix(two-dimensional array)
 * exchange top and bottom rows
 *         cols
 *        1  2  3         7  8  9
 *  rows  4  5  6   >>>   4  5  6
 *        7  8  9         1  2  3
 * 
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
matrix_t flipRowsD(matrix_t matrix) {
    //return matrix;
}

/**
 * flip cols of NxM matrix(two-dimensional array)
 * exchange left and right columns
 *         cols
 *        1  2  3         3  2  1
 *  rows  4  5  6   >>>   6  5  4
 *        7  8  9         9  8  7
 * 
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
matrix_t flipColsD(matrix_t matrix) {
    //return matrix;
}

/**
 * clockwise rotate of NxN square(two-dimensional array)
 * rotate NxN array as clockwise direction
 *         cols
 *        1  2  3         7  4  1
 *  rows  4  5  6   >>>   8  5  2
 *        7  8  9         9  6  3
 * 
 * !!! clockwise rotation is CR=(clockwise, row antipode)
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 */
matrix_t clockwiseRotateD(matrix_t square) {
    //return rotated;
}

/**
 * reverse-clockwise rotate of NxN square(two-dimensional array)
 * rotate NxN array as revserse-clockwise direction
 *         cols
 *        1  2  3         3  6  9
 *  rows  4  5  6   >>>   2  5  8
 *        7  8  9         1  4  7
 * 
 * !!! reverse clockwise rotation is RC=(reverse-clockwise, col antipode)
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 */
matrix_t reverseClockwiseRotateD(matrix_t square) {
    //return rotated;
}

TEST_F(MatrixTest, run)
{STARTTIME

    auto printv = [](const auto& v){
        for (const auto& e:v){
            cout << e << " ";
        }
        cout << endl;
    };

    auto print2dv = [](const auto& v){
        for (const auto& row:v){
            for (const auto& e:row){
              cout << e << " ";
            }
            cout << endl;
        }
        cout << endl;
    };

    cout << "\n************************************" << endl;
    cout << "original matrix: =================" << endl;
    matrix_t a2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    print2dv(a2);
    cout << "flip row matrix: =================" << endl;
    print2dv(flipRowsD(a2));
    cout << "flip column matrix: =================" << endl;
    print2dv(flipColsD(a2));
    cout << "rotate clock-wise matrix: =================" << endl;
    print2dv(clockwiseRotateD(a2));
    cout << "rotate revser-clock-wise matrix: =================" << endl;
    print2dv(reverseClockwiseRotateD(a2));

    ENDTIME
    }

/***** SOLUTION UNDER HERE *****/

/**
 * flip row of matrix(two-dimensional array)
 * exchange top and bottom rows
 *         cols
 *        1  2  3         7  8  9
 *  rows  4  5  6   >>>   4  5  6
 *        7  8  9         1  2  3
 * 
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
matrix_t flipRows(matrix_t matrix) {
    int rlen = matrix.size();
    int clen = matrix[0].size();

    for (int r=0; r<rlen/2; r++) {
        for (int c=0; c<clen; c++) {
            swap(matrix[r][c], matrix[rlen-1-r][c]);
        }
    }

    return matrix;
}

/**
 * flip cols of NxM matrix(two-dimensional array)
 * exchange left and right columns
 *         cols
 *        1  2  3         3  2  1
 *  rows  4  5  6   >>>   6  5  4
 *        7  8  9         9  8  7
 * 
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
matrix_t flipCols(matrix_t matrix) {
    int rlen = matrix.size();
    int clen = matrix[0].size();

    for (int r=0; r<rlen; r++) {
        for (int c=0; c<clen/2; c++) {
            swap(matrix[r][c], matrix[r][clen-1-c]);
        }
    }

    return matrix;
}

/**
 * clockwise rotate of NxN square(two-dimensional array)
 * rotate NxN array as clockwise direction
 *         cols
 *        1  2  3         7  4  1
 *  rows  4  5  6   >>>   8  5  2
 *        7  8  9         9  6  3
 * 
 * how to:
 * clockwise rotation is CR=(clockwise, row antipode)
 * 
 * array's last_index is array.length - 1
 * [index]'s antipode is [len-1-index] == [max_index-index]
 */
matrix_t clockwiseRotate(matrix_t square) {
    int len = square.size();

    matrix_t rotated(len, vector<int>(len));
    for (int r=0; r<len; r++) {
        for (int c=0; c<len; c++) {
            rotated[c][len-1-r] = square[r][c];
        }
    }

    return rotated;
}

/**
 * reverse-clockwise rotate of NxN square(two-dimensional array)
 * rotate NxN array as revserse-clockwise direction
 *         cols
 *        1  2  3         3  6  9
 *  rows  4  5  6   >>>   2  5  8
 *        7  8  9         1  4  7
 * 
 * how to:
 * !!! reverse clockwise rotation is RC=(reverse-clockwise, col antipode)
 * 
 * array's last_index is array.length - 1
 * index i's antipode is [len-1-i] == [last_index-index]
 */
matrix_t reverseClockwiseRotate(matrix_t square) {
    int len = square.size();

    matrix_t rotated(len, vector<int>(len));
    for (int r=0; r<len; r++) {
        for (int c=0; c<len; c++) {
            rotated[len-1-c][r] = square[r][c];
        }
    }

    return rotated;
}

TEST_F(MatrixTest, runa)
{STARTTIME

    auto printv = [](const auto& v){
        for (const auto& e:v){
            cout << e << " ";
        }
        cout << endl;
    };

    auto print2dv = [](const auto& v){
        for (const auto& row:v){
            for (const auto& e:row){
              cout << e << " ";
            }
            cout << endl;
        }
        cout << endl;
    };

    cout << "\n************************************" << endl;
    cout << "original matrix: =================" << endl;
    matrix_t a2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    print2dv(a2);
    cout << "flip row matrix: =================" << endl;
    print2dv(flipRowsD(a2));
    cout << "flip column matrix: =================" << endl;
    print2dv(flipColsD(a2));
    cout << "rotate clock-wise matrix: =================" << endl;
    print2dv(clockwiseRotateD(a2));
    cout << "rotate revser-clock-wise matrix: =================" << endl;
    print2dv(reverseClockwiseRotateD(a2));

ENDTIME}

} ///namespace

