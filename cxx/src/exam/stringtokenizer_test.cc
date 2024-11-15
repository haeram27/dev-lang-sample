#include <cstring>
#include <iostream>
#include <string>
#include <sstream>
#include <vector>

#include "gtest/gtest.h"
#include "exam/stringtokenizer_test.h"


using namespace std;

class Strtoktest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Strtoktest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Strtoktest() {
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


void tokenizeA(const string& str, vector<string>& tokens, const string& delimiters = " ") {
    // 맨 첫 글자가 구분자인 경우 무시
    string::size_type lastPos = str.find_first_not_of(delimiters, 0);
    // 구분자가 아닌 첫 글자를 찾는다
    string::size_type pos = str.find_first_of(delimiters, lastPos);

    while (string::npos != pos || string::npos != lastPos) {
        // token을 찾았으니 vector에 추가한다
        tokens.push_back(str.substr(lastPos, pos - lastPos));
        // 구분자를 뛰어넘는다.  "not_of"에 주의하라
        lastPos = str.find_first_not_of(delimiters, pos);
        // 다음 구분자가 아닌 글자를 찾는다
        pos = str.find_first_of(delimiters, lastPos);
    }
}

void tokenizeB() {
    string str("Split me by whitespaces");
    string buf; // 버퍼 string
    stringstream ss(str); // string을 stream에 넣는다

    vector<string> tokens; // word들을 넣을 vector

    while (ss >> buf) {
        std::cout << "token: " << buf << std::endl;
        tokens.push_back(buf);
    }
}

void stringtokenizer () {
    std::cout << "stringtokenizer" << std::endl;
}



TEST_F(Strtoktest, strtoktest) {
    stringtokenizer();
}