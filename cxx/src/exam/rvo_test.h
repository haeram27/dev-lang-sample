#include <iostream>
#include <vector>
#include <sstream>
#include <string>


class Rvo {
public:
    Rvo() { std::cout << "D constructor: " << (void*) this << std::endl; }
    ~Rvo() { std::cout << "~Destructor: " << (void*) this << std::endl; }
    Rvo(const Rvo& r) { std::cout << "C constructor: " << (void*) this << std::endl; }
    Rvo& operator=(const Rvo& r) { std::cout << "C assign operator: " << (void*) this << std::endl; }
    Rvo(const Rvo&& r) { std::cout << "M constructor: " << (void*) this << std::endl; }
    Rvo& operator=(const Rvo&& r) { std::cout << "M assign operator: " << (void*) this << std::endl; }

    void print() {std::cout << "print(): " << (void*) this << std::endl;}
};