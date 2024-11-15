#include <chrono>
#include <iostream>

#include "gtest/gtest.h"
#include "util/pipe.h"


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

class Pipetest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Pipetest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Pipetest() {
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

TEST_F(Pipetest, test)
{STARTTIME

    std::string name {"init"};
    std::cout << R"(ps hocomm 1:)" << std::endl;
    std::cout << util::pipe::pexec(R"(ps hocomm 1)") << std::endl;
    std::cout << (name.compare(util::pipe::pexec(R"(ps hocomm 1)"))) << std::endl;
    std::cout << ((util::pipe::pexec(R"(ps hocomm 1)")).find("init") != std::string::npos) << std::endl;

ENDTIME}

} ///namespace