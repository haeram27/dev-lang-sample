#include "gtest/gtest.h"
#include "constructor_test.h"

class Constructor: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Constructor() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Constructor() {
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


TEST_F(Constructor, structor) {
    std::cout << "====foo1========================== "<< std::endl;
    Foo foo1{"foo1"};

    std::cout << "foo1.print()" << std::endl;
    foo1.print();
    std::cout << std::endl << std::endl << std::endl;


    std::cout << "====foo2========================== "<< std::endl;
    Foo foo2 {foo1};
    std::cout << "foo2.print()" << std::endl;
    foo2.print();

    std::cout << std::endl;
    std::cout << "foo1.name_: " << (void*) &foo1.name_ << std::endl;
    std::cout << "foo2.name_: " << (void*) &foo2.name_ << std::endl;
    std::cout << "foo1.name_.data(): " << (void*) (foo1.name_.data()) << std::endl;
    std::cout << "foo2.name_.data(): " << (void*) (foo2.name_.data()) << std::endl;
    std::cout << std::endl << std::endl << std::endl;


    std::cout << "====foo3========================== "<< std::endl;
#if 1
    Foo foo3 {"foo3"};
    foo3 = foo1;
#else
    Foo3 foo3 = foo1;
#endif

    std::cout << "foo1.name_.data(): " << (void*) (foo1.name_.data()) << std::endl;
    std::cout << "foo3.name_.data(): " << (void*) (foo3.name_.data()) << std::endl;
    std::cout << std::endl;

    std::cout << "foo3.print()" << std::endl;
    foo3.print();
    std::cout << std::endl << std::endl << std::endl;
}
