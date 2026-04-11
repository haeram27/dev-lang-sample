package com.example.sample.basic;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionTests {

    private static final Logger log = LoggerFactory.getLogger(ExceptionTests.class);

    @Test
    public void nestedTryCatch() {
        try { 
            try {
                System.out.println();
                throw new NoSuchFieldException("2");
            } catch (Exception e) {
                log.error("## catch2:", e);
                    throw new NoSuchFieldException("3");

            }
        } catch (Exception e) {
            log.error("## catch1: ", e);
        }
    }

}
