package com.example.sample.basic;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

public class StringTests extends EvaluatedTimeTests {

    String textblk = """
            line 1
                line 2""";

    @Test
    void stringTest() {
        System.out.println(textblk);
    }
}
