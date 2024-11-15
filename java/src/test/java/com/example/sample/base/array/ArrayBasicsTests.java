package com.example.sample.base.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ArrayBasicsTests {
    /**
        ## antipode(opposite position) of index
            antipode = len-1-i
    
        ## length(number of element) from left index(lo) to right index(hi)
            len = hi-lo+1
    
        ## mid index between two index
            mid = (hi+lo+1)/2
    
        ## mid index of entire array
            mid = len/2
    */

    /**
    * Quest:
    * primitive array to List
    */
    @Test
    void primitiveArrayToList() {
        int a[] = {1, 2, 3, 4, 5};
        // TODO:
        var list = new ArrayList<>();

        list.forEach(e -> System.out.print(e + " "));
        System.out.println();

        Collections.reverse(list);
        list.forEach(e -> System.out.print(e + " "));
    }

    @Test
    void primitiveArrayToListA() {
        int a[] = {1, 2, 3, 4, 5};

        var l1 = Arrays.stream(a).boxed().collect(Collectors.toList());
        l1.forEach(e -> System.out.print(e + " "));
        System.out.println();

        Collections.reverse(l1);
        l1.forEach(e -> System.out.print(e + " "));
        System.out.println();

        // legarcy  boxing
        var l2 = new ArrayList<Integer>();
        for (var e : a)
            l2.add(e);
        l2.forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    /**
    * Quest:
    * copy single dimension array
    */
    int[] arrayCopy(int[] a) {
        // TODO:

        return new int[a.length];
    }

    @Test
    public void arrayCopyTest() {
        long start = System.nanoTime();

        int[] a = {1, 2, 3, 4, 5};
        var b = arrayCopy(a);
        System.out.println(Arrays.toString(b));
        System.out.println(a);
        System.out.println(b);

        System.out.println(String.format("Process time: %d nsec", System.nanoTime() - start));
    }

    int[] arrayCopyA(int[] a) {
        return a.clone();
    }

    int[] arrayCopyB(int[] a) {
        // Arrays.copyOf() only copy 1st dimension(row)
        var b = Arrays.copyOf(a, a.length);
        return b;
    }

    int[] arrayCopyC(int[] a) {
        var b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        return b;
    }

    /**
     * Quest:
     * reverse one dimensional array
     *    1  2  3   >>>   3  2  1
     */

    @Test
    public void reverseArrayTest() {
        int[] a = {1, 2, 3, 4, 5};
        // TODO:

        System.out.println(Arrays.toString(a));
    }

    @Test
    void reverseArrayA() {
        int[] a = {1, 2, 3, 4, 5};
        int len = a.length;

        // just swap item 0 with item n-1, 1 with n-2, ...
        // begin index of behind half array = len/2 = last-index+1/2
        for (int i = 0; i < len / 2; ++i) {
            // swap
            int temp = a[i];
            a[i] = a[len - 1 - i];
            a[len - 1 - i] = temp;
        }

        System.out.println(Arrays.toString(a));
    }

    @Test
    void reverseArrayB() {
        int[] a = {1, 2, 3, 4, 5};
        int len = a.length;

        // just swap item 0 with item last-1, 1 with last-2, ...
        for (int l = 0, r = len - 1; l < r; l++, r--) {
            // swap
            int temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }

        System.out.println(Arrays.toString(a));
    }

    @Test
    void reverseArrayD() {
        int[] a = {1, 2, 3, 4, 5};

        var l = Arrays.stream(a).boxed().collect(Collectors.toList());
        Collections.reverse(l);
        l.forEach(e -> System.out.print(e + " "));
    }

    /**
    * Quest:
    * sort array reverse
    */
    @Test
    void arraySortTest() {
        int[] a = {5, 3, 2, 4, 1};
        // TODO:

        System.out.println(Arrays.toString(a));
    }

    @Test
    public void arraySortTestA() {
        int[] a = {5, 3, 2, 4, 1};
        Arrays.sort(a);

        System.out.println(Arrays.toString(a));
    }

    /**
    * Quest:
    * sort array reverse
    */
    @Test
    void arrayReverseSortTest() {
        int[] a = {5, 3, 2, 4, 1};
        int[] b = null;
        // TODO:

        System.out.println(Arrays.toString(b));
    }

    @Test
    public void arrayReverseSortTestA() {
        int[] a = {5, 3, 2, 4, 1};
        int[] b = Arrays.stream(a).boxed().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();

        System.out.println(Arrays.toString(b));
    }

    @Test
    public void arrayReverseSortTestB() {
        int[] a = {5, 3, 2, 4, 1};
        Arrays.sort(a);
        for (int i = 0; i < a.length / 2; i++) {
            int t = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = t;
        }

        System.out.println(Arrays.toString(a));
    }

    /**
    * Quest:
    * find all sub set index of given array length
    */
    private static Stream<Arguments> provideLength() {
        // @formatter:off
        return Stream.of(
            Arguments.of(3),  // AllSubSet(6), Comb(3)
            Arguments.of(4),  // AllSubSet(10), Comb(6)
            Arguments.of(5),  // AllSubSet(15), Comb(10)
            Arguments.of(6),  // AllSubSet(21), Comb(15)
            Arguments.of(7)); // AllSubSet(28), Comb(21)
        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("provideLength")
    void allSubSet(int len) {
        int count = 0;
        int s, e; // e is not included in subset(substring)

        System.out.println(count);
    }

    /* Answer */
    @ParameterizedTest
    @MethodSource("provideLength")
    void allSubSetA(int len) {
        int count = 0;

        // [s, e) = {s <= x < e}
        // s is opened(NOT include) index
        for (int s = 0; s < len; ++s) {
            // e is closed(NOT include) index
            for (int e = s + 1; e < len + 1; ++e) {
                System.out.println(String.format("%d, %d", s, e));
                count++;
            }
        }

        System.out.println(count);
    }

    @ParameterizedTest
    @MethodSource("provideLength")
    void twoIndexCombination(int len) {
        int count = 0;
        // TODO:

        System.out.println(count);
    }

    /* Answer */
    @ParameterizedTest
    @MethodSource("provideLength")
    void twoIndexCombinationA(int len) {
        int count = 0;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                System.out.println(String.format("%d, %d", i, j));
                count++;
            }
        }

        System.out.println(count);
    }

    /**
    * Quest:
    * merge two sorted array into sorted single array 
    */
    @Test
    void mergeTwoSortedArrays() {
        int a[] = {1, 3, 5};
        int b[] = {2, 4, 6};
        // TODO:

        //System.out.println(Arrays.toString(c));
    }

    @Test
    void mergeTwoSortedArraysA() {
        int a[] = {1, 3, 5};
        int b[] = {2, 4, 6};
        // TODO:
        var alen = a.length;
        var blen = b.length;
        int[] c = new int[alen + blen];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            c[k++] = (a[i] < b[j]) ? a[i++] : b[j++];
        }

        while (i < a.length) {
            c[k++] = a[i++];
        }

        while (j < b.length) {
            c[k++] = b[j++];
        }

        System.out.println(Arrays.toString(c));
    }

}
