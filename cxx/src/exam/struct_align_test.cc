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
class StructAlignTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StructAlignTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StructAlignTest() {
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

using namespace std;
/***** TEST UNIT CONTEXT UNDER HERE *****/

struct PACK {
    int a;
    char b;
    double c;
};

TEST_F(StructAlignTest, fixedtype)
{STARTTIME

    PACK p;
    cout << sizeof(p) << endl;  // 64::16

    long l;
    cout << sizeof(l) << endl;  // 64::8

    long long ll;
    cout << sizeof(ll) << endl;  // 64::8

    double d;
    cout << sizeof(d) << endl;  // 64::8

    long double ld;
    cout << sizeof(ld) << endl;  // 64::16

ENDTIME}

typedef struct _ASTRUCT {
    uint8_t a;
    uint8_t b;
    uint8_t c;
    uint8_t d;
    uint32_t e;
    uint64_t f;
} ASTRUCT;

typedef struct _BSTRUCT {
    uint8_t a;
    uint8_t b;
    uint32_t e;
    uint8_t c;
    uint8_t d;
    uint64_t f;
} BSTRUCT;

#pragma pack(push,1)
typedef struct _B1STRUCT {
    uint8_t a;
    uint8_t b;
    uint32_t e;
    uint8_t c;
    uint8_t d;
    uint64_t f;
} B1STRUCT;
#pragma pack(pop)


TEST_F(StructAlignTest, vector) {
    ASTRUCT ast;
    std::clog << "==== ASTRUCT pack size: default ====" << std::endl;
    std::clog << "struct size:" << sizeof(ast) << std::endl;
    std::clog << "a: size:" << sizeof(ast.a) << " addr:" << (void*)&(ast.a) << std::endl;
    std::clog << "b: size:" << sizeof(ast.b) << " addr:" << (void*)&(ast.b) << std::endl;
    std::clog << "c: size:" << sizeof(ast.c) << " addr:" << (void*)&(ast.c) << std::endl;
    std::clog << "d: size:" << sizeof(ast.d) << " addr:" << (void*)&(ast.d) << std::endl;
    std::clog << "e: size:" << sizeof(ast.e) << " addr:" << (void*)&(ast.e) << std::endl;
    std::clog << "f: size:" << sizeof(ast.f) << " addr:" << (void*)&(ast.f) << std::endl;

    BSTRUCT bst;
    std::clog << "==== BSTRUCT pack size: default ====" << std::endl;
    std::clog << "struct size:" << sizeof(bst) << std::endl;
    std::clog << "a: size:" << sizeof(bst.a) << " addr:" << (void*)&(bst.a) << std::endl;
    std::clog << "b: size:" << sizeof(bst.b) << " addr:" << (void*)&(bst.b) << std::endl;
    std::clog << "e: size:" << sizeof(bst.e) << " addr:" << (void*)&(bst.e) << std::endl;
    std::clog << "c: size:" << sizeof(bst.c) << " addr:" << (void*)&(bst.c) << std::endl;
    std::clog << "d: size:" << sizeof(bst.d) << " addr:" << (void*)&(bst.d) << std::endl;
    std::clog << "f: size:" << sizeof(bst.f) << " addr:" << (void*)&(bst.f) << std::endl;

    B1STRUCT b1st;
    std::clog << "==== B1STRUCT pack size: 1 ====" << std::endl;
    std::clog << "struct size:" << sizeof(b1st) << std::endl;
    std::clog << "a: size:" << sizeof(b1st.a) << " addr:" << (void*)&(b1st.a) << std::endl;
    std::clog << "b: size:" << sizeof(b1st.b) << " addr:" << (void*)&(b1st.b) << std::endl;
    std::clog << "e: size:" << sizeof(b1st.e) << " addr:" << (void*)&(b1st.e) << std::endl;
    std::clog << "c: size:" << sizeof(b1st.c) << " addr:" << (void*)&(b1st.c) << std::endl;
    std::clog << "d: size:" << sizeof(b1st.d) << " addr:" << (void*)&(b1st.d) << std::endl;
    std::clog << "f: size:" << sizeof(b1st.f) << " addr:" << (void*)&(b1st.f) << std::endl;
}


} ///namespace