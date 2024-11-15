#include <bits/stdc++.h>

#include "gtest/gtest.h"


namespace {

/***** DO NOT REMOVE *****/
#define STARTTIME ElapsedTime et;
#define ENDTIME et.elapsed();
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

class StringTokenizerTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StringTokenizerTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StringTokenizerTest() {
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

using namespace std;


/* decompose string to token into vector using string stream*/
TEST_F(StringTokenizerTest, run)
{STARTTIME

    string s {"fail liaf ifal buy yub uyb abyss"};
    stringstream ss{s};

ENDTIME}


/***** SAMPLE and STUDY UNDER HERE *****/

TEST_F(StringTokenizerTest, streamop)
{STARTTIME

    string s {"fail liaf ifal buy yub uyb abyss"};
    istringstream iss {s};
    string token;
    vector<string> tokens;

    while (iss >> token) {
        tokens.push_back(token);
    }

    for (const auto& e : tokens) {
        cout << e << endl;
    }

ENDTIME}

TEST_F(StringTokenizerTest, getline)
{STARTTIME
    auto printv = [](const auto& v) {
    for (const auto& e : v) {
        cout << e << endl;
    }
    cout << endl;
    };

    string s{"fail liaf ifal buy yub uyb abyss"};
    istringstream iss{s};
    string token;
    vector<string> tokens;

    while (getline(iss, token, ' ')) {
        tokens.push_back(token);
    }
    printv(tokens);
    tokens.clear();

    /* !! getline CANNOT HANDLE repeated delimiter*/
    string s2{"a   b  c"};
    istringstream iss2{s2};
    while (getline(iss2, token, ' ')) {
        tokens.push_back(token);
    }
    printv(tokens);

ENDTIME}

struct manip_classic_mask : std::ctype<char> {
manip_classic_mask() : std::ctype<char>(get_table()) {}

static std::ctype_base::mask const* get_table() {
        typedef std::ctype<char> cctype;
        static const cctype::mask* const_rc = cctype::classic_table();
        static cctype::mask rc[cctype::table_size];

        std::memcpy(rc, const_rc, cctype::table_size * sizeof(cctype::mask));
        rc[':'] = std::ctype_base::space;  // colon is also treated as whitespace

        return &rc[0];
    }
};

struct new_mask : std::ctype<char> {
new_mask() : std::ctype<char>(get_table()) {}

static std::ctype_base::mask const* get_table() {
        typedef std::ctype<char> cctype;
        static cctype::mask rc[cctype::table_size];

        rc[' '] = std::ctype_base::space;
        rc['\t'] = std::ctype_base::space;
        rc['\n'] = std::ctype_base::space;
        rc[':'] = std::ctype_base::space;  // colon is also treated as whitespace

        return &rc[0];
    }
};

TEST_F(StringTokenizerTest, changedelim)
{STARTTIME
    string token;

    istringstream iss {"aa:bb cc:dd ee,ff"};
    iss.imbue(locale(cin.getloc(), new new_mask));
    while(iss >> token) {
        std::cout << token << ", ";
    }
    cout << endl;

    istringstream iss2{"11:22 33:44 55,66"};
    iss2.imbue(locale(cin.getloc(), new manip_classic_mask));
    while (iss2 >> token) {
        std::cout << token << ", ";
    }
    cout << endl;

    //   std::cin.imbue(locale(cin.getloc(), new new_mask));
    //   while(cin >> token) {
    //     std::cout << token << "\n";
    //   }

ENDTIME}

} /// namespace
