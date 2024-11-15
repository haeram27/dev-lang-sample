package com.example.sample.quiz.greedy;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import java.util.*;
// import java.util.stream.*;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LentShirtsTests {
    // https://school.programmers.co.kr/learn/courses/30/lessons/42862

    private static Stream<Arguments> argSupplier() {
        // @formatter:off
        return Stream.of(
            Arguments.of(5, new int[]{2,4}, new int[]{1,3,5}, 5),
            Arguments.of(5, new int[]{2,4}, new int[]{3}, 4),
            Arguments.of(3, new int[]{3}, new int[]{1}, 2)
        );
        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void quiz(int n, int[] lost, int[] reserve, int expected) {
        int answer = n;
        //TODO:

        System.out.println(expected + " " + answer);
        assertEquals(expected, answer);
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void solutionA(int n, int[] lost, int[] reserve, int expected) {
        int answer = n;
        int[] people = new int[n + 1];

        for (int i : lost) {
            people[i]--;
        }

        for (int i : reserve) {
            people[i]++;
        }

        for (int i = 1; i < people.length; i++) {
            if (people[i] < 0)
                answer--;
        }

        for (int i = 1; i < people.length; i++) {
            if (people[i] < 0 && i - 1 > 0 && people[i - 1] > 0) {
                people[i - 1]--;
                people[i]++;
                answer++;
            } else if (people[i] < 0 && i + 1 < people.length && people[i + 1] > 0) {
                people[i + 1]--;
                people[i]++;
                answer++;
            }
        }
        System.out.println(Arrays.toString(people));
        System.out.println(expected + " " + answer);
        assertEquals(expected, answer);
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void solutionB(int n, int[] lost, int[] reserve, int expected) {
        int answer = 0;
        int llen = lost.length;
        int rlen = reserve.length;
        Arrays.sort(lost);
        Arrays.sort(reserve);

        answer = n - llen;

        for (int l = 0; l < llen; l++) {
            for (int r = 0; r < rlen; r++) {
                if (lost[l] == reserve[r]) {
                    lost[l] = 0;
                    reserve[r] = 0;
                    answer++;
                }
            }
        }

        for (int l = 0; l < llen; l++) {
            for (int r = 0; r < rlen; r++) {
                if ((lost[l] != 0 && reserve[r] != 0) && (lost[l] == reserve[r] - 1 || lost[l] == reserve[r] + 1)) {
                    lost[l] = 0;
                    reserve[r] = 0;
                    answer++;
                }
            }
        }

        assertEquals(expected, answer);
    }
}
