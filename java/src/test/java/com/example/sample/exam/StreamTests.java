package com.example.sample.exam;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StreamTests {
    /** Methods to make Stream
        # primitive
        IntStream IntStream.of(int t)
        IntStream IntStream.of(int... values)
        LongStream LongStream.of(long t)
        LongStream LongStream.of(long... values)
        DoubleStream DoubleStream.of(double t)
        DoubleStream DoubleStream.of(double... values)
        
        # String
        IntStream {String}.chars()
        IntStream {String}.codePoints()
        
        # Arrays
        Stream<T> Arrays.stream(T[] array)
        Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)
        IntStream Arrays.stream(int[] array)
        IntStream Arrays.stream(int[] array, int startInclusive, int endExclusive)
        LongStream Arrays.stream(long[] array)
        LongStream Arrays.stream(long[] array, int startInclusive, int endExclusive)
        DoubleStream Arrays.stream(double[] array)
        DoubleStream Arrays.stream(double[] array, int startInclusive, int endExclusive)
        
        # Collection(List, Set)
        Stream<E> {Collection}.stream()
        Stream<E> {Collection}.parallelStream()
        Stream<E> {Map}.entrySet().stream()
        
        # Stream
        Stream<T> Stream.of(T t)
        Stream<T> Stream.of(T... values)
        Stream<T> Stream.ofNullable(T t)
        Stream<T> Stream.empty()
        Stream<T> Stream.iterate(final T seed, final UnaryOperator<T> f)
        Stream<T> Stream.generate(Supplier<? extends T> s)
        Stream<T> Stream.builder().add(T t).build()
        Stream<T> Stream.concat(Stream<? extends T> a, Stream<? extends T> b)
    */

    @Test
    public void run() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(i);
        }

        // stream
        list.stream();

        // stream 요소 반복
        list.stream().forEach(System.out::println);
        // 0 1 2 3 4

        // stream 요소를 연산가능하게 함
        list.stream().map(i -> i * i).forEach(System.out::println);
        // 0 1 4 9 16

        // stream 요소의 인덱스까지 제한함
        list.stream().limit(1).forEach(System.out::println);
        // 0

        // stream :: discarding first n number of elements
        list.stream().skip(1).forEach(System.out::println);
        // 1 2 3 4

        // stream 요소를 조건문과 비교하여 필터
        list.stream().filter(i -> i <= 1).forEach(System.out::println);
        // 0 1

        list.stream().filter(i -> i % 2 == 0).forEach(System.out::println);
        // 0 2 4

        // stream 단일요소 반환 0+1+2+3+4
        var n = list.stream().reduce((a, b) -> a + b).get();
        System.out.println(n);
        // 10
    }
}
