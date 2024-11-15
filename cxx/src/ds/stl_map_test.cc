#include <chrono>
#include <iostream>
#include <map>
#include <sstream>

#include "gtest/gtest.h"

namespace {

/***** DO NOT REMOVE *****/
#define TESTBEGIN ElapsedTime et;
#define TESTEND et.elapsed();
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

class StlMapTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StlMapTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StlMapTest() {
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

TEST_F(StlMapTest, map)
{TESTBEGIN

    std::map<int, std::string> m;

    m.insert(std::make_pair(50, "fifty"));
    m.insert(std::make_pair(20, "twenty"));
    m.insert(std::make_pair(30, "thirty"));
    m.insert(std::make_pair(40, "forty"));
    m.insert(std::make_pair(10, "ten"));

    for (auto i = m.begin(); i != m.end(); ++i) {
        std::cout <<  "[" << i->first << " " << i->second << "]" << std::endl;
    }
    std::cout << std::endl;

    auto i = m.find(30);
    std::cout << i->second;

TESTEND}

} ///namespace
