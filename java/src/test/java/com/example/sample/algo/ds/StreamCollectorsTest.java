package com.example.sample.algo.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamCollectorsTest {

    @Test
    public void toListTest() {
        String s1 = new String("final fnial fanil proxy pxory abyss");
        var tokens1 = s1.split("\\s+");
        Stream.of(tokens1).sorted().collect(
        // @formatter:off
            Collectors.toList()
        // @formatter:on
        ).forEach(e -> {
            System.out.println(e);
        });

        String s2 = new String("final, fnial, fanil, proxy, pxory, abyss");
        var tokens2 = s2.split(",\\s+");
        Stream.of(tokens2).sorted(Collections.reverseOrder()).collect(
        // @formatter:off
            Collectors.toList()
        // @formatter:on
        ).forEach(e -> {
            System.out.println(e);
        });
    }

    @Test
    public void toMapTest() {
        String s1 = new String("final fnial fanil proxy pxory abyss");
        var tokens = s1.split("\\s+");

        Stream.of(tokens).collect(
        // @formatter:off
            Collectors.toMap(
                Function.identity(),   // keyMapper : key generation func, Function.identity() == t->t
                String::length,        // valueMapper : value generation func
                (oldV, newV) -> newV,  // mergeFunction : key collision resolver, return resoleved value between two value.
                LinkedHashMap::new     // mapFactory : return map instance but not default type of HashMap
            )
        // @formatter:on
        ).forEach((k, v) -> {
            System.out.print("k:" + k);
            System.out.print(", v:" + v);
            System.out.println();
        });
    }

    @Test
    public void CopyMapBytoMapTest() {
        HashMap<String, String> o = new HashMap<>();
        o.put("k1", "v1");
        o.put("k2", "v2");

        var n = o.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        n.put("k2", "v3");

        o.forEach((k, v) -> {
            System.out.print("k:" + k);
            System.out.print(", v:" + v);
            System.out.println();
        });
        n.forEach((k, v) -> {
            System.out.print("k:" + k);
            System.out.print(", v:" + v);
            System.out.println();
        });
    }

    /**
     * Collectors.groupingBy() -> returns Map<K, List<T>>
     * https://devdocs.io/openjdk~21/java.base/java/util/stream/collectors#groupingBy(java.util.function.Function)
     * 
     * Map<K,List<T>> groupingBy(classifier)
     * Map<K, D<T>> groupingBy(classifier, downstream)
     * Map<K, D<T>> groupingBy(classifier, mapFactory, downstream)
     * 
     * classifier = keyMaker by value(= T = stream item)
     * downstream = D Container, default is List but can be Set or Queue etc...
     * mapFactory = Type of Map, default is HashMap but can be LinkedHashMap etc.. 
     */
    @Test
    public void groupingByTest() {
        List<String> l1 = Arrays.asList("A", "B", "C", "D", "A");
        // Map<String, List<String>> m = l1.stream().collect(Collectors.groupingBy(Function.identity()))
        l1.stream().collect(Collectors.groupingBy(Function.identity())).forEach((k, v) -> {
            System.out.println("k::" + k);
            System.out.println("v::" + v);
        });

        // Map<String, List<String>>
        String s1 = new String("final fnial fanil proxy pxory abyss");
        Arrays.stream(s1.split("\\s+")).collect(
        // @formatter:off
            Collectors.groupingBy(
                // classifier == keyMapper, keyMaker
                // classifier returns key of map made using value(each stream element)
                // classifier is function to return map key for each unit of stream and each unit of stream becomes value of map
                str -> Stream.of(str.split("")).sorted().collect(Collectors.joining())
            )
        // @formatter:on
        ).forEach((k, v) -> {
            System.out.println("k::" + k);
            System.out.println("v::" + v);
        });
    }

    /**
     * Collectors.joining(delimiter)
     * concatenate string unit of stream
     */
    @Test
    public void joiningTest() {
        var l1 = new ArrayList<>(Arrays.asList("abc", "def", "ghi", "jkl"));
        System.out.println(l1.stream().collect(Collectors.joining(", ")));

        var l2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        // if unit of stream is not String then need to convert unit type to String(.map(String::valueOf))
        System.out.println(l2.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }
}
