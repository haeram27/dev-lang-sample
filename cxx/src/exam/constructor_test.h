#include <iostream>
#include <vector>
#include <sstream>
#include <string>


class Foo {
public:
    Foo() { name_ = "default"; std::cout << "D constructor: " << name_ << std::endl; }
    Foo(const std::string name) { name_ = name; std::cout << "D constructor: " << name_ << std::endl; }
    Foo(const Foo& f): name_{f.name_} { std::cout << "C constructor: " << name_ << std::endl; }
    Foo& operator=(const Foo& f) { name_ = f.name_; std::cout << "C assign operator: " << name_ << std::endl; }
    Foo(const Foo&& f): name_{f.name_} { std::cout << "M constructor: " << name_ << std::endl; }
    Foo& operator=(const Foo&& f) { name_ = f.name_; std::cout << "M assign operator: " << name_ << std::endl; }

    void print() { std::cout << name_ << std::endl; }

    std::string name_;
};
