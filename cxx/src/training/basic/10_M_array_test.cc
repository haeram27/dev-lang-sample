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

class DTArrayTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    DTArrayTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~DTArrayTest() {
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
 * # median between two integer
 * mid = (lo+hi)/2
 *
 * # median for array index
 *
 * ## median of entire index
 * mid = array.length/2
 * for(i=0; i<mid; i++)
 *
 * ## median of index range
 * mid = (lo+hi+1)/2
 * for(i=lo; i<mid; i++)
 */

/**
 * reverse one dimensional ar
 *    1  2  3   >>>   3  2  1
 *
 * array's last_index is array.length - 1
 * [index]'s antipode is [len-1-index] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
void reverseArrayD(vector<int>& ar) {
}

void rotateArrayD() {
    auto printv = [](const auto& v) {
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

    // rotate(shift) to left
    vector<int> rotateLeft{1, 2, 3, 4, 5};
    // TODO
    printv(rotateLeft);  // 3 4 5 1 2

    // rotate(shift) to right :: use reverse iterator
    vector<int> rotateRight{1, 2, 3, 4, 5};
    // TODO
    printv(rotateRight);  // 4 5 1 2 3

    // rotate(shift) one round :: use vector.size()
    vector<int> rotateOneRoundLeft{1, 2, 3, 4, 5};
    // TODO
    printv(rotateOneRoundLeft);  // 1 2 3 4 5

    vector<int> rotateOneRoundRight{1, 2, 3, 4, 5};
    // TODO
    printv(rotateOneRoundRight);  // 1 2 3 4 5
}

TEST_F(DTArrayTest, run) {
    STARTTIME

    auto printv = [](const auto& v) {
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

    auto print2dv = [](const auto& v) {
        for (const auto& row : v) {
            for (const auto& e : row) {
                cout << e << " ";
            }
            cout << endl;
        }
        cout << endl;
    };

    cout << "\n************************************" << endl;
    vector<int> a1 = {10, 20, 30, 40, 50};
    cout << "original array: =================" << endl;
    printv(a1);
    cout << "\nreversed array: =================" << endl;
    reverseArrayD(a1);
    printv(a1);

    cout << "\nrotate array: =================" << endl;
    rotateArrayD();

    ENDTIME
}

/***** SOLUTION UNDER HERE *****/

/**
 * reverse one dimensional ar
 *    1  2  3   >>>   3  2  1
 *
 * array's last_index is array.length - 1
 * [index]'s antipode is [len-1-index] == [last_index-index]
 * (index<len/2) is efficient half point of array in loop
 */
void reverseArray(vector<int>& ar) {
    int len = ar.size();

    // just swap item index with item len-1-index until index<len/2
    for (int i = 0; i < len / 2; i++) {
        swap(ar[i], ar[len - 1 - i]);
    }
}

void rotateArray() {
    auto printv = [](const auto& v) {
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

    // rotate(shift) to left
    vector<int> rotateLeft{1, 2, 3, 4, 5};
    rotate(rotateLeft.begin(), rotateLeft.begin() + 2, rotateLeft.end());
    printv(rotateLeft);  // 3 4 5 1 2

    // rotate(shift) to right :: use reverse iterator
    vector<int> rotateRight{1, 2, 3, 4, 5};
    rotate(rotateRight.rbegin(), rotateRight.rbegin() + 2, rotateRight.rend());
    printv(rotateRight);  // 4 5 1 2 3

    // rotate(shift) one round :: use vector.size()
    vector<int> rotateOneRoundLeft{1, 2, 3, 4, 5};
    rotate(rotateOneRoundLeft.begin(), rotateOneRoundLeft.begin() + rotateOneRoundLeft.size(), rotateOneRoundLeft.end());
    printv(rotateOneRoundLeft);  // 1 2 3 4 5

    vector<int> rotateOneRoundRight{1, 2, 3, 4, 5};
    rotate(rotateOneRoundRight.rbegin(), rotateOneRoundRight.rbegin() + rotateOneRoundRight.size(), rotateOneRoundRight.rend());
    printv(rotateOneRoundRight);  // 1 2 3 4 5
}

TEST_F(DTArrayTest, runa) {
    STARTTIME

    auto printv = [](const auto& v) {
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

    auto print2dv = [](const auto& v) {
        for (const auto& row : v) {
            for (const auto& e : row) {
                cout << e << " ";
            }
            cout << endl;
        }
        cout << endl;
    };

    cout << "\n************************************" << endl;
    vector<int> a1 = {10, 20, 30, 40, 50};
    cout << "original array: =================" << endl;
    printv(a1);
    cout << "\nreversed array: =================" << endl;
    reverseArray(a1);
    printv(a1);

    rotateArray();

    ENDTIME
}

}  // namespace
