#include <bits/stdc++.h>

#include "gtest/gtest.h"

using namespace std;

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

class PermutationTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    PermutationTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~PermutationTest() {
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


/*
 * remember
 * permutation()
 * nextpermvecr
 */

template <typename T>
void permutation(vector<T> data, vector<T> perm, vector<bool> visited, int depth, int r)
{
    if (depth == r) {
        for (int i = 0; i < r; i++) {
            cout << perm[i] << " ";
        }
        cout << endl;
        return;
    }

    for (int i = 0; i < data.size(); i++) {
        if (visited[i] != true) {
            visited[i] = true;
            perm[depth] = data[i];
            permutation(data, perm, visited, depth + 1, r);
            visited[i] = false;
        }
    }
}

TEST_F(PermutationTest, perm_char)
{STARTTIME

    int r = 3; // number of elements to select
    vector<char> data {'a', 'b', 'c', 'd'}; // element list
    vector<char> perm (r); // result of permutation
    vector<bool> visited (data.size()); // check selected or not

    std::cout << "\n[permutation#1]===================" << std::endl;
    permutation(data, perm, visited, 0, r); // 4P3

ENDTIME}

TEST_F(PermutationTest, perm_int)
{STARTTIME

    int r = 3;
    vector<int> data {1, 2, 3, 4};
    vector<int> perm (r);
    vector<bool> visited (data.size());

    std::cout << "\n[permutation#1]===================" << std::endl;
    permutation(data, perm, visited, 0, r); // 4P3

ENDTIME}


/*
*  data  vector has values
*  depth  index for swapping
*    previous depth index elements are fixed
*    all elements of depth and behind depth index should place in depth index
*  r  size of permutation
*/
template <typename T>
void permutation_swap(vector<T> data, uint depth, uint r)
{
    if (depth == r) {
        // print
        for (uint i = 0; i < r; i++)
            cout << data[i] << " ";
        cout << endl;

        return;
    }

    for(uint i = depth; i < data.size(); i++) {
        swap(data[depth], data[i]);   // swap
        // iter_swap(v.begin() + depth, v.begin() + i);
        permutation_swap(data, depth + 1, r);  // recursive
        swap(data[depth], data[i]);  // revert swap
    }
}


/*
next_permutation의 알고리즘의 원리는 다음과 같습니다:

뒤에서부터 탐색을 시작하여 오름차순의 형태를 띄는 구간이 나올 때까지 진행합니다. 이 때 작은 쪽을 i, 큰 쪽을 ii라고 하겠습니다.
다시 맨 뒤에서 탐색을 시작하여, i보다 큰 값이 나올 때까지 진행합니다. 멈추면, 그곳을 j라고 하겠습니다.
i와 j를 스왑합니다.
ii부터 마지막까지의 원소를 뒤집습니다.
 

1, 2, 3, 4, 5를 예시로 설명하면, 다음과 같습니다 :

1 2 3 4(i) 5(ii) 5에서 탐색을 시작하며 4,5가 오름차순임을 확인하고 4 위치가 i, 5 위치가 ii가 됩니다.
1 2 3 4(i) 5(ii)(j) 5에서 탐색을 시작하며 4보다 큰 5를 확인하고, 5 위치가 j가 됩니다.
1 2 3 5 4(ii) i와 j를 스왑하므로 4와 5가 바뀝니다.
1 2 3 5 4 ii부터 마지막 요소까지 뒤집지만 변화가 없습니다.
1 2 3(i) 5(ii) 4 4에서부터 탐색을 시작하여 3,5가 오름차순임을 확인하고 3 위치가 i, 5 위치가 ii가 됩니다.
1 2 3(i) 5(ii) 4(j) 4에서부터 탐색을 시작하여 3보다 큰 4를 확인하고, 4 위치가 j가 됩니다.
1 2 4 5(ii) 3 i와 j를 스왑하므로 3과 4가 바뀝니다.
1 2 4 3 5 ii부터 마지막 요소까지 뒤집어 3 5가 됩니다.

// pseudo next_permutation() :: https://en.cppreference.com/w/cpp/algorithm/next_permutation
template<class BidirIt>
bool next_permutation(BidirIt first, BidirIt last)
{
  auto r_first = std::make_reverse_iterator(last);
  auto r_last = std::make_reverse_iterator(first);
  auto left = std::is_sorted_until(r_first, r_last);
  if (left != r_last) {
    auto right = std::upper_bound(r_first, left, *left);
    std::iter_swap(left, right);
  }
  std::reverse(left.base(), last);
  return left != r_last;
}
*/

TEST_F(PermutationTest, nextpermstr)
{STARTTIME

    std::string s = "aba";

    // next_permutation must require ascending order
    std::sort(s.begin(), s.end());
    do {
        std::cout << s << endl;;
    } while(std::next_permutation(s.begin(), s.end()));

ENDTIME}

// resolve nPn using next_permutation()
TEST_F(PermutationTest, nextpermvec)
{STARTTIME

    vector<int> v = {4, 3, 2, 1}; // SHOULD NOT have duplicated element

    // next_permutation must require asceding order
    std::sort(v.begin(), v.end()); // sort ascending - use less() comparator

    do {
        for (const auto& e : v) {
            cout << e << " ";
        }
        cout << endl;
    } while (next_permutation(v.begin(), v.end()));

ENDTIME}


// resolve nPr using next_permutation()
TEST_F(PermutationTest, nextpermvecr)
{STARTTIME

    vector<int> v = {4, 3, 2, 1}; // SHOULD NOT have duplicated element
    int r = 2;

    // next_permutation must require asceding order
    std::sort(v.begin(), v.end()); // sort ascending - use less() comparator

    do {
        for (int i=0; i<r; i++) {
            cout << v[i] << " ";
        }
        cout << endl;
        reverse(v.begin() + r, v.end());
    } while (next_permutation(v.begin(), v.end()));

ENDTIME}


/*
template<class BidirIt>
bool prev_permutation(BidirIt first, BidirIt last)
{
    if (first == last) return false;
    BidirIt i = last;
    if (first == --i) return false;
 
    while (1) {
        BidirIt i1, i2;
 
        i1 = i;
        if (*i1 < *--i) {
            i2 = last;
            while (!(*--i2 < *i))
                ;
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
*/
TEST_F(PermutationTest, prevpermstr)
{STARTTIME

    std::string s="abc";  // SHOULD NOT have duplicated element

    // next_permutation must require descending order
    std::sort(s.begin(), s.end(), std::greater<>()); // sort descending - use greater()

    do {
        std::cout << s << ' ';
    } while(std::prev_permutation(s.begin(), s.end()));
    std::cout << '\n';

ENDTIME}


TEST_F(PermutationTest, prevpermvec)
{STARTTIME

    vector<int> v = {1, 2, 3}; // SHOULD NOT have duplicated element

    // prev_permutation must require descending order
    std::sort(v.begin(), v.end(), std::greater<>()); // sort descending - use greater()

    do {
        for (uint i = 0; i < v.size(); i++) {
            cout << v[i] << " ";
        }
        cout << '\n';

    } while (prev_permutation(v.begin(), v.end()));

ENDTIME}

} /// namespace
