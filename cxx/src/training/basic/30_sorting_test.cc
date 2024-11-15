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

class SortingTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    SortingTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~SortingTest() {
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


TEST_F(SortingTest, run)
{STARTTIME

    using namespace std;

    auto printv = [](const auto& v){
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

/*
    std::sort() // #include <algorithm>
    std::sort() using RandomAccessIterator only std::array, std::vector and  std::deque have
*/
#if 1  // can change vector as array or deque
    vector<uint> v {1,3,5,7,9,2,4,6,8,10};
#else
    int a[] {1,3,5,7,9,2,4,6,8,10};
    std::vector<int> v {a, a+(sizeof(a)/sizeof(a[0]))};
#endif

    /* sort ascending; make bigger back; e1 < e2; */
    // TODO under here
    sort(v.begin(), v.end());
    printv(v); // 1 2 3 4 5 6 7 8 9 10
    sort(v.begin(), v.end()); // use std::less
    printv(v); // 1 2 3 4 5 6 7 8 9 10


    /* sort desceding; make bigger front; e1 > e2; */
    // TODO under here
    sort(v.begin(), v.end()); // use std::greater
    printv(v); // 10 9 8 7 6 5 4 3 2 1
    sort(v.begin(), v.end()); // use compare lambda
    printv(v); // 10 9 8 7 6 5 4 3 2 1


ENDTIME}

TEST_F(SortingTest, vector)
{STARTTIME

    using namespace std;

    auto printv = [](const auto& v){
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

/*
    std::sort() // #include <algorithm>
    std::sort() using RandomAccessIterator only std::array, std::vector and  std::deque have

    Comparation functor std::greater() and std::less() for third arguement of std::sort() 
    only can be used when element type of container is comparable.
    Comparable type means numbers, char(ascii code is numbers actually)
    and objects impelement comparation operators(>, <).
*/
#if 1  // can change vector as array or deque
    vector<uint> v {1,3,5,7,9,2,4,6,8,10};
#else
    int a[] {1,3,5,7,9,2,4,6,8,10};
    std::vector<int> v {a, a+(sizeof(a)/sizeof(a[0]))};
#endif

    /* sort ascending; make bigger back; e1 < e2; */
    sort(v.begin(), v.end());  // defalut dompare func is std::less<>()
    printv(v); // 1 2 3 4 5 6 7 8 9 10

    sort(v.begin(), v.end(), less<uint>());  // #include <functional>
    printv(v); // 1 2 3 4 5 6 7 8 9 10


    /* sort desceding; make bigger front; e1 > e2; */
    sort(v.begin(), v.end(), greater<uint>());  // #include <functional>
    printv(v); // 10 9 8 7 6 5 4 3 2 1

    sort(v.begin(), v.end(), [](const auto& e1, const auto& e2){return e1 > e2;});
    printv(v); // 10 9 8 7 6 5 4 3 2 1

ENDTIME}


TEST_F(SortingTest, list)
{STARTTIME

    using namespace std;

    auto printv = [](const auto& v){
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    };

    list<uint> l {1,3,5,7,9,2,4,6,8,10};

    /* sort ascending; make bigger back; e1 < e2; */
    l.sort();  // defalut dompare func is std::less<>()
    printv(l); // 1 2 3 4 5 6 7 8 9 10

    l.sort(less<uint>());  // #include <functional>
    printv(l); // 1 2 3 4 5 6 7 8 9 10


    /* sort desceding; make bigger front; e1 > e2; */
    l.sort(greater<uint>());  // #include <functional>
    printv(l); // 10 9 8 7 6 5 4 3 2 1

    l.sort([](const auto& e1, const auto& e2){return e1 > e2;});
    printv(l); // 10 9 8 7 6 5 4 3 2 1

ENDTIME}

} ///namespace
