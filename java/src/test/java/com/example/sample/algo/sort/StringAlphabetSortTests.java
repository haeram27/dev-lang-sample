package com.example.sample.algo.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StringAlphabetSortTests {
    String s = "abcdeghijklmn";
    String s1 = "abcdeg hijklmn";

    /*
        String Stream of each character : easiest and simple
    */
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

    @Test
    public void CharArrayAscendingSort() {
        var ar = s.toCharArray();
        Arrays.sort(ar);
        System.out.println(new String(ar));
    }

    /*
        char[] sort : fastest
    */

    @Test
    public void CharArrayDescendingSort() {
        // make char[]
        var ar = s.toCharArray();

        // sort ascending
        Arrays.sort(ar);

        // reverse array = make descending
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            char tmp = ar[i];
            ar[i] = ar[j];
            ar[j] = tmp;
        }

        // char[] -> String
        var descStr = new String(ar);

        System.out.println(descStr);
    }

    /*
        IntStream(CodePointStream)
    */

    @Test
    public void CodePointStreamSort() {
        String sorted;
        /* natural order */
        sorted = s.chars().sorted().mapToObj(Character::toString).collect(Collectors.joining());
        System.out.println(sorted);

        sorted = s.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        System.out.println(sorted);

        /* reverse order */
        // IntStream.sorted() can NOT use Collections.reverseOrder
        sorted = s.chars().boxed().sorted(Collections.reverseOrder()).map(i -> Character.valueOf((char)i.intValue()).toString()).collect(Collectors.joining());
        System.out.println(sorted);

        sorted = s.chars().mapToObj(Character::toString).sorted(Collections.reverseOrder()).collect(Collectors.joining());
        System.out.println(sorted);
    }
}
