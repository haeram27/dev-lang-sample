package com.example.sample.base.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class LinkedHashMapTests {

    @Test
    public void run() {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");

        map.forEach((k, v) -> {
            System.out.println("k::" + k);
            System.out.println("v::" + v);
        });
        System.out.println("-".repeat(10));

        map.entrySet().forEach(e -> {
            System.out.println("k::" + e.getKey());
            System.out.println("v::" + e.getValue());
        });
        System.out.println("-".repeat(10));

        map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(e -> {
            System.out.println("k::" + e.getKey());
            System.out.println("v::" + e.getValue());
        });
        System.out.println("-".repeat(10));

        map.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).forEach(e -> {
            System.out.println("k::" + e.getKey());
            System.out.println("v::" + e.getValue());
        });
        System.out.println("-".repeat(10));

        map.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).collect(Collectors.toList())
                .forEach(e -> {
                    System.out.println("k::" + e.getKey());
                    System.out.println("v::" + e.getValue());
                });
        System.out.println("-".repeat(10));

        var list = new ArrayList<Map.Entry<String, String>>();
        for (var e : map.entrySet()) {
            list.add(e);
        }
        list.forEach(e -> {
            System.out.println("k::" + e.getKey());
            System.out.println("v::" + e.getValue());
        });
        System.out.println("-".repeat(10));
    }
}
