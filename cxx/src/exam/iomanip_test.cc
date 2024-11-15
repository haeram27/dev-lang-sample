#include <cstdlib>
#include <cstring>

#include <iomanip>
#include <iostream>
#include <string>
#include <vector>

#include "gtest/gtest.h"

#if 1
#include "util/string.h"
using namespace util::string;
#else
template<typename TInputIter>
std::string make_hex_str(TInputIter first, TInputIter last, bool use_uppercase=false, bool insert_spaces=true)
{
    std::ostringstream ss;
    ss << std::hex << std::setfill('0');
    if (use_uppercase) ss << std::uppercase;

    while (first != last)
    {
        ss << std::setw(2) << static_cast<uint32_t>(*first++);
        if (insert_spaces && first != last) ss << " ";
    }
    return ss.str();
}

std::string make_hex_str(const uint8_t* buf, const uint32_t size, bool use_uppercase=false, bool insert_spaces=true)
{
    // setw() : 최소 width 지정
    // setfill() : (빈위치에 삽입될)채움문자 지정.

    std::ostringstream ss;
    ss << std::hex << std::setfill('0');
    if (use_uppercase) ss << std::uppercase;

    for (size_t i = 0; i < size; ++i)
    {
        ss << std::setw(2) << static_cast<int>(buf[i]);//(*buf++);
        if (insert_spaces) ss << " ";
    }
    return ss.str();
}
#endif

typedef struct strstruct {
    std::string a;
} STRST;

typedef struct bytestruct {
    char version[256];
} BYTEST;


void iomanexam()
{

    double x=3.1415;
    int i=16;

//    std::system("clear");
    std::cout << x << std::endl;
    std::cout << std::setw(10) << std::setfill('0') << x << std::endl;
    std::cout << std::setprecision(3) << x << std::endl;
    std::cout << "dec= " << std::setbase(10) << i << std::endl;
    std::cout << "oct= " << std::setbase(8) << i << std::endl;
    std::cout << "hex= " << std::setbase(16) << i << std::endl;

    std::cout << std::endl << "from_array = " << std::endl;
    uint8_t byte_array[] = { 0xDE, 0xAD, 0xC0, 0xDE, 0x00, 0xFF };
    auto from_array = make_hex_str(std::begin(byte_array), std::end(byte_array), true, true);
    std::cout << from_array << std::endl;

    std::cout << std::endl << "from_vector = " << std::endl;
    // fill with values from the array above
    std::vector<uint8_t> byte_vector(std::begin(byte_array), std::end(byte_array));
    auto from_vector = make_hex_str(byte_vector.begin(), byte_vector.end());
    std::cout << from_vector << std::endl;

    std::clog << std::endl << "st1hstr = " << std::endl;
    STRST st1 {}; st1.a = "hello";
    auto st1hstr = make_hex_str((const uint8_t*)&st1, sizeof(st1));
    std::cout << st1hstr << std::endl;

    std::clog << std::endl << "st2hstr = " << std::endl;
    STRST st2 {}; st2.a = "hello";
    auto st2hstr = make_hex_str((const uint8_t*)&st2, sizeof(st2));
    std::cout << st2hstr << std::endl;

    std::clog << std::endl << "by1hstr = " << std::endl;
    BYTEST by1 {}; memset(by1.version, 0x00, sizeof(by1.version)); strcpy(by1.version, "hello");
    auto by1hstr = make_hex_str((const uint8_t*)&by1, sizeof(by1));
    std::cout << by1hstr << std::endl;

    std::clog << std::endl << "by2hstr = " << std::endl;
    BYTEST by2 {}; memset(by2.version, 0x00, sizeof(by2.version)); strcpy(by2.version, "hello");
    auto by2hstr = make_hex_str((const uint8_t*)&by2, sizeof(by2));
    std::cout << by2hstr << std::endl;

    std::clog << std::endl << "by3hstr = " << std::endl;
    auto by3hstr = make_hex_str(byte_array, sizeof(byte_array));
    std::cout << by3hstr << std::endl;
}


class IomanipTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    IomanipTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~IomanipTest() {
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


TEST_F(IomanipTest, iomaniptest) {
    iomanexam();
}


