package com.example.sample.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

public class MethodRefTest {

    static class Person {
        final String name;

        Person(String name) {
            this.name = name;
        }

        String greet(String other) {
            return name + " -> hi " + other;
        }
    }

    @Test
    void testString_unbound_toUpperCase() {

        /*
            String::toUpperCase()의 람다 변환: s -> s.toUpperCase();
            람다의 첫 번째 인자가 메서드 참조의 리시버(인스턴스)로 사용됨

            Function<String, String>의 람다 시그니처는 `(String) -> String`
            String::toUpperCase()의 람다 시그니처는 `(String) -> String`이므로 첫 인자가 리시버(인스턴스)로 사용되어 호환됨
         */
        Function<String, String> f = String::toUpperCase; // unbound

        assertEquals("HELLO", f.apply("hello"));
    }

    @Test
    void testString_unbound_startsWith() {
        /*
            String::startsWith(String prefix)의 람다 변환: (s1, s2) -> s1.startsWith(s2);
            람다의 첫 번째 인자(s1)가 메서드 참조의 리시버(인스턴스)로 사용되고, 두 번째 인자(s2)가 메서드 참조의 매개변수로 사용됨

            BiPredicate<T, U> { boolean test(T t, U u); } 의 람다 시그니처는 `(T, U) -> boolean`
            String::startsWith(String prefix); 의 람다 시그니처는 `(String, String) -> boolean`이므로 첫 인자가 리시버로 사용될 때 호환됨
        */
        BiPredicate<String, String> p = String::startsWith; // 첫 인자가 리시버
        assertTrue(p.test("hello", "he"));
        assertFalse(p.test("hello", "no"));
    }

    @Test
    void testPerson_unbound_and_bound() {
        /*
            Person::greet(String other)의 람다 변환: (p, other) -> p.greet(other);
            람다의 첫 번째 인자(p)가 메서드 참조의 리시버(인스턴스)로 사용되고, 두 번째 인자(other)가 메서드 참조의 매개변수로 사용됨

            BiFunction<T, U, R> { R apply(T t, U u); } 의 람다 시그니처는 `(T, U) -> R`
            Person::greet(String other); 의 람다 시그니처는 `(Person, String) -> String`이므로 첫 인자가 리시버로 사용될 때 호환됨

            또한, bound method reference인 alice::greet은 alice가 리시버로 고정되어 있기 때문에 Function<String, String>과 호환됨
         */
        BiFunction<Person, String, String> greeter = Person::greet; // unbound: Person is 리시버
        Person alice = new Person("Alice");
        assertEquals("Alice -> hi Bob", greeter.apply(alice, "Bob"));

        Function<String, String> aliceGreeter = alice::greet; // bound: alice가 리시버로 고정
        assertEquals("Alice -> hi Charlie", aliceGreeter.apply("Charlie"));
    }
}
