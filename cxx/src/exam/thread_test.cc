#include <iostream>
#include <mutex>
#include <string>
#include <queue>
#include <thread>
#include <vector>

#include "gtest/gtest.h"

class Threadtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Threadtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Threadtest() {
        // You can do clean-up work that doesn't throw exceptions here.
    }

    virtual void SetUp() override {
        // Code here will be called immediately after each test (right
        // before each test).
    }

    virtual void TearDown() override {
        // Code here will be called immediately after each test (right
        // before the destructor).
        // No exception handling allowed here.
    }
};


void func_copy(uint32_t id, uint32_t count) {
    for (int i = 0; i < count; ++i) {
        std::cout << "func_copy::thread(" << id << "):" << i << std::endl;
    }
}


void func_ref(uint32_t& id, uint32_t& count) {
    for (int i = 0; i < count; ++i) {
        std::cout << "func_ref::thread(" << id << "):" << i << std::endl;
    }
}


void func_ptr(uint32_t* id, uint32_t* count) {
    for (int i = 0; i < *count; ++i) {
        std::cout << "func_ptr::thread(" << *id << "):" << i << std::endl;
    }
}


TEST_F(Threadtest, detachtest) {
    std::vector<std::thread> thread_vector;
    int count = 3;

    for (auto i = 0; i < count; ++i) {
        // thread is run as soon as std::thread<> instance is created 
        thread_vector.emplace_back(func_copy, i, 20);
    }
    std::cout << "main::thread_vector push complete" << std::endl;

    for (auto i = 0; i < count; ++i) {
        // thread will wait for calling join() or detach()
        // after it's execution is finished.
        thread_vector[i].detach();
        std::cout << "thread(" << i << "):: detached" << std::endl;
    }
    std::cout << "main:: terminated" << std::endl;
}


TEST_F(Threadtest, jointest) {
    std::vector<std::thread> thread_vector;
    int count = 3;

    for (auto i = 0; i < count; ++i) {
        // thread is run as soon as std::thread<> instance is created 
        thread_vector.emplace_back(func_copy, i, 1000);
    }
    std::cout << "main::thread_vector push complete" << std::endl;

    //for (auto i = count-1; i >= 0; --i) {
    for (auto i = 0; i < count; ++i) {
        // thread will wait for calling join() or detach()
        // after it's execution is finished.
        thread_vector[i].join();
        std::cout << "thread(" << i << "):: join" << std::endl;
    }
    std::cout << "main:: terminated" << std::endl;
}
