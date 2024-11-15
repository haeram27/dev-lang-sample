#include <cstring>

#include <chrono>
#include <iostream>
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

class FtemplateTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    FtemplateTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~FtemplateTest() {
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

// function template
template <typename T>
T const add(T const& a, T const& b) {
    return a+b;
}

// function template specialization
template <>
char* const add<char*>(char* const& a, char* const& b) {
    return ::strcat(a, b);
}


// function overloading
char* const add(char* const& a, char* const& b) {
    return ::strcat(a, b);
}


TEST_F(FtemplateTest, test1)
{TESTBEGIN

    int i = 5;
    int j = 10;

    double a = 5.1;
    double b = 10.2;

    char m[20] = "Hello";
    char n[20] = "World";

    std::cout << "typename T) " << i << " + " << j << " = " << add(i, j) << std::endl;
    std::cout << "typename T) " << a << " + " << b << " = " << add(a, b) << std::endl;

    // invoke function template specialization
    std::cout << "specialization) add<char*>(m, n)" << " = " << add<char*>(m, n) << std::endl;

    // invoke function overloading
    std::cout << "overloading) add(m, n)" << " = " << add(m, n) << std::endl;

TESTEND}

} ///namespace
