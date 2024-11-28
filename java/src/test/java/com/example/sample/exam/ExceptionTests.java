package com.example.sample.exam;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionTests {

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
