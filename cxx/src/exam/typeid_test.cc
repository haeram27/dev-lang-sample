#include <iostream>
#include <typeinfo>  //for 'typeid' to work

#include "gtest/gtest.h"
#include "exam/typeid_test.h"

using namespace std;


class Person {
    public:
    // ... Person members ...
    virtual ~Person() {}
};

class Employee : public Person {
    // ... Employee members ...
};

int typeidexam () {
    Person person;
    Employee employee;
    Person *ptr = &employee;
    int t = 3;

    std::cout << typeid(t).name() << std::endl;

    // Person (statically known at compile-time)
    // result: 6Person - 6(Length of class name) Person(class name)
    std::cout << typeid(person).name() << std::endl;

    // Employee (statically known at compile-time)
    // result: 8Employee - 8(Length of class name) Employee(class name)
    std::cout << typeid(employee).name() << std::endl;

    // Person * (statically known at compile-time)
    // result: P6Person - P(pointer) 6(Length of class name) Person(class name)
    std::cout << typeid(ptr).name() << std::endl;

    // Employee (looked up dynamically at run-time
    // because it is the dereference of a pointer
    // to a polymorphic class)
    std::cout << typeid(*ptr).name() << std::endl;


    char c = ' ';
    float f = 0.00;

    // using typeinfo operator, == or != for comparison
    if (typeid(c) == typeid(f))
        std::cout << "c and f are the same type." << std::endl;
    else
        std::cout << "c and f are different type." << std::endl;

    // before() checks whether this typeid is SUPER class of argument typeid...
    std::cout << typeid(int).name();
    std::cout << " before " << typeid(double).name() << ": " <<
        (typeid(int).before(typeid(double)) ? "true" : "false") << std::endl;

    std::cout << typeid(double).name();
    std::cout << " before " << typeid(int).name() << ": " <<
        (typeid(double).before(typeid(int)) ? "true" : "false") << std::endl;

    // Person is SUPER class of Employee class
    std::cout << typeid(Person).name();
    std::cout << " before " << typeid(Employee).name() << ": " <<
        (typeid(Person).before(typeid(Employee)) ? "true" : "false") << std::endl;

    return 0;
}


class Typeidtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Typeidtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Typeidtest() {
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

TEST_F(Typeidtest, typeidtest) {
    typeidexam();
}