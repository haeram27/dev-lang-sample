#include <chrono>
#include <iostream>
#include <vector>
#include <sstream>
#include <string>

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

class Callbyref: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Callbyref() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Callbyref() {
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



class Foo {
public:
    Foo() {std::cout << "D constructor: " << (void*) this << std::endl; }
    ~Foo() { std::cout << "~Destructor: " << (void*) this << std::endl; }
    Foo(const Foo& f) { std::cout << "C constructor: " << (void*) this << std::endl; }
    Foo& operator=(const Foo& f) { std::cout << "C assign operator: " << (void*) this << std::endl; }
    Foo(const Foo&& f) { std::cout << "M constructor: " << (void*) this << std::endl; }
    Foo& operator=(const Foo&& f) { std::cout << "M assign operator: " << (void*) this << std::endl; }

    void print() {std::cout << "print(): " << (void*) this << std::endl;}
};


void callByValue(Foo v) {
    std::cout << "parameter v: " << (void*) &v << std::endl << std::endl;
}

void callByReference(Foo& r) {
    std::cout << "parameter r: " << (void*) &r << std::endl << std::endl;
}

void callByPointer(Foo* p) {
    std::cout << "parameter r: " << (void*) p << std::endl << std::endl;
}


/***** TEST UNIT CONTEXT UNDER HERE *****/

TEST_F(Callbyref, callbyref)
{STARTTIME
    std::cout << "=== creat instance Foo f ====" << std::endl;
    Foo f{};
    std::cout << std::endl << std::endl;


    std::cout << "==== callByValue(Foo v) called ====" << std::endl;
    callByValue(f);
    std::cout << std::endl << std::endl;


    std::cout << "==== callByReference(Foo& r) called ====" << std::endl;
    callByReference(f);
    std::cout << std::endl << std::endl;


    std::cout << "==== callByPointer(Foo* p) called ====" << std::endl;
    callByPointer(&f);
    std::cout << std::endl << std::endl;

ENDTIME}
/*  Result ================================
CallByValue는 parameter instance를 생성하고
CallByReference는 parameter instance를 생성하지 않는다.

=== creat instance Foo f ====
D constructor: 0x7fffe9ea0c56


==== callByValue(Foo v) called ====
C constructor: 0x7fffe9ea0c57
parameter v: 0x7fffe9ea0c57

~Destructor: 0x7fffe9ea0c57


==== callByReference(Foo& r) called ====
parameter r: 0x7fffe9ea0c56


==== callByPointer(Foo* p) called ====
parameter r: 0x7fffe9ea0c56


~Destructor: 0x7fffe9ea0c56
*/


} ///namespace


