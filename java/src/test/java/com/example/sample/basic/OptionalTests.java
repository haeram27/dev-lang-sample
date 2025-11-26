package com.example.sample.basic;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalTests {
    @Test
    public void run() {
        String nonNullStr = "hello";
        String nullStr = null;
        Optional.ofNullable(nullStr).ifPresentOrElse(e -> {
            System.out.println(e);
        }, () -> {
            System.out.println("null");
        });

        Optional.ofNullable(nonNullStr).ifPresentOrElse(e -> {
            System.out.println(e);
        }, () -> {
            System.out.println("null");
        });

        Optional.of("1").map(Integer::valueOf).ifPresentOrElse(e -> {
            System.out.println(e);
        }, () -> {
            System.out.println("null");
        });

        Optional.of("result").map(e -> 1).ifPresentOrElse(e -> {
            System.out.println(e);
        }, () -> {
            System.out.println("null");
        });

        Optional.of("result").flatMap((val) -> Optional.ofNullable(1)).ifPresentOrElse(e -> {
            System.out.println(e);
        }, () -> {
            System.out.println("null");
        });
    }

    @Test
    public void validateObjectTypeAndGetWithCastTest() {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("string_type", "str");
        paramMap.put("boolean_type", true);

        var strObj = Optional.ofNullable(paramMap.get("string_type"))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .orElse("");

        var boolObj = Optional.ofNullable(paramMap.get("boolean_type"))
                    .filter(Boolean.class::isInstance)
                    .map(Boolean.class::cast)
                    .orElse(false);

        System.out.println(strObj);
        System.out.println(boolObj);
    }
}
