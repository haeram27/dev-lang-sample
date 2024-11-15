#include <cstring>
#include <chrono>
#include <iostream>
#include <sstream>
#include <utility>

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
class ForwardTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    ForwardTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }
    virtual ~ForwardTest() {
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

template <typename T>
void wrapper(T u) {
    overloaded(u);
}

template <typename T>
void lvwrapper(T& u) {
    overloaded(u);
}

// resolver of compile error: cannot bind non-const lvalue reference of type ‘{anonymous}::A&’ to an rvalue of type ‘{anonymous}::A’
// ** r-value will be binded to const l-value reference
template <typename T>
void lvwrapper(const T& u) {
    overloaded(u);
}

template <typename T>
void rvwrapper(T&& u) {
    overloaded(u);
}

// universal-reference!!!
// "<type>&&" of template type is universal-reference
// universal-reference can bind both of l-value and r-value reference
// if you want to pass universal-reference to another function 
// as its own original-type(r or l) then SHOULD use std::forward()
template <typename T>
void rvfwdwrapper(T&& u) {
    overloaded(std::forward<T>(u));
}


class A {};

void overloaded(A& a) { std::cout << "l-value reference is passed" << std::endl; }
void overloaded(const A& a) { std::cout << "const l-value reference is passed" << std::endl; }
void overloaded(A&& a) { std::cout << "r-value reference is passed" << std::endl; }


TEST_F(ForwardTest, basic)
{TESTBEGIN

    A a;
    const A ca;

    std::cout << std::endl << "<< vanila >>" << std::endl;
    overloaded(a);      // l-value in overloaded()
    overloaded(ca);     // const l-value in overloaded()
    overloaded(A{});    // r-value in overloaded()

    std::cout << std::endl << "<< wrapper >>" << std::endl;
    wrapper(a);      // l-value in overloaded()
    wrapper(ca);     // l-value in overloaded()
    wrapper(A{});    // l-value in overloaded()

    std::cout << std::endl << "<< l-value wrapper >>" << std::endl;
    lvwrapper(a);      // l-value in overloaded()
    lvwrapper(ca);     // const l-value in overloaded()
    lvwrapper(A{});    // const l-value in overloaded() // compile error: cannot bind non-const lvalue reference of type ‘{anonymous}::A&’ to an rvalue of type ‘{anonymous}::A’

    std::cout << std::endl << "<< r-value wrapper >>" << std::endl;
    rvwrapper(a);      // l-value in overloaded()
    rvwrapper(ca);     // const l-value in overloaded()
    rvwrapper(A{});    // l-value in overloaded()

    std::cout << std::endl << "<< r-value forward wrapper >>" << std::endl;
    rvfwdwrapper(a);      // l-value in overloaded()
    rvfwdwrapper(ca);     // const l-value in overloaded()
    rvfwdwrapper(A{});    // r-value in overloaded()

TESTEND}


} ///namespace
