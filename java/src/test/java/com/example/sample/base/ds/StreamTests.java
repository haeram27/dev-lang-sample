package com.example.sample.base.ds;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamTests {
    /** Methods to make Stream
        # Stream generate
            ## primitive
            IntStream IntStream.of(int t)
            IntStream IntStream.of(int... values)
            LongStream LongStream.of(long t)
            LongStream LongStream.of(long... values)
            DoubleStream DoubleStream.of(double t)
            DoubleStream DoubleStream.of(double... values)
    
            ## String
            IntStream {String}.chars()
            IntStream {String}.codePoints()
    
            ## Arrays
            Stream<T> Arrays.stream(T[] array)
            Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)
            IntStream Arrays.stream(int[] array)
            IntStream Arrays.stream(int[] array, int startInclusive, int endExclusive)
            LongStream Arrays.stream(long[] array)
            LongStream Arrays.stream(long[] array, int startInclusive, int endExclusive)
            DoubleStream Arrays.stream(double[] array)
            DoubleStream Arrays.stream(double[] array, int startInclusive, int endExclusive)
    
            ## Collection(List, Set)
            Stream<E> {Collection}.stream()
            Stream<E> {Collection}.parallelStream()
            Stream<E> {Map}.entrySet().stream()
            
            ## Stream
            Stream<T> Stream.of(T t)
            Stream<T> Stream.of(T... values)
            Stream<T> Stream.ofNullable(T t)
            Stream<T> Stream.empty()
            Stream<T> Stream.iterate(final T seed, final UnaryOperator<T> f)
            Stream<T> Stream.iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
            Stream<T> Stream.generate(Supplier<? extends T> s)
            Stream<T> Stream.builder().add(T t).build()
            Stream<T> Stream.concat(Stream<? extends T> a, Stream<? extends T> b)
    
        # Stream.intermediate operations
            <R> Stream<R>  map(Function<? super T,? extends R> mapper)
            DoubleStream   mapToDouble(ToDoubleFunction<? super T> mapper)
            IntStream      mapToInt(ToIntFunction<? super T> mapper)
            LongStream     mapToLong(ToLongFunction<? super T> mapper)
            <R> Stream<R>  flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
            DoubleStream   flatMapToDouble(Function<? super T,? extends DoubleStream> mapper)
            IntStream      flatMapToInt(Function<? super T,? extends IntStream> mapper)
            LongStream     flatMapToLong(Function<? super T,? extends LongStream> mapper)
            <R> Stream<R>  mapMulti(BiConsumer<? super T,? super Consumer<R>> mapper)
            DoubleStream   mapMultiToDouble(BiConsumer<? super T,? super DoubleConsumer> mapper)
            IntStream      mapMultiToInt(BiConsumer<? super T,? super IntConsumer> mapper)
            LongStream     mapMultiToLong(BiConsumer<? super T,? super LongConsumer> mapper)
    
            Stream<T>      sorted()
            Stream<T>      sorted(Comparator<? super T> comparator)
    
            Stream<T>      distinct()
            Stream<T>      filter(Predicate<? super T> predicate)
    
            Stream<T>      skip(long n)
            Stream<T>      limit(long maxSize)
    
            Stream<T>      peek(Consumer<? super T> action)
    
            Stream<T>      takeWhile(Predicate<? super T> predicate)
            Stream<T>      dropWhile(Predicate<? super T> predicate)
    
        # Stream.terminal operations
            <R,A> R        collect(Collector<? super T,A,R> collector)
            <R> R          collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
    
            boolean        allMatch(Predicate<? super T> predicate)
            boolean        anyMatch(Predicate<? super T> predicate)
            boolean        noneMatch(Predicate<? super T> predicate)
    
            Optional<T>    findAny()
            Optional<T>    findFirst()
    
            void           forEach(Consumer<? super T> action)
            void           forEachOrdered(Consumer<? super T> action)
    
            long           count()
            Optional<T>    max(Comparator<? super T> comparator)
            Optional<T>    min(Comparator<? super T> comparator)
    
            Optional<T>    reduce(BinaryOperator<T> accumulator)
            T              reduce(T identity, BinaryOperator<T> accumulator)
            <U> U          reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
    
            Object[]       toArray()
            <A> A[]        toArray(IntFunction<A[]> generator)
            default List<T>    toList()
    */

    @Test
    public void run() {

        List<Integer> list = List.of(0, 1, 2, 3, 4);

        // create stream
        list.stream();

        // process per stream element
        list.stream().forEach(System.out::println);
        // 0 1 2 3 4

        // transform stream elements
        list.stream().map(i -> i * i).forEach(System.out::println);
        // 0 1 4 9 16

        // discard tail of stream
        list.stream().limit(4).forEach(System.out::println);
        // 0

        // discard head of stream
        list.stream().skip(1).forEach(System.out::println);
        // 1 2 3 4

        // select elements using Predicate
        list.stream().filter(i -> i <= 1).forEach(System.out::println);
        // 0 1

        list.stream().filter(i -> i % 2 == 0).forEach(System.out::println);
        // 0 2 4

        // reduce elements to one,  0+1+2+3+4
        var n = list.stream().reduce((a, b) -> a + b).get();
        System.out.println(n);
        // 10
    }

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
