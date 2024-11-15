#include <chrono>
#include <iostream>
#include <sstream>

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

class StaticTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StaticTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StaticTest() {
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
/*
* STATIC VARIABLE IN GLOBAL SCOPE
* lifetime: end of this program
* access scope: in this file
*/
static int gst;

int add(int v) {
    /*
     * STATIC VARIABLE IN FUNCTION
     * lifetime: end of this program
     * access scope: in this function 
     */

    static int fst;
    fst += v;
    return fst;
}

class TestClass {
public:
    int add(int v);

private:
    /*
     * STATIC VARIABLE as CLASS MEMBER
     * lifetime: end of this program
     * access scope: through all instance of this class 
     */
    static int cst;
};

/* 
 * class member static variable SHOULD be initialized
 * or it'll be undefined reference
 */
int TestClass::cst = 0;
int TestClass::add(int v) {
    cst += v;
    return cst;
}

TEST_F(StaticTest, test1)
{STARTTIME

    for (auto id = 0; id < 10; ++id) {
        std::cout << "[" << id << "]" << "global_static:" << ++gst << std::endl;
    }

    for (auto id = 0; id < 10; ++id) {
        std::cout << "[" << id << "]" << "function_static:" << add(1) << std::endl;
    }

    TestClass tc1;
    for (auto id = 0; id < 10; ++id) {
        std::cout << "[" << id << "]" << "class_member_static:tc1:" << tc1.add(1) << std::endl;
    }

    TestClass tc2;
    for (auto id = 0; id < 10; ++id) {
        std::cout << "[" << id << "]" << "class_member_static:tc2:" << tc2.add(1) << std::endl;
    }

ENDTIME}

} ///namespace
