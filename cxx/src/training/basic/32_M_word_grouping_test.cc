#include <bits/stdc++.h>
#include <string>

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

class WordGroupTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    WordGroupTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~WordGroupTest() {
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
void printstrv(const std::vector<std::string>& v) {
    for (uint i=0; i<v.size(); i++) {
        std::cout << i + ": " << v[i] << std::endl;
    }
}

template <typename T> 
void printv(const T& v) {
    for (const auto& e : v) {
        std::cout << e << " ";
    }
    std::cout << std::endl;
}

template <typename K, typename V> 
void printpairv(const std::vector<std::pair<K, V>>& v, const std::string& title) {
    std::cout << title << " ---------------------" << std::endl;
    std::for_each(v.begin(), v.end(), [](const std::pair<K, V> e){
        std::cout << e.first << " " << e.second << std::endl;
    });
}

template <typename K, typename V> 
void printmap(const std::map<K, V>& m, const std::string& title) {
    std::cout << title << " ---------------------" << std::endl;
    std::for_each(m.begin(), m.end(), [](const std::pair<K, V> e){
        std::cout << e.first << " " << e.second << std::endl;
    });
}

template <typename K, typename V> 
void printmap(const std::unordered_map<K, V>& m, const std::string& title) {
    std::cout << title << " ---------------------" << std::endl;
    std::for_each(m.begin(), m.end(), [](const std::pair<K, V> e){
        std::cout << e.first << " " << e.second << std::endl;
    });
}


TEST_F(WordGroupTest, run)
{STARTTIME

    std::string s {"final fnial fanil proxy pxory abyss"};
    /*
        make group of token consist of same alphabets
        and print each group in each line

        output for s1 ::
        final fnial fanil
        proxy pxory
        abyss
    */


ENDTIME}


TEST_F(WordGroupTest, runa)
{STARTTIME
    using namespace std;

#if 1
    string s {"final fnial fanil proxy pxory abyss"};
    /* 
        make group of token consist of same alphabets
        and print each group in each line
        
        output for s1 ::
        final fnial fanil
        proxy pxory
        abyss
    */

    string s2 {"fired gainly dreads listen silent fried"};
    /* 
        make group of token consist of same alphabets
        and print each group in each line

        output for s2 ::
        listen silent
        fired fried
        gainly
        dreads
    */
#else
    string s;
    cout << "please input words ... " << endl;
    getline(cin, s);  // #include <string>
    cout << "words: " << s << endl;
#endif

    stringstream ss {s};
    string token;
    unordered_map<string, vector<string>> m;

    /*
        In "while(ss >> token)", "ss >> token" operation returns bool type through [stringstream object -> ios::void* () -> bool] steps
        If stringstream ss has fail and bad bit as OFF then "ss >> token" operation is dealt with as bool by NULL(false) or NOT NULL(true)

        https://modoocode.com/213
        stringstream operator>> returns istream&
        istream class is derived from ios class and ios class has ios::operator void* ()
        ios::void* () can be NULL when failbit or badbit is ON
        NULL is dealt with as false on bool in c++
    */
    while(ss >> token) {
        string k {token};
        sort(k.begin(), k.end(), less());

        auto it = m.find(k);
        if (it == m.end()) {
            m.emplace(k, vector<string>{token});
        } else {
            it->second.push_back(token);
        }
    }

    for (const auto& v : m) {
        for (const auto& t : v.second) {
            cout << t << " ";
        }
        cout << endl;
    }


ENDTIME}

} ///namespace
