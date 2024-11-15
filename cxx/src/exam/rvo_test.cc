#include "gtest/gtest.h"
#include "rvo_test.h"


Rvo retrvo () {
    return Rvo{};
}

Rvo retnrvo () {
    Rvo r{};
    r.print();
    return r;
}

class Rvotest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Rvotest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Rvotest() {
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

TEST_F(Rvotest, rvotest) {
    std::cout << "==== RVO initialize ====" << std::endl;
    Rvo rvo1{retrvo()};
    rvo1.print();
    std::cout << std::endl << std::endl;


    std::cout << "==== RVO substituute ====" << std::endl;
    Rvo rvo2 = retrvo();
    rvo2.print();
    std::cout << std::endl << std::endl;


    std::cout << "==== NRVO initialize ====" << std::endl;
    Rvo nrvo1{retnrvo()};
    nrvo1.print();
    std::cout << std::endl << std::endl;


    std::cout << "==== NRVO substituute ====" << std::endl;
    Rvo nrvo2 = retnrvo();
    nrvo2.print();
    std::cout << std::endl << std::endl;


/*  Result ==========================================
RVO든 NRVO이든 컴파일러가 알아서 최적화 해서
반환-수신 객체가 같을 경우 instance는 단 한번만 생성된다. 

==== RVO initialize ====
D constructor: 0x7ffffdcdb6a4
print(): 0x7ffffdcdb6a4


==== RVO substituute ====
D constructor: 0x7ffffdcdb6a5
print(): 0x7ffffdcdb6a5


==== NRVO initialize ====
D constructor: 0x7ffffdcdb6a6
print(): 0x7ffffdcdb6a6
print(): 0x7ffffdcdb6a6


==== NRVO substituute ====
D constructor: 0x7ffffdcdb6a7
print(): 0x7ffffdcdb6a7
print(): 0x7ffffdcdb6a7


~Destructor: 0x7ffffdcdb6a7
~Destructor: 0x7ffffdcdb6a6
~Destructor: 0x7ffffdcdb6a5
~Destructor: 0x7ffffdcdb6a4
*/
}
