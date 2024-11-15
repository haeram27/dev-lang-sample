#include <chrono>
#include <iostream>
#include <sstream>
#include <type_traits> 

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

class TypeTraitsTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TypeTraitsTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TypeTraitsTest() {
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
type traits 개념:
1. 컴파일 시간에 타입에 대한 정보를 얻거나 변형된 타입을 얻을 때 사용하는 도구 (메타함수)
2. <type_traits> 헤더로 제공 (C++11부터)

용도?
type traits란 어떤 type 특성을 말하는 것으로 배열, 포인터, 레퍼런스, void, integral 등과 같은 특수한 성질을 의미한다.
c++에서는 <type_traits> 헤더를 통하여 컴파일 타임에 특수화된 template의 type을 체크할 수 있도록 돕는 메타 합수를 제공한다. 

template을 사용하여 클래스나 함수를 구현하게 되면 코드 상에서는
template 타입 변수가 특수화 되었을 때 그 실제 타입이 무엇인지 알 수 없다.
만약 template 타입 변수의 특성(traits)에 따라 로직이 바뀌어야 하는 경우
컴파일러에 의해 특수화 되는 template 타입의 실제 특성(traits)을 체크하는 메타 함수를 조건문에서 사용한다.

아래는 type_traits의 구현 원리를 이해하기 위한 테스트 코드이다.
실제 메타 함수(struct)는 <type_traits> 헤더의 72개 함수들을 이용하라.
/// is_void
/// is_integral
/// is_floating_point
/// is_array
/// is_pointer
/// is_lvalue_reference
/// is_rvalue_reference
/// is_member_object_pointer
/// is_member_function_pointer
/// is_enum
/// is_union
/// is_class
/// is_function
/// is_null_pointer (LWG 2247).
/// is_reference
/// is_arithmetic
/// is_fundamental
/// is_object
/// is_scalar
/// is_compound
/// is_member_pointer
/// is_const
/// is_volatile
/// is_trivial
/// is_standard_layout
/// is_pod
/// is_literal_type
/// is_empty
/// is_polymorphic
/// is_final
/// is_abstract
/// is_signed
/// is_unsigned
/// is_destructible
/// is_nothrow_destructible
/// is_constructible
/// is_default_constructible
/// is_copy_constructible
/// is_move_constructible
/// is_nothrow_constructible
/// is_nothrow_default_constructible
/// is_nothrow_copy_constructible
/// is_nothrow_move_constructible
/// is_assignable
/// is_copy_assignable
/// is_move_assignable
/// is_nothrow_assignable
/// is_nothrow_copy_assignable
/// is_nothrow_move_assignable
/// is_trivially_constructible
/// is_trivially_default_constructible
/// is_trivially_copy_constructible
/// is_trivially_move_constructible
/// is_trivially_assignable
/// is_trivially_copy_assignable
/// is_trivially_move_assignable
/// is_trivially_destructible
/// is_same
/// is_base_of
/// is_convertible
/// is_nothrow_convertible
/// is_nothrow_convertible_v
/// is_swappable
/// is_nothrow_swappable
/// is_swappable_v
/// is_nothrow_swappable_v
/// is_swappable_with
/// is_nothrow_swappable_with
/// is_swappable_with_v
/// is_nothrow_swappable_with_v
/// is_aggregate
/// is_aggregate_v
​*/


template<typename T> void printv(T v)
{
    std::cout << v << std::endl;
}

TEST_F(TypeTraitsTest, printv)
{STARTTIME

    int n = 7;
    double pi = 3.141591;

    printv(n);   // 7
    printv(pi);  // 3.141591
    printv(&n);  // 0x7ffcecd31884   pointer??

ENDTIME}


// Primary template
template<typename T> struct is_pointer
{
    enum { value = false };
    // static constexpr bool value = false; // also ok
};

// Partial specialization
template<typename T> struct is_pointer<T*>
{
    enum { value = true };
    // static constexpr bool value = true; // also ok
};

// Partial specialization
template<typename T> struct is_pointer<T* const>
{
    enum { value = true };
    // static constexpr bool value = true; // also ok
};

// Partial specialization
template<typename T> struct is_pointer<T* volatile>
{
    enum { value = true };
    // static constexpr bool value = true; // also ok
};

// Partial specialization
template<typename T> struct is_pointer<T* const volatile>
{
    enum { value = true };
    // static constexpr bool value = true; // also ok
};

template<typename T> void isptr(T p)
{
    /*
      is_pointer<T>::value  is meta-function... retrieve enum constant "value" from template
      // T will be confirm at compile time so compiler will make real code with T at comiletime.
      If type T of parameter v is pointer then 
      (is_pointer<T>::value) will be true by partial specialization of struct is_pointer<T*> at compile time
    */
    if (is_pointer<T>::value) {
        std::cout << "Pointer!!" << std::endl;
    } else {
        std::cout << "NOT Pointer!!" << std::endl;
    }
}

TEST_F(TypeTraitsTest, isptr)
{STARTTIME

    int n = 7;

    isptr(n);   // NOT Pointer!!
    isptr(&n);  // Pointer!!

ENDTIME}



// Primary template
/*
  is_array::value will be "false" when T is NOT array type,
  because if T is array then "is_array<T[N]>" partial specialization is priority mached target.
*/
template<typename T> struct is_array
{
    static constexpr bool value = false;
    static constexpr size_t size = 0;
};

// Partial specialization
/*
  is_array::value will be "true" when only T is array type
  because if T is array then "is_array<T[N]>" partial specialization is priority mached target.
*/
template<typename T, size_t N> struct is_array<T[N]>
{
    static constexpr bool value = true;
    static constexpr size_t size = N;
};

template<typename T> void isarr(T& a)
{
    // T will be confirm at compile time so compiler will make real code with T.
    if (is_array<T>::value) {
        std::cout << "Array!!  " << is_array<T>::size << std::endl;
    } else {
        std::cout << "NOT Array!!" << std::endl;
    }
}

TEST_F(TypeTraitsTest, isarr)
{STARTTIME

    int arr3[] = {1,2,3};
    isarr(arr3);   // Array!!  3

    int arr5[5] = {1,2,3,4,5};
    isarr(arr5);  // Array!!  5

ENDTIME}

} ///namespace
