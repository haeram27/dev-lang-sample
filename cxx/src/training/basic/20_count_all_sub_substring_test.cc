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

class DPAllSubstringTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    DPAllSubstringTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~DPAllSubstringTest() {
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

TEST_F(DPAllSubstringTest, run)
{STARTTIME

    string s {"abcde"};


ENDTIME}


/* Solution: "abcde"
a
ab
abc
abcd
abcde
b
bc
bcd
bcde
c
cd
cde
d
de
e
*/
TEST_F(DPAllSubstringTest, runa)
{STARTTIME

    string s {"abcde"};

    // s.size() == number of characters of string s == last index of string s +1
    // s.size()-1 == last index of string s 
    // (!) s.size()-i == number of characters (length) from i index to last index of full string 
    for (int i=0; i<s.size(); i++) {
        for(int j=1; j<=s.size()-i; j++){
            cout << s.substr(i, j) << endl;
        }
    }

ENDTIME}

} ///namespace

