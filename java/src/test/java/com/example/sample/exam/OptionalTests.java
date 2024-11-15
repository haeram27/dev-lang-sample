package com.example.sample.exam;

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
}
