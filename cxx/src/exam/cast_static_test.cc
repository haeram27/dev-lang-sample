#include <chrono>
#include <iostream>
#include <sstream>
#include <typeinfo>

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

class StaticCastTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StaticCastTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StaticCastTest() {
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

TEST_F(StaticCastTest, test1)
{TESTBEGIN

float f = 2.0;
double d = 3.0;

auto r1 = d / f;  // double
std::cout << "d1 type: " << typeid(r1).name() << ", val: " << r1 << std::endl;

float r2 = d / f;  // float
std::cout << "f1 type: " << typeid(r2).name() << ", val: " << r2 << std::endl;

float r3 = static_cast<float>(d / f);  // float
std::cout << "f2 type: " << typeid(r3).name() << ", val: " << r3 << std::endl;

TESTEND}


TEST_F(StaticCastTest, test2)
{TESTBEGIN

float f = 2.0;
int i = 3.0;

auto r1 = i / f;  // float
std::cout << "r1 type: " << typeid(r1).name() << ", val: " << r1 << std::endl;

int r2 = i / f;  // int
std::cout << "i1 type: " << typeid(r2).name() << ", val: " << r2 << std::endl;

int r3 = static_cast<int>(i / f);  // int
std::cout << "f3 type: " << typeid(r3).name() << ", val: " << r3 << std::endl;

float r4 = i / f;  // float
std::cout << "f2 type: " << typeid(r4).name() << ", val: " << r4 << std::endl;

float r5 = static_cast<float>(i / f);  // float
std::cout << "f3 type: " << typeid(r5).name() << ", val: " << r5 << std::endl;

TESTEND}

} ///namespace
