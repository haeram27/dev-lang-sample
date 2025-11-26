package com.example.sample.algo.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StringSortTests {
    String s = "abcdeghijklmn";
    String s1 = "abcdeg hijklmn";

    @Test
    public void CodePointStreamSort() {
        String sorted;

        sorted = s.chars().mapToObj(Character::toString).sorted(Collections.reverseOrder())
                .collect(Collectors.joining());
        System.out.println(sorted);

        // IntStream.sorted() can NOT use Collections.reverseOrder
        sorted = s.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(sorted);
    }

    @Test
    public void StringStreamSort() {
        String sorted;
        sorted = Stream.of(s.split("")).sorted().collect(Collectors.joining());
        System.out.println(sorted);
        sorted = Stream.of(s1.split("")).filter(e -> !e.isBlank()).sorted().collect(Collectors.joining());
        System.out.println(sorted);
        sorted = Stream.of(s.split("")).sorted(Collections.reverseOrder()).collect(Collectors.joining());
        System.out.println(sorted);
    }

    <T> void swap(int i, int j, T[] a) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    @Test
    public void CharArrayAscendingSort() {
        var ar = s.toCharArray();
        Arrays.sort(ar);
        System.out.println(new String(ar));
    }

    @Test
    public void CharArrayDescendingSort() {
        var ar = s.toCharArray();
        Arrays.sort(ar);
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            char tmp = ar[i];
            ar[i] = ar[j];
            ar[j] = tmp;
        }

        System.out.println(new String(ar));
    }
}
