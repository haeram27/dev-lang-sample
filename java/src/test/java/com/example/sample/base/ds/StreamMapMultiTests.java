package com.example.sample.base.ds;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class StreamMapMultiTests {
    /** Methods to make Stream
        <R> Stream<R>  flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        DoubleStream   flatMapToDouble(Function<? super T,? extends DoubleStream> mapper)
        IntStream      flatMapToInt(Function<? super T,? extends IntStream> mapper)
        LongStream     flatMapToLong(Function<? super T,? extends LongStream> mapper)
        <R> Stream<R>  mapMulti(BiConsumer<? super T,? super Consumer<R>> mapper)
        DoubleStream   mapMultiToDouble(BiConsumer<? super T,? super DoubleConsumer> mapper)
        IntStream      mapMultiToInt(BiConsumer<? super T,? super IntConsumer> mapper)
        LongStream     mapMultiToLong(BiConsumer<? super T,? super LongConsumer> mapper)
    */

    @Test
    public void mapMultiStringTest() {

        List<String> sentences = List.of("hello world", "java stream");

        // flatMap SHOULD return new stream instance per element of source stream
        sentences.stream()
                .flatMap(sentence -> Stream.of(sentence.split("\\s+")))
                .forEach(System.out::println); // "hello" "world" "java" "stream"

        // mapMulti() is advanced version of flatMap()
        // mapMulti do NOT need to create new stream instance
        sentences.stream()
                .mapMulti((sentence, consumer) -> {
                    for (String word : sentence.split("\\s+")) {
                        consumer.accept(word);
                    }
                })
                .forEach(System.out::println); // "hello" "world" "java" "stream"
    }

    @Test
    public void mapMultiListOfListTest() {

        List<Integer> firstList = List.of(3, 4);
        List<Integer> secondList = List.of(1, 2);
        List<List<Integer>> listlist = List.of(firstList, secondList);

        // flatMap SHOULD return new stream instance per element of source stream
        listlist.stream()
                .flatMap(List::stream)
                .forEach(System.out::println); // 3 4 1 2

        // mapMulti() is advanced version of flatMap()
        // mapMulti do NOT need to create new stream instance
        listlist.stream()
                .mapMulti((list, consumer) -> {
                    for (Integer i : list) {
                        consumer.accept(i);
                    }
                })
                .forEach(System.out::println); // 3 4 1 2
    }

    @Test
    public void mapMultiListOfMapTest() {
        // List<Map<String, String>>
        String jsonString = "["
                + "{\"ip\": \"1.1.1.1\"},"
                + "{\"ip\": \"2.2.2.2\"},"
                + "{\"ip\": \"3.3.3.3\"},"
                + "{\"ip\": \"4.4.4.4\"}"
                + "]";

        try {
            // ObjectMapper
            ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

            // transform JSON to List<Map<String, String>>
            List<Map<String, String>> listMap = objectMapper.readValue(jsonString, new TypeReference<List<Map<String, String>>>() {});

            // flatten by mapMulti
            var ipSet = listMap.stream()
                .<String>mapMulti((map, consumer) -> map.values().forEach(v -> consumer.accept((String) v)))
                .collect(Collectors.toSet());

            // print set
            System.out.println(ipSet); // [1.1.1.1, 2.2.2.2, 3.3.3.3, 4.4.4.4]

            // flatten by mapMulti
            var ipNumberList = listMap.stream()
                .<String>mapMulti((map, consumer) -> map.values().forEach(v -> consumer.accept((String) v)))
                .<String>mapMulti((s, consumer) -> Arrays.stream(s.split("\\.")).forEach(e->consumer.accept(e)))
                .collect(Collectors.toList());

            // print list
            System.out.println(ipNumberList); // [1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4]
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
