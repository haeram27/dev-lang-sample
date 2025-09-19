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

        // stderr
        try {
            throw new RuntimeException("asdf");
        } catch (Exception e) {
            log.debug("## stderr: ");
            e.printStackTrace();
        }

        // logger
        try {
            throw new RuntimeException("asdf");
        } catch (Exception e) {
            log.error("## Error: ", e);
        }
    }
}
