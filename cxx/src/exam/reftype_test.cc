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

class Reftypetest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Reftypetest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Reftypetest() {
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

TEST_F(Reftypetest, test)
{STARTTIME

    int&& rv_rhs = 1;
    int&& rv_lhs = 2;
    int i = rv_rhs;
    int* p = &rv_rhs;
    int& lv_rhs = *p;
    int& lv_lhs = rv_lhs;

    std::clog << ">>>> normal variable <<<<" << std::endl;

    std::clog << ">>>> lvalue reference <<<<" << std::endl;

    std::clog << ">>>> rvalue reference <<<<" << std::endl;

    std::clog << rv_rhs << std::endl;
    std::clog << &rv_rhs << std::endl;
    rv_lhs = rv_rhs;
    std::clog << rv_lhs << std::endl;
    std::clog << &rv_lhs << std::endl;

    std::clog << ">>>> pointer <<<<" << std::endl;





ENDTIME}

} ///namespace
