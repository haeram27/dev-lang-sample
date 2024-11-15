#include <iostream>
#include <regex>
#include <string>

#include "gtest/gtest.h"
#include "exam/regex_test.h"

#define ARRAYSIZE 50


void regexexam() {
/*
    regex test for char* and string match
*/
    std::cout << "[TEST 1] regex test for char* match ====\n";
    char cstr[ARRAYSIZE] = "Like=\"Hello World\"";
    // CHECK: cmatch(ssub_match) result string, 
    // cmatch only has pointer against original string so original string should not be changed after match.
    std::cmatch cm;
    std::regex r{"like=\"(.*)\"", std::regex_constants::icase};

    if (std::regex_search(cstr, cm, r)) {
        // WARNING: cmatch cm has address into original string, if original string has changed then cmatch' value also will be changed.
        std::cout << "cmatch[1]: " << cm[1] << '\n';
        std::cout << "size matched: " << cm[1].length() << '\n';
        std::cout << "first: " << cm[1].first << '\n';
        std::cout << "second: " << cm[1].second << '\n';

        // change cmatch to str()
        char pbuf[ARRAYSIZE];
        memset(pbuf, 0x00, ARRAYSIZE);
        strncpy(pbuf, cm[1].str().c_str(), ARRAYSIZE);
        std::cout << "pbuf: " << pbuf << '\n';
    }

    std::cout << "\n\n[TEST 2] regex test for string match ====\n";
    std::string str{cstr};
    // CHECK: smatch(ssub_match) result string, 
    // smatch only has pointer against original string so original string should not be changed after match.
    std::smatch sm;
    if (std::regex_search(str, sm, r)) {
        // WARNING: cmatch sm has address into original string, if original string has changed then smatch' value also will be changed.
        std::cout << "smatch[1]: " << sm[1] << '\n';
        for (auto x:sm) std::cout << x << " ";
        std::cout << std::endl;
    }


    std::cout << "\n\n[TEST 3] cmatch value is changed after change original string ====\n";
    if (std::regex_search(cstr, cm, r)) {
        // WARNING: cmatch and smatch has  JUST address into original string, if original string has changed then cmatch' value also will be changed.
        memset(cstr, 0x00, ARRAYSIZE);
        strncpy(cstr, "MerongAMerong", ARRAYSIZE);

        std::cout << "cmatch[1]: " << cm[1] << '\n';
        std::cout << "size matched: " << cm[1].length() << '\n';
        std::cout << "first: " << cm[1].first << '\n';
        std::cout << "second: " << cm[1].second << '\n';
    }


/*
    group matching test
*/
    std::cout << "\n\n[TEST 4] regex group matching ====\n";
    std::string s2 {"this subject has a submarine as a subsequence"};
    std::regex r2 {"\\b(sub)([^ ]*)"};   // matches words beginning by "sub"
    std::smatch sm2;

    std::cout << "Target sequence: " << s2 << std::endl;
    std::cout << "Regular expression: /\\b(sub)([^ ]*)/" << std::endl;
    std::cout << "The following matches and submatches were found:" << std::endl;

    while (std::regex_search (s2,sm2,r2)) {
        for (auto x:sm2) std::cout << x << " ";
        std::cout << std::endl;

        s2 = sm2.suffix().str();
        std::cout <<"next string: " << s2 << std::endl;
    }
}


class Regextest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Regextest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Regextest() {
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

TEST_F(Regextest, regextest) {
    regexexam();
}