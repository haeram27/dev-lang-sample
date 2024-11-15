#include <algorithm>
#include <chrono>
#include <iostream>
#include <sstream>
#include <string>
#include <type_traits>
#include <unordered_map>

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

class StlUmmapTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StlUmmapTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StlUmmapTest() {
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

//#define pairtype pair<std::string, int>
TEST_F(StlUmmapTest, sortbyval)
{TESTBEGIN

    using pairtype = std::pair<std::string, int>;
    // Create an unordered_map of three strings (that map to strings)
    std::unordered_multimap<std::string, int> map = {
        {"a", 5},
        {"b", 4},
        {"c", 3},
        {"d", 2},
        {"e", 1},
        {"f", 3},
    };

    // Iterate and print keys and values of unordered_map
    for(const auto& n : map) {
        std::cout << "Key:[" << n.first << "] Value:[" << n.second << "]\n";
    }

    std::vector<pairtype> vec {map.begin(), map.end()};
    std::sort(vec.begin(), vec.end(),
        [](
        const std::pair<std::string, int>& e1,
        const std::pair<std::string, int>& e2)
        {return e1.second < e2.second;});

    for (const pairtype& e : vec) {std::cout << e.first << ":" << e.second << std::endl;}
TESTEND}


} ///namespace
