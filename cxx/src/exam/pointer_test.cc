#include "exam/pointer_test.h"

#include <chrono>
#include <iostream>

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

class Pointertest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Pointertest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Pointertest() {
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

void pointerexam() {

    char* sp1;
    char** sp2;
    char* sp3[2];
    char sp4[2][2];

    int ip1;
    int* ip2;
    int ip3[2];
    int* ip4[2];

    sprint(&sp1);    // OK
    sprint(sp2);    // OK
    sprint(sp3);    // OK
//    sprint(sp4);    // Compile Error:: char (*)[2]   incompatible char**

    iprint(&ip1);    // OK
//    iprint(ip2);    // Runtime Error
    iprint(ip3);    
//    iprint(ip4);    // Compiel Error:: cannot convert ‘int**’ to ‘int*’

}

void sprint(OUT char** p) {
    *p = "thist is string pointer";
    std::clog << p << std::endl;
    std::clog << *p << std::endl << std::endl;
}

void iprint(OUT int* p) {
    *p = 7;
    std::clog << p << std::endl;
    std::clog << *p << std::endl << std::endl;
}


/***** TEST UNIT CONTEXT UNDER HERE *****/

TEST_F(Pointertest, pointertest)
{STARTTIME

    pointerexam();

ENDTIME}

} ///namespace
