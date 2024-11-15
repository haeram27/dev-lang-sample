package com.example.sample.base.ds;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ArrayListTests {
    @Test
    public void run() {
        // #### modifiable, flexible length
        // new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));

        // var list = new ArrayList<Integer>();
        // list.add(1);

        var list = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
        list.add(5);
        list.set(2, 7);
        list.forEach(e -> System.out.println(e));

        // #### unmodifiable
        // Arrays.asList(1, 2, 3);       // fixed length == add() X
        // Stream.of(1, 2, 3).toList();  // unmodifiable == add(), set() X
        // Collections.emptyList();      // unmodifiable  == add(), set() X
        // Collections.singletonList();  // unmodifiable  == add(), set() X
    }
}
