package com.example.sample;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloTests extends EvaluatedTimeTests {

    @Test
    public void hello() {
        System.out.println("hello");
    }

    @Test void printExceptionStackTrace() {

        String str = " 1111 - 2222 ";
        var a = str.split("-");
        System.out.println("["+a[0]+"]");
        System.out.println("["+a[1]+"]");
    }
}
