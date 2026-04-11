package com.example.sample;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloTests extends EvaluatedTimeTests {

    private static final Logger log = LoggerFactory.getLogger(HelloTests.class);

    @Test
    public void hello() {
        System.out.println("hello");
        log.info("hello");
    }
}
