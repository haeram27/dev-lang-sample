#include <chrono>
#include <iostream>
#include <cstring>

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
class Cstringtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Cstringtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Cstringtest() {
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


void cstring_exam () {
	std::cout << "[cstring_exam]" << std::endl;

	int len = 5;
	char d[len] = "0987654321";  // causes undefined behavior. writing past the bounds of the array.
                                 // cxx11 compiler doesn't alert this as an error.
	char s[len] = "123";

	std::cout << "sizeof d: " << sizeof(d) << std::endl;
	memset(d, 0x00, sizeof(d));  //sizeof = number of byte in data type and array
	strncpy(d, s, sizeof(d));

	std::cout << "d: " << d << std::endl;
	std::cout << "s: " << s << std::endl;
}


TEST_F(Cstringtest, cstringtest)
{STARTTIME

    cstring_exam();

ENDTIME}

} ///namespace
