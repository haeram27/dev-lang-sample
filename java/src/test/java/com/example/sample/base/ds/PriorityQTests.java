package com.example.sample.base.ds;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

public class PriorityQTests {

    @Test
    void test() {
        // PriorityQueue allows the duplicate elements
        PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
        q.offer(5);
        q.offer(4);
        q.offer(3);
        q.offer(2);
        q.offer(1);
        q.forEach(e -> System.out.println(e));

        // TreeSet doesn’t allow the duplicate elements
        TreeSet<Integer> s = new TreeSet<>(Collections.reverseOrder());
        s.add(5);
        s.add(4);
        s.add(3);
        s.add(2);
        s.add(1);
        s.forEach(e -> System.out.println(e));

        TreeMap<Integer, Character> m = new TreeMap<>(Collections.reverseOrder());
        m.put(1, 'c');
        m.put(2, 'b');
        m.put(3, 'a');
        m.forEach((k, v) -> System.out.println(k + " " + v));

    }

}
