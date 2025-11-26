package com.example.sample.algo.ds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class StreamCopyCollectionTests {

    /* If Using Collectors with Stream to copy Collections then it is possible making MUTABLE copy */
    @Test
    public void copyListTest() {
        List<Integer> list = List.of(4, 3, 2, 1);

        List<Integer> copyImmutableUsingList = List.copyOf(list);
        // copyImmutableUsingList.set(0, 5); // runtime-error

        List<Integer> copyImmutable = list.stream().toList();
        // copyImmutable.set(0, 5); // runtime-error

        List<Integer> copyMutable = list.stream().collect(Collectors.toList());
        copyMutable.set(0, 5); // mutable

        System.out.println(list);
        System.out.println(copyImmutableUsingList);
        System.out.println(copyImmutable);
        System.out.println(copyMutable);
    }

    @Test
    public void copyMapTest() {
        Map<String, Integer> map = new HashMap<>();
        map.put("k1", 4);
        map.put("k2", 3);
        map.put("k3", 2);
        map.put("k4", 1);

        List<Map.Entry<String, Integer>> copyImmutableUsingList = List.copyOf(map.entrySet());
        // copyImmutableUsingList.set(0, Map.entry("k1", 5)); // runtime-error

        Map<String, Integer> copyMutable = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        copyMutable.put("k1", 5); // mutable

        System.out.println(map);
        System.out.println(copyImmutableUsingList);
        System.out.println(copyMutable);
    }
}
