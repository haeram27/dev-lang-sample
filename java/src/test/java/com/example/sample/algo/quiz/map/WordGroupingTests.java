package com.example.sample.algo.quiz.map;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, List<String>> m = new LinkedHashMap<>();

        // StringTokenizer st = new StringTokenizer(s2);
        // while (st.hasMoreTokens()) {
        //     System.out.println(st.nextToken());
        // }

        String[] tokens = s1.split("[\\s]+");
        for (String t : tokens) {
            if (!t.isBlank()) {
                // var k = t.chars().mapToObj(Character::toString).sorted().collect(Collectors.joining());
                char[] ar = t.toCharArray();
                Arrays.sort(ar);
                var k = new String(ar);

                if (m.containsKey(k)) {
                    m.get(k).add(t);
                } else {
                    m.put(k, Stream.of(t).collect(Collectors.toList()));
                }
            }
        }

        m.forEach((k, v) -> {
            for (var t : v) {
                System.out.print(t + " ");
            }
            System.out.println();
        });
    }

}
