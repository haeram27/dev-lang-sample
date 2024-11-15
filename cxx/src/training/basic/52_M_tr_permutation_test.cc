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

class TRPermutationTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TRPermutationTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TRPermutationTest() {
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
 * @param out       result of permutation (size:r)
 * @param visited   check selected or not
 * @param depth     current level of permutation
 */
// template <typename T> void permutation(vector<T>& data, vector<T>& out, vector<bool>& visited, uint depth) {}

TEST_F(TRPermutationTest, run)
{STARTTIME

    int r = 2;                          // number(r) of elements to select
    vector<char> data{'a', 'b', 'c'};   // element list (size:n)
    vector<char> out(r);                // result of permutation (size:r)
    vector<bool> visited(data.size());  // check selected or not

    std::cout << "\n[permutation#1]===================" << std::endl;
    /* !!!! permutation caller !!!! */
    // permutation(data, out, visited, 0);  // 3P2

    /* expected result ===============
       a b
       a c
       b a
       b c
       c a
       c b
    */

ENDTIME}

/* ANSWER:
template <typename T>
void permutation(vector<T>& data, vector<T>& out, vector<bool>& visited, uint depth)
{
    if (depth == out.size()) {       // out.size() is r
        for (const T& e : out) {
            cout << e << " ";
        }
        cout << endl;
        return;
    }

    for (uint i = 0; i < data.size(); i++) {
        if (!visited[i]) {
            visited[i] = true;
            out[depth] = data[i];
            permutation(data, out, visited, depth + 1);
            visited[i] = false;
        }
    }
}
*/

TEST_F(TRPermutationTest, nextperm) 
{STARTTIME

    vector<int> v = {4, 3, 2, 1};  // SHOULD NOT have duplicated element
    int r = 2;

    // TODO: Under Here

ENDTIME}


/* ANSWER:
TEST_F(TRPermutationTest, nextperm)
{STARTTIME

    vector<int> v = {4, 3, 2, 1}; // SHOULD NOT have duplicated element
    int r = 2;

    // next_permutation must require asceding order
    std::sort(v.begin(), v.end()); // sort ascending - use less() comparator

    do {
        for (int i=0; i<r; i++) {
            cout << v[i] << " ";
        }
        cout << endl;
        reverse(v.begin() + r, v.end());
    } while (next_permutation(v.begin(), v.end()));

ENDTIME}
*/

}  // namespace
