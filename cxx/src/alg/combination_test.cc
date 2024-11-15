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

class CombinationTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    CombinationTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~CombinationTest() {
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

using namespace std;

void combination_recur_0(int data[], bool comb[], int n, int r, int depth) {
    // print current combination
    if (depth == r) {
        for (int i = 0; i < n; i++ ) {
            if (comb[i] == true) {
                cout << data[i] << " ";
            }
        }
        cout << endl;
        return;
    }

    for (int i = 0; i < n; i++ ) {
        
    }
}

void combination_recur_1(vector<char> data, vector<char> comb, int r, int index, uint depth) {
    if (r == 0)
    {
        for (uint i = 0; i < comb.size(); i++)
            cout << comb[i] << " ";
        cout << endl;
    }
    else if (depth == data.size())  // depth == n // 계속 안뽑다가 r 개를 채우지 못한 경우는 이 곳에 걸려야 한다.
    {
        return;
    }
    else
    {
        // data[depth] 를 뽑은 경우
        comb[index] = data[depth];
        combination_recur_1(data, comb, r - 1, index + 1, depth + 1);
        
        // data[depth] 를 뽑지 않은 경우
        combination_recur_1(data, comb, r, index, depth + 1);
    }
}

void combination_recur_2(vector<char> data, vector<char> comb, int index, uint depth) {
    if (depth == comb.size())
    {
        for (uint i = 0; i < comb.size(); i++)
            cout << comb[i] << " ";
        cout << endl;
        
        return;
    }
    else
    {
        for (uint i = index; i < data.size(); i++)
        {
            comb[depth] = data[i];
            combination_recur_2(data, comb, i + 1, depth + 1);
        }
    }
}


void combination_recur_3(vector<pair<char, bool>> data, vector<char> comb, int index, uint depth) {
    if (depth == comb.size()) {
        for (uint i = 0; i < data.size(); i++)
            if (data[i].second)
                cout << data[i].first << " ";
        cout << endl;
        
        return;
    } else {
        for (uint i = index; i < data.size(); i++) {
            if (data[i].second == true) {
                continue;
            } else {
                data[i].second = true;
                comb[depth] = data[i].first;
                combination_recur_3(data, comb, i + 1, depth + 1);
                data[i].second = false;
            }
        }
    }
}

/***** TEST UNIT CONTEXT UNDER HERE *****/

TEST_F(CombinationTest, recur)
{STARTTIME

    vector<char> vec = {'a', 'b', 'c', 'd', 'e'};  // n = 5
    int r = 3;
    vector<char> comb(r);
    
    std::cout << "\n[combination#1]===================" << std::endl;
    combination_recur_1(vec, comb, r, 0, 0);

    std::cout << "\n[combination#2]===================" << std::endl;
    combination_recur_2(vec, comb, 0, 0);

    std::cout << "\n[combination#3]===================" << std::endl;
    vector<pair<char, bool>> check(vec.size());
    for (uint i = 0; i < check.size(); i++)
        check[i] = make_pair(vec[i], false);
    combination_recur_3(check, comb, 0, 0);
 
ENDTIME}



/*
template<class BidirIt>
bool pseudo_next_permutation(BidirIt first, BidirIt last)
{
    if (first == last) return false;
    BidirIt i = last;
    if (first == --i) return false;
 
    while (true) {
        BidirIt i1, i2;
 
        i1 = i;
        if (*--i < *i1) {
            i2 = last;
            while (!(*i < *--i2));
            std::iter_swap(i, i2);
            std::reverse(i1, last);
            return true;
        }
        if (i == first) {
            std::reverse(first, last);
            return false;
        }
    }
}


알고리즘:
맨 뒤에서부터 인접한 두개의 수를 비교한다. (뒤에서부터 내림차순이 아닌 위치를 찾기 위함)
앞의 수(A)가 작을 경우 비교를 멈춘다. (A 뒤에 있는 수들은 내림차순으로 정렬 되어있는 상태)
맨 뒤에서부터 A와 비교하여 A보다 커지는 지점(B)을 찾는다.
A와 B를 스왑한다. (여전히 뒤의 수들은 내림차순)
뒤의 수들을 반전시킨다. (내림차순에서 오름차순으로 만든다)
*/

TEST_F(CombinationTest, nextperm1)
{STARTTIME

    const int r = 4;

    vector<char> data{'a', 'b', 'c', 'd', 'e'};
    vector<bool> temp(data.size(), true);
    for (uint i = 0; i < data.size() - r; i ++) // 뒤에 false가 n-r개 채워지고 뒤에 true 가 r개 채워진다.
        temp[i] = false;
 
    do {
        for (uint i = 0; i < data.size(); ++i) {
            if (temp[i]) 
                cout << data[i] << " ";
        }
        cout << endl;
    } while (next_permutation(temp.begin(), temp.end()));

ENDTIME}


TEST_F(CombinationTest, nextperm2)
{STARTTIME

    const int r = 4;

    vector<char> data{'a', 'b', 'c', 'd', 'e'};
    vector<bool> temp(data.size(), true);
    for (uint i = 0; i < r; i ++)
        temp[i] = false;

    do {
        for (uint i = 0; i < data.size(); ++i) {
            if (temp[i] == false)
                cout << data[i] << " ";
        }
        cout << endl;
    } while (next_permutation(temp.begin(), temp.end()));

ENDTIME}


TEST_F(CombinationTest, prevperm)
{STARTTIME

    const int r = 4;

    vector<char> data{'a', 'b', 'c', 'd', 'e'};
    vector<bool> temp(data.size(), false);
    for (uint i = 0; i < r; i ++) // 앞부터 r개의 true가 채워진다. 나머지 뒤는 false.
        temp[i] = true;
 
    do {
        for (uint i = 0; i < data.size(); ++i) {
            if (temp[i])
                cout << data[i] << ' ';
        }
        cout << endl;
    } while (prev_permutation(temp.begin(), temp.end()));

ENDTIME}


} ///namespace
