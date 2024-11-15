#include <chrono>
#include <iostream>
#include <sstream>
#include <algorithm>

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

class StlAlgoTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    StlAlgoTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~StlAlgoTest() {
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
 ## <algorithmfwd.h>

    adjacent_find
    all_of (C++11)
    any_of (C++11)
    binary_search
    clamp (C++17)
    copy
    copy_backward
    copy_if (C++11)
    copy_n (C++11)
    count
    count_if
    equal
    equal_range
    fill
    fill_n
    find
    find_end
    find_first_of
    find_if
    find_if_not (C++11)
    for_each
    generate
    generate_n
    includes
    inplace_merge
    is_heap (C++11)
    is_heap_until (C++11)
    is_partitioned (C++11)
    is_sorted (C++11)
    is_sorted_until (C++11)
    iter_swap
    lexicographical_compare
    lower_bound
    make_heap
    max
    max_element
    merge
    min
    min_element
    minmax (C++11)
    minmax_element (C++11)
    mismatch
    next_permutation
    none_of (C++11)
    nth_element
    partial_sort
    partial_sort_copy
    partition
    partition_copy (C++11)
    partition_point (C++11)
    pop_heap
    prev_permutation
    push_heap
    random_shuffle
    remove
    remove_copy
    remove_copy_if
    remove_if
    replace
    replace_copy
    replace_copy_if
    replace_if
    reverse
    reverse_copy
    rotate
    rotate_copy
    search
    search_n
    set_difference
    set_intersection
    set_symmetric_difference
    set_union
    shuffle (C++11)
    sort
    sort_heap
    stable_partition
    stable_sort
    swap
    swap_ranges
    transform
    unique
    unique_copy
    upper_bound
  */


TEST_F(StlAlgoTest, test1)
{STARTTIME

    for (auto id = 0; id < 10; ++id) {
        std::cout << "[" << id << "]" << std::endl;
    }

ENDTIME}

} ///namespace
