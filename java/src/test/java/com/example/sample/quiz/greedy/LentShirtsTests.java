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
    public void solutionA(int n, int[] lost, int[] reserve, int correctanswer) {
        int[] people = new int[n];
        int answer = n;

        for (int l : lost)
            people[l - 1]--;
        for (int r : reserve)
            people[r - 1]++;

        for (int i = 0; i < people.length; i++) {
            if (people[i] == -1) {
                if (i - 1 >= 0 && people[i - 1] == 1) {
                    people[i]++;
                    people[i - 1]--;
                } else if (i + 1 < people.length && people[i + 1] == 1) {
                    people[i]++;
                    people[i + 1]--;
                } else
                    answer--;
            }
        }

        assertEquals(correctanswer, answer);
    }

    @ParameterizedTest
    @MethodSource("argSupplier")
    public void solutionB(int n, int[] lost, int[] reserve, int correctanswer) {
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

        assertEquals(correctanswer, answer);
    }
}
