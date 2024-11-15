#include <iostream>
#include <sstream>

#include "gtest/gtest.h"


class Base {
public:
    // default constructor
    Base() { std::cout << "[Base] Base()" << std::endl; }

    // destructor
    virtual ~Base() { std::cout << "[Base] ~Base()" << std::endl; }

    virtual void vprint() { std::cout << "[Base] vprint()" << std::endl; }
    void print() { std::cout << "[Base] print()" << std::endl; }
};


class Derived : public Base {
public:
    // default constructor
    Derived() { std::cout << "[Derived] Derived()" << std::endl; }

    // destructor
    virtual ~Derived() { std::cout << "[Derived] ~Derived()" << std::endl; }

    void vprint() { std::cout << "[Derived] vprint()" << std::endl; }
    void print() { std::cout << "[Derived] print()" << std::endl; }
};


class Virtualtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Virtualtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Virtualtest() {
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


// explicit cast:: always call of function of casting class's type
TEST_F(Virtualtest, virttempexplcast) {
    Derived d;
    std::cout << "expl_cast.print(): "; ((Base)d).print(); // temporary Base class is created
    std::cout << "expl_cast.vprint(): "; ((Base)d).vprint(); // temporary Base class is created
}


TEST_F(Virtualtest, virttempstacast) {
    Derived d;
    std::cout << "static_cast.print(): "; static_cast<Base>(d).print(); // temporary Base class is created
    std::cout << "static_cast.vprint(): "; static_cast<Base>(d).vprint(); // temporary Base class is created
}


// upcasting :: put a class into super class type variable as reference or pointer;
// upcasting upcasting+virtual only call overrided function in derived class 
TEST_F(Virtualtest, virtupcastrefs) {
    Derived d;
    Base b;
    Base& ub = d;
//    Base& ub{d};

    std::cout << "b.print(): "; b.print();
    std::cout << "b.vprint(): "; b.vprint();
    std::cout << "d.print(): "; d.print();
    std::cout << "d.vprint(): "; d.vprint();
    std::cout << "<upcasting>b.print(): "; ub.print();
    std::cout << "<upcasting>b.vprint(): "; ub.vprint();

}


TEST_F(Virtualtest, virtupcastptr) {
    Base* b = new Base{};
    Base* ub = new Derived{};

    std::cout << "b->print(): "; b->print();
    std::cout << "b->vprint(): "; b->vprint();
    std::cout << "<upcasting>b->print(): "; ub->print();
    std::cout << "<upcasting>b->vprint(): "; ub->vprint();

    delete b;
    delete ub;
}


/*
[ RUN      ] Virtualtest.virttempexplcast
[Base] Base()
[Derived] Derived()
expl_cast.print(): [Base] print()
[Base] ~Base()
expl_cast.vprint(): [Base] vprint()
[Base] ~Base()
[Derived] ~Derived()
[Base] ~Base()
[       OK ] Virtualtest.virttempexplcast (0 ms)
[ RUN      ] Virtualtest.virttempstacast
[Base] Base()
[Derived] Derived()
static_cast.print(): [Base] print()
[Base] ~Base()
static_cast.vprint(): [Base] vprint()
[Base] ~Base()
[Derived] ~Derived()
[Base] ~Base()
[       OK ] Virtualtest.virttempstacast (0 ms)
[ RUN      ] Virtualtest.virtupcastrefs
[Base] Base()
[Derived] Derived()
[Base] Base()
b.print(): [Base] print()
b.vprint(): [Base] vprint()
d.print(): [Derived] print()
d.vprint(): [Derived] vprint()
<upcasting>b.print(): [Base] print()
<upcasting>b.vprint(): [Derived] vprint()
[Base] ~Base()
[Derived] ~Derived()
[Base] ~Base()
[       OK ] Virtualtest.virtupcastrefs (0 ms)
[ RUN      ] Virtualtest.virtupcastptr
[Base] Base()
[Base] Base()
[Derived] Derived()
b->print(): [Base] print()
b->vprint(): [Base] vprint()
<upcasting>b->print(): [Base] print()
<upcasting>b->vprint(): [Derived] vprint()
[Base] ~Base()
[Derived] ~Derived()
[Base] ~Base()
[       OK ] Virtualtest.virtupcastptr (0 ms)
*/
