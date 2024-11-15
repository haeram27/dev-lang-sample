#include <iostream>

#include "gtest/gtest.h"

class PrivateClass {
private:
    int privateint = 5;

    friend class FriendClass; // FriendClass is friend of PrivateClass
};


class FriendClass {
private:
    PrivateClass pc;

public:
    int getPrivateInt(){
        return pc.privateint;
    };
};

void friendexam() {
}

class Friendtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Friendtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Friendtest() {
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

TEST_F(Friendtest, friendtest) {
    FriendClass fc;
    std::cout << fc.getPrivateInt() << std::endl;
}