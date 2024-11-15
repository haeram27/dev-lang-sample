
#include <cstdlib>
#include <chrono>
#include <condition_variable>
#include <iostream>
#include <mutex>
#include <thread>

#include "gtest/gtest.h"
#include "util/path.h"



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

class MutexRecursiveTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    MutexRecursiveTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~MutexRecursiveTest() {
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

/***** TEST UNIT CONTEXT UNDER HERE *****/

#define MUTEX 1
class SharedObjectInt {
public:
    int Get(int id);
    void Increase(int id);
    void IncreaseAndGet(int id);

private:
    int shared_int_;
    std::recursive_mutex rmtx_;

};

int SharedObjectInt::Get(int id) {
#if (defined(MUTEX) && (MUTEX == 1))
    std::lock_guard<std::recursive_mutex> lk(rmtx_);
#endif
    return shared_int_;
}

void  SharedObjectInt::Increase(int id) {
#if (defined(MUTEX) && (MUTEX == 1))
    std::lock_guard<std::recursive_mutex> lk(rmtx_);
#endif
    ++shared_int_;
}

void  SharedObjectInt::IncreaseAndGet(int id) {
#if (defined(MUTEX) && (MUTEX == 1))
    std::lock_guard<std::recursive_mutex> lk(rmtx_);
#endif
    Increase(id);
    std::this_thread::sleep_for(std::chrono::milliseconds(10));
    std::cout << "IncreaseAndGet(" << id << "): val:" << Get(id) << std::endl;
}


static SharedObjectInt soi;
void RunnerInt(const int id) {
//    std::cout << "wait(" << id << ")  Called" << std::endl;

    for (auto i = 0; i < 100; ++i) {
        soi.IncreaseAndGet(id);
    }
}

TEST_F(MutexRecursiveTest, update) {
    using namespace std::chrono;
    std::vector<std::thread> thread_vector;

    system_clock::time_point start = system_clock::now();
    for (auto i = 0; i < 10; ++i) {
        thread_vector.emplace_back(RunnerInt, i);
    }

    for (auto i = 0; i < 10; ++i) {
        // thread will wait for calling join() or detach() after it's execution is finished.
        thread_vector[i].join();
        std::cout << "join(thread:" << i << ") Called" << std::endl;
    }

    system_clock::time_point end = system_clock::now();
    milliseconds msec = duration_cast<milliseconds>(end - start);
    std::cout << "======== Elapsed: " << msec.count() << "========" << std::endl;
}


} /// namespace