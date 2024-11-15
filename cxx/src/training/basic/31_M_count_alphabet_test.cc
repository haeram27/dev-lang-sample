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

class CountAlphabetTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    CountAlphabetTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~CountAlphabetTest() {
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

/*
    1. create map from vector by counting elements of vector
    2. sorting map by value of pair
    3. makesure diff of most frequent char and least frequent char
*/

TEST_F(CountAlphabetTest, run)
{STARTTIME

    // expected answer is 4 = 5(a) - 1(e)
    std::string s {"aaaaabbbbcccdde"};
    // printv(s);


ENDTIME}


TEST_F(CountAlphabetTest, runa)
{STARTTIME

    using namespace std;
    using pair_t = pair<char, int>;

#if 1
    string s {"aaaaabbbbcccdde"};
#else
    string s {"a"};
#endif

    std::unordered_map<char, int> m;

    for (const auto& c : s) {
        auto it = m.find(c);
        if (it == m.end()) {
            m.emplace(c, 1);
        } else {
            it->second += 1;
        }
    }

    printmap(m, "debug");

    std::vector<pair_t> v{m.begin(), m.end()};

    // sort pairs descending by value user lambda greater against pair.second
    std::sort(v.begin(), v.end(), [](
        const pair_t& e1,
        const pair_t& e2 ){
            return e1.second > e2.second;
        });

    printpairv(v, "debug");

    std::cout << "diff most frequency char and least frequency char ------------------ " << std::endl;
    // most frequency char
    std::cout << v.front().first << ":" << v.front().second << std::endl;
    // least frequency char
    std::cout << v.back().first << ":" << v.back().second << std::endl;
    std::cout << v.front().second - v.back().second << std::endl;

ENDTIME}

} ///namespace
