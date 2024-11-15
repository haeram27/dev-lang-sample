#include <iostream>
#include <memory>

#include "gtest/gtest.h"
#include "exam/smartpointer_test.h"

using namespace std;


void smartpointer_exam() {
    std::cout << "[smartpointer_exam]" << std::endl;

//// unique_ptr
    cout << endl << ">>>> unique_ptr test <<<<" << endl;
    //unique_ptr<int> up1;
    //make-unique  C++14
    auto up1 = make_unique<int>(10);
    //unique_ptr<int> up1(new int(10));
    cout << "*up1: " << *up1 << endl;
    // unique_ptr<int> up2 = up1;    // error

    cout << ">>>> unique_ptr<int> up3 = move(up1);" << endl;
    unique_ptr<int> up3 = move(up1);
    // cout << *up1 << endl;         // error
    // unique_ptr.get() : 스마트 포인터가 관리하는 데이터이 주소값 획득
    cout << "up1.get(): " << up1.get() << endl;       
    cout << "up3.get(): " << up3.get() << endl;



//// shared_ptr
    cout << endl << ">>>> shared_ptr test <<<<" << endl;
    //shared_ptr<int> up1;
    
    //shared_ptr<int> sp1(new int(10));
    auto sp1 = make_shared<int>(10); //make-shared  C++11
    shared_ptr<int> sp2(sp1);

    cout << "*sp1: " << *sp1 << endl;
    cout << "*sp2: " << *sp2 << endl;
 
     // use_count() - 데이터의 reference count를 반환
    cout << "sp2.use_count(): " << sp2.use_count() << endl;
    // reset() - 실제 데이터에 대한 소유를 더 이상 하지 않겠다고 명시적으로 지정
    //           데이터에 대한 reference count가 1 줄어듬
    cout << ">>>> sp1.reset();" << endl;
    sp1.reset();
    cout << "sp2.use_count(): " << sp2.use_count() << endl;
    cout << ">>>> sp2.reset();" << endl;
    sp2.reset();
    cout << "sp2.use_count(): " << sp2.use_count() << endl;



//// weak_ptr
    cout << endl << ">>>> weak_ptr test <<<<" << endl;
    cout << ">>>> weak_ptr<int> wp1 = spw1;" << endl;
    auto spw1 = make_shared<int>(10);
    weak_ptr<int> wp1(spw1);
    cout << "spw1.use_count(): " << spw1.use_count() << endl;
    cout << "wp1.use_count(): " << wp1.use_count() << endl;

    {
        cout << ">>>> shared_ptr<int> spw2 = wp1.lock();" << endl;
        // lock()함수는 shared_ptr의 객체를 생성해서 반환해줌
        shared_ptr<int> spw2 = wp1.lock();

        cout << "spw1.use_count(): " << spw1.use_count() << endl;
        cout << "wp1.use_count(): " << wp1.use_count() << endl;
    }

    cout << "spw1.use_count(): " << spw1.use_count() << endl;
    cout << "wp1.use_count(): " << wp1.use_count() << endl;
}


class SmartPtrTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    SmartPtrTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~SmartPtrTest() {
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

TEST_F(SmartPtrTest, smartptest) {
    smartpointer_exam();
}