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

class TRCombinationTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TRCombinationTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TRCombinationTest() {
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

using namespace std;

/***** TEST UNIT CONTEXT UNDER HERE *****/

/**
 * TODO: implement combination() Under Here
 *
 * @brief combination
 *
 * @tparam T
 * @param data      element list (size:n)
 * @param out       result of combination (size:r)
 * @param start     start index in data to be candidate
 * @param depth     current level of combination
 */
// template <typename T> void combination(vector<T>& data, vector<T>& out, uint start, uint depth) {}

TEST_F(TRCombinationTest, run)
{STARTTIME

    int r = 2;
    vector<char> data = {'a', 'b', 'c', 'd'};  // n = 4
    vector<char> out(r);

    std::cout << "\n[combination#2]===================" << std::endl;
    /* !!!! combination caller !!!! */
    // combination(data, out, 0, 0);

    /* expected result ===============
        a b
        a c
        a d
        b c
        b d
        c d
    */

ENDTIME}

/* ANSWER:
template <typename T>
void combination(vector<T>& data, vector<T>& out, uint start, uint depth) {
    if (depth == out.size()) {    // out.size() is r
        for (const T& e : out)
            cout << e << " ";
        cout << endl;
        return;
    }

    for (uint i = start; i < data.size(); i++) {
        out[depth] = data[i];
        combination(data, out, i + 1, depth + 1);
    }
}
*/

TEST_F(TRCombinationTest, nextperm) 
{STARTTIME

    const int r = 2;
    vector<char> data{'a', 'b', 'c', 'd'};

    // TODO: Under Here

ENDTIME}

/* ANSWER:
TEST_F(TRCombinationTest, nextperm)
{STARTTIME

    const int r = 2;

    vector<char> data{'a', 'b', 'c', 'd'};
    vector<bool> temp(data.size(), true);
    for (uint i = 0; i < r; i ++)
        temp[i] = false;

    do {
        for (uint i = 0; i < data.size(); ++i) {
            if (!temp[i])
                cout << data[i] << " ";
        }
        cout << endl;
    } while (next_permutation(temp.begin(), temp.end()));

    // expected result ===============
    // a b
    // a c
    // a d
    // b c
    // b d
    // c d

ENDTIME}
*/

}  // namespace
