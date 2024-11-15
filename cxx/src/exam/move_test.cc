#include <cstring>

#include <chrono>
#include <iostream>
#include <sstream>

#include "gtest/gtest.h"

namespace {

/***** DO NOT REMOVE *****/
#define TESTBEGIN ElapsedTime et;
#define TESTEND et.elapsed();
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

class MoveTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    MoveTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~MoveTest() {
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


/***** TEST UNIT CONTEXT UNDER HERE *****/

class MyString {
    char *string_content;  // 문자열 데이터를 가리키는 포인터
    int string_length;     // 문자열 길이

    int memory_capacity;  // 현재 할당된 용량

public:
    MyString();

    // 문자열로 부터 생성
    MyString(const char *str);

    // 복사 생성자
    MyString(const MyString &str);

    // 이동 생성자
    MyString(MyString &&str);

    // 복사 대입 연산자
    MyString &operator=(const MyString &s);

    // 이동 대입 연산자
    MyString& operator=(MyString&& s);

    ~MyString();

    int length() const;

    void println();
};

MyString::MyString() {
    std::cout << "Default Constructor !" << std::endl;
    string_length = 0;
    memory_capacity = 0;
    string_content = NULL;
}

MyString::MyString(const char *str) {
    std::cout << "Constructor !" << std::endl;
    string_length = strlen(str);
    memory_capacity = string_length;
    string_content = new char[string_length];

    for (int i = 0; i != string_length; i++) string_content[i] = str[i];
}

MyString::MyString(const MyString &str) {
    std::cout << "Copy Constructor !" << std::endl;
    string_length = str.string_length;
    string_content = new char[string_length];

    for (int i = 0; i != string_length; i++)
    string_content[i] = str.string_content[i];
}

MyString::MyString(MyString &&str) {
    std::cout << "Move Constructor !" << std::endl;
    string_length = str.string_length;
    string_content = str.string_content;
    memory_capacity = str.memory_capacity;

    // 임시 객체 소멸 시에 메모리를 해제하지
    // 못하게 한다.
    str.string_content = nullptr;
    str.string_length = 0;
    str.memory_capacity = 0;
}

MyString::~MyString() {
    if (string_content) delete[] string_content;
}

MyString &MyString::operator=(const MyString &s) {
    std::cout << "Copy Substitution Operator !" << std::endl;
    if (s.string_length > memory_capacity) {
    delete[] string_content;
    string_content = new char[s.string_length];
    memory_capacity = s.string_length;
    }

    string_length = s.string_length;
    for (int i = 0; i != string_length; i++) {
    string_content[i] = s.string_content[i];
    }

    return *this;
}

MyString& MyString::operator=(MyString&& s) {
    std::cout << "Move Substitution Operator !" << std::endl;
    string_content = s.string_content;
    memory_capacity = s.memory_capacity;
    string_length = s.string_length;

    s.string_content = nullptr;
    s.memory_capacity = 0;
    s.string_length = 0;
    return *this;
}

int MyString::length() const { return string_length; }
void MyString::println() {
    for (int i = 0; i != string_length; i++) std::cout << string_content[i];

    std::cout << std::endl;
}

template <typename T>
void my_swap(T &a, T &b) {
    T tmp(std::move(a));
    a = std::move(b);
    b = std::move(tmp);
}

class MyInt {
public:
    MyInt();
    MyInt(const int i);

    // 복사 생성자
    MyInt(const MyInt &i);

    // 이동 생성자
    MyInt(MyInt &&i);

    // 복사 대입 연산자
    MyInt& operator=(const MyInt &i);

    // 이동 대입 연산자
    MyInt& operator=(MyInt&& i);

    ~MyInt();

private:
    int i_;
};

MyInt::MyInt() {
    std::cout << "Default Constructor !" << std::endl;
    i_ = 0;
}

MyInt::MyInt(const int i) {
    std::cout << "Constructor !" << std::endl;
    i_ = i;
}

MyInt::MyInt(const MyInt &i) {
    std::cout << "Copy Constructor !" << std::endl;
    i_ = i.i_;
}

MyInt::MyInt(MyInt &&i) {
    std::cout << "Move Constructor !" << std::endl;
    i_ = i.i_;
}

MyInt::~MyInt() {
    //std::cout << "Destructor !" << std::endl;
}

MyInt &MyInt::operator=(const MyInt &i) {
    std::cout << "Copy Substitution Operator !" << std::endl;
    i_ = i.i_;

    return *this;
}

MyInt& MyInt::operator=(MyInt&& i) {
    std::cout << "Move Substitution Operator !" << std::endl;
    i_ = i.i_;

    return *this;
}

MyInt makemyint(const int i) {
    return MyInt{i};
}

TEST_F(MoveTest, basic)
{TESTBEGIN

    MyString str1("abc");
    MyString str2("def");
    std::cout << "Before Swap -----" << std::endl;
    std::cout << "str1 : ";
    str1.println();
    std::cout << "str2 : ";
    str2.println();

    std::cout << "After Swap -----" << std::endl;
    my_swap(str1, str2);
    std::cout << "str1 : ";
    str1.println();
    std::cout << "str2 : ";
    str2.println();

TESTEND}


TEST_F(MoveTest, lvalue)
{TESTBEGIN

    std::cout << "----------(1)" << std::endl;
    MyInt i{10};            // Constructor !

    std::cout << "----------(2)" << std::endl;
    MyInt c{MyInt{20}};     // Constructor !

    std::cout << "----------(3)" << std::endl;
    MyInt d{makemyint(30)}; // Constructor !

    std::cout << "----------(4)" << std::endl;
    MyInt a{i};             // Copy Constructor !

    std::cout << "----------(5)" << std::endl;
    MyInt b{std::move(i)};  // Move Constructor !

    std::cout << "----------(6)" << std::endl;
    MyInt e;                // Default Constructor !

    std::cout << "----------(7)" << std::endl;
    e = i;                  // Copy Substitution Operator !

    std::cout << "----------(8)" << std::endl;
    e = std::move(i);       // Move Substitution Operator !

    std::cout << "----------(9)" << std::endl;
    e = 15+20;              // Constructor!
                            // Move Substitution Operator !

    std::cout << "----------(10)" << std::endl;
    e = std::move(15+20);   // Constructor!
                            // Move Substitution Operator !

TESTEND}


} ///namespace
