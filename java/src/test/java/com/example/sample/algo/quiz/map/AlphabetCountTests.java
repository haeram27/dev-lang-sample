package com.example.sample.algo.quiz.map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class AlphabetCountTests {

    /**
    * Quest:
    * 1. create map from vector by counting elements of vector
    * 2. sorting map by value of pair
    * 3. makesure diff of most frequent char and least frequent char
    */
    // expected answer is 4 = 5(a) - 1(e)
    String s = "aaaaabbbbcccdde";

    @Test
    void alphabetCountTest() {
        // TODO:
    }

    @Test
    void alphabetCountTestA() {
        var m = new HashMap<Character, Integer>();
        for (var c : s.toCharArray()) {
            m.put(c, m.getOrDefault(c, 0) + 1);
        }

        var list = m.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        System.out.println(list.get(list.size() - 1).getValue() - list.get(0).getValue());
        //System.out.println(list.stream().max(Map.Entry.comparingByValue()).orElse(Map.entry("null", 0)).getValue());
        //System.out.println(list.stream().min(Map.Entry.comparingByValue()).orElse(Map.entry("null", 0)).getValue());
    }

}
