#include <exception>
#include <iostream>
#include <string>

#include "gtest/gtest.h"
#include "exam/except_test.h"


/*
[KEYWORD]
throw  -  현재 함수의 진행을 멈추고 지정한 객체를 exception 처리 루틴(try-catch block)으로 던진다.
       throw된 객체는 상위 stack 중 가장 가까운 try-catch 블럭에서 처리된다.
       throw는 exception을 발생시키기 위한 용도의 키워드 이지만 exception 객체 뿐만 아니라 모든 타입의 객체를 exception 처리루틴에 던질 수 있다.
       throw로 던져진 객체가 상위 stack중 어디에서도 exception 처리 루틴(try-catch)에 의해 catch되지 않고 끝까지 던져져서 main() stack을 벗어나게 되면 프로그램은 강제 종료(terminate()-abort) 된다.

try  -  try로 지정된 block 안에서 throw로 던져진 모든 타입의 객체를 전달 받아 catch stack으로 전달한다.
     throw는 모든 객체를 던질 수 있으나 객체 중에는 std::exception도 포함되며 
     try-catch의 주 목적은 본래 exception을 처리하기 위함이다.

catch  -  try block안에서 throw로 던져진 객체 중에 catch가 자신의 parameter로 지정된 타입 객체만 가져와 처리한다.

noexcept  -  noexcept로 지정된 함수 안에서는 throw를 사용하면 안된다. 이 키워드는 다음 두가지 역할을 한다.
          1. noexcept 키워드는 컴파일러에게 이 함수는 throw를 사용(exception을 발생)하지 않는다고 명시한다.
          noexcept 함수 안에서 throw 키워드 사용시 컴파일러는 compile-time에 warning출력하고 error는 발생시키지 않는다.
          컴파일러는 noexcept로 지정된 함수에 대해서 throw가 사용되지 않음을 가정하고 최적화를 실행한다.
          2. noexcept가 사용된 함수에서 throw가 사용되면 exception 처리 루틴이 진행되지 않고
          해당 지점에서 프로그램이 강제 비정상 종료(abort) 된다.

std::exception  -  모든 excpetion class의 base class.
                   custom exception class를 생성하려면 반드시 상속해 주는 것이 바람직하다.
                   <exception> 에 정의되어 있다.


[TIP]
1. C++11 부터 소멸자는 모두 noexcept 이다. 
   exception을 처리하지 않으므로 소멸자에서는 exception을 던지지 말것

2. 생성자에서 exception이 생길 경우 소멸자가 호출되지 않으므로 주의
   예를 들어 멤버 변수들 중에 heap allocation을 하는 포인터가 여러 개 있는데 
   생성자가 메모리 할당에 실패한다면 exception을 던지기 전에 지금까지 할당한 메모리들을 해제해야 함
   그냥 스마트포인터 사용하면 위 문제(memory leak) 모두 해결

3. exception은 아무거나 던질 수 있으나,
   여러 유용한 함수들 (std::nested_exception 등)과 호환이 되기 위해서는 
   던지는 exception은 모두 std::exception을 상속하게 하는 것이 바람직

4. 템플릿으로 정의되는 클래스의 경우 instantiate되기 전까지는 
   어떤 exception을 던질 지 알수 없으므로 catch 블록에서 모든 exception을 고려해야 함
*/


class ParentCustomException : public std::exception {
public:
    virtual const char* what() const noexcept override { return "ParentCustomException!\n"; }
};

class ChildCustomException : public ParentCustomException {
public:
    const char* what() const noexcept override { return "ChildCustomException!\n"; }
};

template <typename T>
void throwobjfunc(T t) {
    throw t;
}

void throwfunc(const std::string& s) {
    if (s.compare("parent") == 0) {
        throw ParentCustomException();
    } else if (s.compare("child") == 0) {
        throw ChildCustomException();
    }
}


void normalfunc(const std::string& s) {
    throwfunc(s);
}


/*
    throw exception in noexcept function WILL MAKE ABNORMAL TERMINATION of PROGRAM ALWAYS. 
    basically SHOULD NOT use throw in noexcept function
    throw from noexcept function CANNOT be catched in exception handling(try-catch)
*/
void noexceptthrowfunc(const std::string& s) noexcept{
    if (s.compare("parent") == 0) {
        throw ParentCustomException();
    } else if (s.compare("child") == 0) {
        throw ChildCustomException();
    }
}

/*
    try catch object from noexcept function
    but this will NOT catch any thrown object from noexcept function...
*/
void noexcepcatchfunc(const std::string& s) {
    try {
        noexceptthrowfunc(s);
    } catch (std::exception e) {
        std::cout << e.what();
    } catch (...) {  // catch (...)  is mached with every exception
        std::cout << "Every Exception Catch!" << std::endl;
    }

}

void exceptexam() {
    int count = 0;

    // Catch Int : 100
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        throwobjfunc(100);
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (int x) {
        std::cout << "Catch Int : " << x << std::endl;
    }

    // Catch Char : A
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        throwobjfunc('A');
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (char x) {
        std::cout << "Catch Char : " << x << std::endl;
    }


    // Catch Literal String : literal string
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        throwobjfunc("literal string");
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (const char* s) {
        std::cout << "Catch Literal String : " << s << std::endl;
    }


    // Catch String : string
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        throwobjfunc(std::string{"string"});
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (std::string& s) {
        std::cout << "Catch String : " << s << std::endl;
    }


// Exception Catch!
// std::exception
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        normalfunc("child");
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (std::exception e) {    // most super exception class
        std::cout << "Exception Catch!" << std::endl;
        std::cout << e.what();
    } catch (ParentCustomException& p) {    // 2nd super exception class
        std::cout << "Parent Catch!" << std::endl;
        std::cout << p.what();
    } catch (ChildCustomException& c) {    // last exception class
        std::cout << "Child Catch!" << std::endl;
        std::cout << c.what();
    }


// Parent Catch!
// ChildCustomException!
    try {
        std::cout << "\n\n[START TEST] " << ++count << std::endl;
        normalfunc("child");
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (ParentCustomException& p) {
        std::cout << "Parent Catch!" << std::endl;
        std::cout << p.what();
    } catch (ChildCustomException& c) {
        std::cout << "Child Catch!" << std::endl;
        std::cout << c.what();
    }


// Child Catch!
// ChildCustomException!
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        normalfunc("child");
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (ChildCustomException& c) {
        std::cout << "Child Catch!" << std::endl;
        std::cout << c.what();
    } catch (ParentCustomException& p) {
        std::cout << "Parent Catch!" << std::endl;
        std::cout << p.what();
    } catch (std::exception e) {
        std::cout << "Exception Catch!" << std::endl;
        std::cout << e.what();
    } catch (...) {  // catch (...)  is mached with every exception
        std::cout << "Every Exception Catch!" << std::endl;
    }


// Every Exception Catch!
    try {
        std::cout << "\n[START TEST] " << ++count << std::endl;
        normalfunc("child");
        std::cout << "WILL NOT BE RUN" << std::endl;
    } catch (std::exception e) {    // most super exception class
        std::cout << "Exception Catch!" << std::endl;
        std::cout << e.what();
    } catch (...) {  // catch (...)  is mached with every exception
        std::cout << "Every Exception Catch!" << std::endl;
    }

#if 0
// terminate called after throwing an instance of 'ChildCustomException'
//   what():  ChildCustomException!
    std::cout << "\n[START TEST] " << ++count << std::endl;
    normalfunc("child");  //CRASH
#endif

#if 0
// throw EXCEPTION in noexcet function
// This will be CRASH even if exception hanldling code is existed
    std::cout << "\n[START TEST] " << ++count << std::endl;
    noexcepcatchfunc("child");  //CRASH
#endif

}

class Excepttest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Excepttest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Excepttest() {
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

TEST_F(Excepttest, excepttest) {
    exceptexam();
}