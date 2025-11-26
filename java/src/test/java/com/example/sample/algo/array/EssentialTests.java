package com.example.sample.algo.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class EssentialTests {
    String s = "abcdeghijklmn";
    String s1 = "abcdeg hijklmn";

    @Test
    public void getMaxTest() {
        int a[] = { 1, 2, 3, 4, 5 };

        Integer A = 1;
        Integer B = 2;
        Integer C = 3;

        // TODO:
        // hint: use stream
        // print max of a[]
        System.out.println(a);

        // print max among A, B, C
        System.out.println(A + B + C);
    }

    @Test
    public void getMaxTestA() {
        int a[] = { 1, 2, 3, 4, 5 };

        Integer A = 1;
        Integer B = 2;
        Integer C = 3;

        // TODO:
        System.out.println(Arrays.stream(a).max().orElse(0));
        System.out.println(Stream.of(A, B, C).min(Integer::compareTo).orElse(0));
        System.out.println(Stream.of(A, B, C).max(Integer::compare).orElse(0));
        System.out.println(Math.max(Math.max(A, B), C));
    }

    @Test
    public void primitiveArrayDescSort() {
        int a[] = { 1, 2, 3, 4, 5 };
        var sorted = a;

        // TODO:

        System.out.println(Arrays.toString(sorted));
    }

    @Test
    public void primitiveArrayDescSortA() {
        int a[] = { 1, 2, 3, 4, 5 };
        var sorted = a;

        // boxed() = int -> Integer
        // mapToInt() =  Integer -> int
        sorted = Arrays.stream(a).boxed().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();

        System.out.println(Arrays.toString(sorted));
    }

    @Test
    public void stringReverseTest() {
        // TODO:
        System.out.println(s);
    }

    @Test
    public void stringReverseTestA() {
        System.out.println(new StringBuilder(s).reverse().toString());
    }

    @Test
    public void CodePointStreamSort() {
        String sorted = "";
        // TODO: map codepoint to each string > sort > join to one String
        // sorted = s.chars()...

        System.out.println(sorted);
    }

    @Test
    public void CodePointStreamSortA() {
        String sorted;

        // recommended
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
        String sorted = "";
        // TODO: steam with split to each char String > sort > join to one String
        // sorted = Stream.of(s...)

        System.out.println(sorted);
    }

    @Test
    public void StringStreamSortA() {
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
        var a = s.toCharArray();
        // TODO:

        System.out.println(new String(a));
    }

    @Test
    public void CharArrayAscendingSortA() {
        var a = s.toCharArray();
        Arrays.sort(a);
        System.out.println(new String(a));
    }

    @Test
    public void CharArrayDescendingSort() {
        var a = s.toCharArray();
        // TODO: use array swap

        System.out.println(new String(a));
    }

    @Test
    public void CharArrayDescendingSortA() {
        var a = s.toCharArray();
        Arrays.sort(a);
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            char tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }

        System.out.println(new String(a));
    }

    @Test
    void mapToListTest() {
        var m = new LinkedHashMap<String, List<Integer>>();
        m.put("a", Stream.of(1, 2, 3).collect(Collectors.toList()));
        m.put("b", Stream.of(4, 5, 6).collect(Collectors.toList()));
        m.put("c", Stream.of(7, 8, 9).collect(Collectors.toList()));

        // TODO: start
        // use new ArrayList(Collection), Set -> List
        var list1 = new ArrayList<Map.Entry<String, List<Integer>>>(m.entrySet());

        // use Map.entrySet().stream()
        var list2 = m.entrySet().stream().collect(Collectors.toList());
        // TODO: end

        list1.forEach(e -> {
            System.out.print(e.getKey() + ":");
            for (var v : e.getValue()) {
                System.out.print(v + ",");
            }
            System.out.println();
        });

        list2.forEach(e -> {
            System.out.print(e.getKey() + ":");
            for (var v : e.getValue()) {
                System.out.print(v + ",");
            }
            System.out.println();
        });
    }

    @Test
    void mapToListTestA() {
        var m = new LinkedHashMap<String, List<Integer>>();
        m.put("a", Stream.of(1, 2, 3).collect(Collectors.toList()));
        m.put("b", Stream.of(4, 5, 6).collect(Collectors.toList()));
        m.put("c", Stream.of(7, 8, 9).collect(Collectors.toList()));

        // TODO: start
        var list1 = new ArrayList<Map.Entry<String, List<Integer>>>(m.entrySet());
        var list2 = m.entrySet().stream().collect(Collectors.toList());
        // TODO: end

        list1.forEach(e -> {
            System.out.print(e.getKey() + ":");
            for (var v : e.getValue()) {
                System.out.print(v + ",");
            }
            System.out.println();
        });

        list2.forEach(e -> {
            System.out.print(e.getKey() + ":");
            for (var v : e.getValue()) {
                System.out.print(v + ",");
            }
            System.out.println();
        });
    }

    @Test
    void mapSortByKey() {
        var m = new LinkedHashMap<String, Integer>();
        m.put("a", 3);
        m.put("b", 2);
        m.put("c", 1);

        // TODO:
    }

    @Test
    void mapSortByKeyA() {
        var m = new LinkedHashMap<String, Integer>();
        m.put("a", 3);
        m.put("b", 2);
        m.put("c", 1);

        // TODO:
        m.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            System.out.println(e.getKey() + ":" + e.getValue());
        });

        m.entrySet().stream().sorted(Map.Entry.comparingByKey(Collections.reverseOrder())).forEach(e -> {
            System.out.println(e.getKey() + ":" + e.getValue());
        });
    }

    @Test
    void mapSortByKeyAndCollect() {
        Map<String, Integer> m = Map.of("b", 2, "a", 1, "C", 3);

        // TODO:
    }

    @Test
    void mapSortByKeyAndCollectA() {
        Map<String, Integer> m = Map.of("b", 2, "a", 1, "C", 3);

        // Ascending (case sensitive)
        var sorted = m.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // key Ascending
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, // merger, select first item when key collision 
                        LinkedHashMap::new)); // LHM keeps order

        System.out.println("Key, Ascending, CaseSensitive:");
        System.out.println(sorted);

        // Ascending (case insensitive)
        var sortedIgnoreCase = m.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        System.out.println("Key, Ascending, CaseInsensitive:");
        System.out.println(sortedIgnoreCase);

        // Descending (case sensitive)
        var sortedDesc = m.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        System.out.println("Key, Descending, CaseSensitive:");
        System.out.println(sortedDesc);

        /* using TreeMap */
        var treeSortedDesc = new TreeMap<String, Integer>();
        treeSortedDesc.putAll(m);
        System.out.println("TreeMap, Key, Descending, CaseSensitive:");
        treeSortedDesc.entrySet().forEach(e -> System.out.print(e));

        var sortedDescIgnoreCase = m.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER.reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (a,b) -> a, LinkedHashMap::new));
        /* using TreeMap */
        // var sortedDescIgnoreCase = new TreeMap<>(String.CASE_INSENSITIVE_ORDER.reversed());
        //     sortedDescIgnoreCase.putAll(m);
        System.out.println("Key, Descending, CaseInsensitive:");
        System.out.println(sortedDescIgnoreCase);
    }

    @Test
    void mapSortByValue() {
        var m = new LinkedHashMap<String, Integer>();
        m.put("a", 3);
        m.put("b", 2);
        m.put("c", 1);

        // TODO:
    }

    @Test
    void mapSortByValueA() {
        var m = new LinkedHashMap<String, Integer>();
        m.put("a", 3);
        m.put("b", 2);
        m.put("c", 1);

        // TODO:
        m.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e -> {
            System.out.println(e.getKey() + ":" + e.getValue());
        });

        m.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).forEach(e -> {
            System.out.println(e.getKey() + ":" + e.getValue());
        });
    }

}
