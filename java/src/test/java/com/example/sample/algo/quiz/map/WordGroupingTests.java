package com.example.sample.algo.quiz.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class WordGroupingTests {
    /* 
        make group of token consist of same alphabets
        and print each group in each line
        
        output for s1 ::
        final fnial fanil
        proxy pxory
        abyss
    
        output for s2 ::
        fired fried 
        gainly 
        dreads 
        listen silent 
    */
    String s1 = new String("final fnial fanil proxy pxory abyss");
    String s2 = new String("fired gainly dreads listen silent fried");

    @Test
    void wordGroupingTest() {
        // TODO:
    }

    @Test
    void wordGroupingTestA() {
        var m = new HashMap<String, List<String>>();
        var tokens = s1.split("\\s+");

        for (var t : tokens) {
            if (!t.isBlank()) {
                /* key = string sort by alphabet order
                    # 1 - String stream, simple and easiest
                    var k = Arrays.stream(t.split("")).sorted().collect(Collectors.joining());

                    # 2 - IntStream
                    var k = t.chars().mapToObj(Character::toString).sorted().collect(Collectors.joining());

                    # 3 - char[] Array, fastest!
                    char[] ar = t.toCharArray();
                    Arrays.sort(ar);
                    var k = new String(ar);
                 */
                var k = Arrays.stream(t.split("")).sorted().collect(Collectors.joining());

                if (m.containsKey(k)) {
                    m.get(k).add(t);
                } else {
                    m.put(k, Stream.of(t).collect(Collectors.toList()));
                }
            }
        }

        m.forEach((k, v) -> System.out.println(k +": " + v));
    }

}
