#include <chrono>
#include <iostream>
#include <sstream>

#include "gtest/gtest.h"


class Sstreamtest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Sstreamtest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Sstreamtest() {
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


template<typename TInputIter>
static std::string make_hex_string(TInputIter first, TInputIter last, bool use_uppercase = false, bool insert_spaces = true)
{
    std::ostringstream ss;
    ss << std::hex << std::setfill('0');
    if (use_uppercase) { ss << std::uppercase; };
    while (first != last)
    {
        // stringstream recognizes integer when "rhs of <<" is NOT <uint8_t> or <char> type.
        ss << std::setw(2) << static_cast<int>(*first++);
        if (insert_spaces && first != last) ss << " ";
    }
    return ss.str();
}


TEST_F(Sstreamtest, sstream) {
    std::stringstream ss;
    ss << "hi " << 12 << " " << 3.14 << true << false << std::endl;
    std::cout << ss.str();
    ss.str(""); // clear contents

    // basically std::stringstream regards each of letter of all-type-input is ascii character so it saves each letter as ascii value into memory.
    // so even if input has inter type of letter, it converts as "integer letter" > "character letter" > ascii value > memory.
    ss << "1024" << " " << 1024;
    auto str{ss.str()};
    std::cout << str << std::endl;
    std::cout << make_hex_string(str.begin(), str.end()) << std::endl;
    ss.str(""); // clear contents

    // dec/oct/hex change base of  integer type input
    // std::hex will converts 1024 through "dec base integer" > "hex base integer" > "character letter" > ascii value > memory.
    ss << std::hex << "1024"  << " " <<  1024;
    str = ss.str();
    std::cout << str << std::endl;
    std::cout << make_hex_string(str.begin(), str.end()) << std::endl;
    ss.str(""); // clear contents
}


TEST_F(Sstreamtest, strappend) {
    using namespace std::chrono;
    steady_clock::time_point steadyclk_start;
    steady_clock::time_point steadyclk_end;
    std::stringstream ss;
    std::string str;

    char* long_word_char = R"(`1234567890-=~!@#$%^&*()_+qwertyuiopQWERTYUIOP[]|asdfghjkl;'ASDFGHJKL:\"zxcvbnm,./ZXCVBNM<>?)";
    std::string long_word_str{"`1234567890-=~!@#$%^&*()_+qwertyuiopQWERTYUIOP[]|asdfghjkl;'ASDFGHJKL:\"zxcvbnm,./ZXCVBNM<>?"};

/*
    stream << str operator process time: 95us.
    string.append(str) process time: 28us.
    stream << char* operator process time: 14us.
    string.append(char*) process time: 10us.
*/

    ss.str("");
    ss.clear();
    steadyclk_start = steady_clock::now();
    for (auto i = 0; i < 1000; ++i) { ss << long_word_str; };
    steadyclk_end = steady_clock::now();
    std::cout << "stream << str operator process time: "
              << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
              << "us.\n";

    str.clear();
    steadyclk_start = steady_clock::now();
    for (auto i = 0; i < 1000; ++i) { str.append(long_word_str); };
    steadyclk_end = steady_clock::now();
    std::cout << "string.append(str) process time: "
              << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
              << "us.\n";

    ss.str("");
    ss.clear();
    steadyclk_start = steady_clock::now();
    for (auto i = 0; i < 1000; ++i) { ss << long_word_char; };
    steadyclk_end = steady_clock::now();
    std::cout << "stream << char* operator process time: "
              << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
              << "us.\n";

    str.clear();
    steadyclk_start = steady_clock::now();
    for (auto i = 0; i < 1000; ++i) { str.append(long_word_char); };
    steadyclk_end = steady_clock::now();
    std::cout << "string.append(char*) process time: "
              << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
              << "us.\n";
}
