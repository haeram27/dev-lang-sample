package com.example.sample.base.ds;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

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
    public void mapMultiListTest() {

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
}
