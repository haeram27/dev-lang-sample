package com.example.sample.quiz.string;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LongestPalindromTests {

    /**
    * Quest:
    * find longest palindrom
    */
    private static Stream<Arguments> argSupplier() {
        // @formatter:off
        return Stream.of(
            Arguments.of("babad"),
            Arguments.of("cbba"));
        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    void logestPalindrom(String s) {

        String p = null;
        // TODO:

        System.out.println(p);
    }

    /* Answer */
    @ParameterizedTest
    @MethodSource("argSupplier")
    void logestPalindromA(String s) {
        String p = null;

        // TODO:
        int begin = 0, max = 0;
        int len = s.length();
        char[] a = s.toCharArray();

        for (int i = 0; i < len; i++) {
            int l, r;

            // find odd number palindrom
            l = i;
            r = i;
            while (l >= 0 && r < len && a[l] == a[r]) {
                l--;
                r++;
            }

            if (r - l - 1 > max) {
                max = r - l - 1;
                begin = l + 1;
            }

            // find even number palindrom
            l = i;
            r = i + 1;
            while (l >= 0 && r < len && a[l] == a[r]) {
                l--;
                r++;
            }

            if (r - l - 1 > max) {
                max = r - l - 1;
                begin = l + 1;
            }
        }

        p = s.substring(begin, begin + max);

        System.out.println(p);
    }

    int begin = 0;
    int end = 0;

    @ParameterizedTest
    @MethodSource("argSupplier")
    void logestPalindromB(String s) {
        if (s == null || s.length() < 2) {
            System.out.println(s);
            return;
        }

        char a[] = s.toCharArray();
        getPalindrome(a, 0);

        System.out.println(s.substring(begin, end + 1));
    }

    public void getPalindrome(char a[], int idx) {
        int l = idx, r = idx, len = a.length;
        if (idx > len) {
            return;
        }

        // same chars are palindrom
        while (r < len - 1 && a[r] == a[r + 1]) {
            r++;
        }
        idx = r;

        // find odd number palindrom from iniial idx
        while (l - 1 >= 0 && r + 1 < len && a[l - 1] == a[r + 1]) {
            l--;
            r++;
        }

        if (end - begin < r - l) {
            begin = l;
            end = r;
        }

        getPalindrome(a, idx + 1);
    }

}
