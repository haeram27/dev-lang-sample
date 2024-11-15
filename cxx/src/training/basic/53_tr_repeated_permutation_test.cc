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

class TRRepeatedPermutationTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TRRepeatedPermutationTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TRRepeatedPermutationTest() {
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
 * TODO: implement permutation() Under Here
 *
 * @brief permutation
 *
 * @tparam T
 * @param data      element list (size:n)
 * @param out      result of permutation (size:r)
 * @param depth     current level of permutation
 */
// template <typename T> void rpermutation(vector<T>& data, vector<T>& out, uint depth) {}

TEST_F(TRRepeatedPermutationTest, run) 
{STARTTIME

    int r = 2;                         // number(r) of elements to select
    vector<char> data{'a', 'b', 'c'};  // element list (size:n)
    vector<char> out(r);              // result of permutation (size:r)

    std::cout << "\n[permutation#1]===================" << std::endl;
    /* !!!! repeated permutation caller !!!! */
    // rpermutation(data, out, 0);  // 3P2

    /* expected result ===============
        a a
        a b
        a c
        b a
        b b
        b c
        c a
        c b
        c c
    */

ENDTIME}

/* ANSWER:
template <typename T>
void rpermutation(vector<T>& data, vector<T>& out, uint depth)
{
    if (depth == out.size()) {       // out.size() is r
        for (const T& e : out) {
            cout << e << " ";
        }
        cout << endl;
        return;
    }

    for (uint i = 0; i < data.size(); i++) {
        out[depth] = data[i];
        rpermutation(data, out, depth + 1);
    }
}
*/

}  // namespace
