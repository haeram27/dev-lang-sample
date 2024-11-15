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

class DPSquareTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    DPSquareTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~DPSquareTest() {
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

/**
 * Answer:
 * find all Square from given array(NxM array)
 * Square is NxN shape of Rectangle
 * start coordinate is most left bottom point
 * end coordinate is most right top point
 *
 * rowLen == number of vertical edges of given square  == last index of row given square
 * colLen == number of horizontal edges of given square == last index of col given square
 *
 * Examples:
 * NxN square's all number of subsquare is N!
 * allSubSquareA(2, 2);  // 5 = 4(1by1) + 1(2by2)
 * allSubSquareA(3, 3);  // 14 = 9(1by1) + 4(2by2) + 1(3by3)
 * allSubSquareA(4, 4);  // 30 = 16(1by1) + 9(2by2) + 4(3by3) + 1(4by4)
 * allSubSquareA(5, 5);  // 55 = 25(1by1) + 16(2by2) + 9(3by3) + 4(4by4) + 1(5by5)
 * allSubSquareA(N, N);  // N^2 + (N-1)^2 + ... + 1^2
 * 
 * key point:
 * NxN square's all number of subsquare is N!
 * rowLen == number of vertical edges of given square  == last index of row given square
 * colLen == number of horizontal edges of given square == last index of col given square
 * off == count of horizontal or vertical edge of square
 * row == start index of vertical edge of subsquare
 * row + off == == end index of vertical edge of subsquare
 * col == start index of horizontal edge of subsquare
 * col + off == == end index of horizontal edge of subsquare
 */
void allSubSquareT(int rowLen, int colLen) {
    int count = 0;
    // TODO under here


    cout << count << endl;
}

TEST_F(DPSquareTest, run)
{STARTTIME

    allSubSquareT(2, 2);  // 5 = 4(1by1) + 1(2by2)
    allSubSquareT(3, 3);  // 14 = 9(1by1) + 4(2by2) + 1(3by3)
    allSubSquareT(4, 4);  // 30 = 16(1by1) + 9(2by2) + 4(3by3) + 1(4by4)
    allSubSquareT(5, 5);  // 55 = 25(1by1) + 16(2by2) + 9(3by3) + 4(4by4) + 1(5by5)

ENDTIME}


/**
 * Solution:
 * find all Square from given array(NxM array)
 * Square is NxN shape of Rectangle
 * start coordinate is most left bottom point
 * end coordinate is most right top point
 *
 * rowLen == number of vertical edges of given square  == last index of row given square
 * colLen == number of horizontal edges of given square == last index of col given square
 *
 * Examples:
 * NxN square's all number of subsquare is N!
 * allSubSquareA(2, 2);  // 5 = 4(1by1) + 1(2by2)
 * allSubSquareA(3, 3);  // 14 = 9(1by1) + 4(2by2) + 1(3by3)
 * allSubSquareA(4, 4);  // 30 = 16(1by1) + 9(2by2) + 4(3by3) + 1(4by4)
 * allSubSquareA(5, 5);  // 55 = 25(1by1) + 16(2by2) + 9(3by3) + 4(4by4) + 1(5by5)
 * allSubSquareA(N, N);  // N^2 + (N-1)^2 + ... + 1^2
 *
 * key point:
 * NxN square's all number of subsquare is N!
 * rowLen == number of vertical edges of given square  == last index of row given square
 * colLen == number of horizontal edges of given square == last index of col given square
 * off == count of horizontal or vertical edge of square
 * row == start index of vertical edge of subsquare
 * row + off == == end index of vertical edge of subsquare
 * col == start index of horizontal edge of subsquare
 * col + off == == end index of horizontal edge of subsquare
 */
void allSubSquareA(int rowLen, int colLen) {
    int count = 0;
    /*
     *  off == number of edges of each subsquare
     *  r+off == last index of each subsquare
     */
    for (int off=1; off<=min(rowLen, colLen); off++) {
        for (int r=0; r+off<=rowLen; r++) {
            for (int c=0; c+off<=colLen; c++) {
                count++;
            }
        }
    }
    cout << count << endl;
}

TEST_F(DPSquareTest, runa)
{STARTTIME

    allSubSquareA(2, 2);  // 5 = 4(1by1) + 1(2by2)
    allSubSquareA(3, 3);  // 14 = 9(1by1) + 4(2by2) + 1(3by3)
    allSubSquareA(4, 4);  // 30 = 16(1by1) + 9(2by2) + 4(3by3) + 1(4by4)
    allSubSquareA(5, 5);  // 55 = 25(1by1) + 16(2by2) + 9(3by3) + 4(4by4) + 1(5by5)

ENDTIME}

} ///namespace

