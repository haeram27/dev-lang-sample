#include <chrono>
#include <iostream>
#include <vector>

#include "gtest/gtest.h"
#include "util/string.h"


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
using namespace util::string;

class ByteBufTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    ByteBufTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~ByteBufTest() {
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

class ByteBuf {
public:
    template<typename T>
    ByteBuf& operator<<(const T& dt) {
        uint32_t size = sizeof(T);
        const uint8_t* ptr = reinterpret_cast<const uint8_t*>(&dt);
        for (size_t i = 0; i < size; ++i)
        {
            vbuf.push_back(*ptr++);
        }
        return *this;
    }

    std::vector<uint8_t>& getbuf() {
        return vbuf;
    }

    uint8_t* data() {
        return vbuf.data();
    }

    uint32_t size() {
        return vbuf.size();
    }

private:
    std::vector<uint8_t> vbuf;
};



TEST_F(ByteBufTest, vector) {
    ByteBuf buf;
    buf << 12345;
    std::cout << make_hex_str(buf.data(), buf.size()) << std::endl;
}


} ///namespace