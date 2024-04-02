package com.example.sample.quiz;

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

        System.out.println(p);
    }
}
