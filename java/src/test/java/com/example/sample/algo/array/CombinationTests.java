package com.example.sample.algo.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

/*
 * Combination - Number of cases consideration of order
 * 조합 - 순서를 고려하지 않은 경우의 수(부분집합)
 */
public class CombinationTests extends EvaluatedTimeTests {

    <T> void printa(T[] a) {
        for (var e : a) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    /* QUIZ */
    // TODO: implement combination() Under Here
    <T> void combination(T[] data, T[] out, int r, int depth, int start) {}

    @Test
    public void run() {
        int r = 3;
        // combination, r==2 : {{a, b}, {a,c}, {a,d}, {b,c}, {b,d}, {c,d}} 
        // combination, r==3 : {{a,b,c}, {a,b,d}, {a,c,d}, {b,c,d}}
        String[] data = {"a", "b", "c", "d"}; // n = 4
        String[] out = new String[r];

        System.out.println("\n[combination recursive]====");

        /* !!!! combination caller !!!! */
        combination(data, out, r, 0, 0);
    }

    /* ANSWER */
    /**
    * @brief combination
    *
    * @tparam T
    * @param data      element list (size >= r)
    * @param out       result of combination (size = r)
    * @param r         round (number of output elements)
    * @param depth     current level of combination, if depth==2 then 0~(depth-1) index is filled in out array 
    * @param start     start is index of data to be candidate of out[depth]
    */
    public <T> void combinationA(T[] data, T[] out, int r, int depth, int start) {
        //System.out.println(String.format("depth=%d, start=%d", depth, start));
        if (depth == r) {
            printa(out);
            return;
        }

        /**
         * depth : index of out[], level of combination selecting tree
         * i : index for data[] to be out[depth], i can be controlled by for() and start
         */
        for (int i = start; i < data.length; i++) {
            // data[i] is selected for current depth
            out[depth] = data[i];
            // data[i] can NOT be select in next depth because pass i+1 as start
            combinationA(data, out, r, depth + 1, i + 1);
        }
    }

    @Test
    public void runA() {
        int r = 2;
        // combination, r==2 : {{a, b}, {a,c}, {a,d}, {b,c}, {b,d}, {c,d}} 
        // combination, r==3 : {{a,b,c}, {a,b,d}, {a,c,d}, {b,c,d}}
        String[] data = {"a", "b", "c", "d"}; // n = 4
        String[] out = new String[r];

        System.out.println("\n[combination recursive]====");

        /* !!!! combination caller !!!! */
        combinationA(data, out, r, 0, 0);
    }

    /* Collect combinations using HashSet*/
    public void combinationC(String[] data, String[] out, int r, int depth, int start, HashSet<String> set) {
        //System.out.println(String.format("depth=%d, start=%d", depth, start));
        if (depth == r) {
            set.add(Arrays.stream(out).collect((Collectors.joining())));
            return;
        }

        /**
         * depth : index of out[], level of combination selecting tree
         * i : index for data[] to be out[depth], i can be controlled by for() and start
         */
        for (int i = start; i < data.length; i++) {
            // data[i] is selected for current depth
            out[depth] = data[i];
            // data[i] can NOT be select in next depth because pass i+1 as start
            combinationC(data, out, r, depth + 1, i + 1, set);
        }
    }

    @Test
    public void runC() {
        HashSet<String> set = new HashSet<>();
        int r = 2;
        String[] data = {"a", "b", "c", "d"}; // n = 4
        String[] out = new String[r];

        System.out.println("\n[combination recursive]====");

        /* !!!! combination caller !!!! */
        combinationC(data, out, r, 0, 0, set);
        set.forEach(e -> System.out.println(e));
        System.out.println(set.size());
    }

    /* QUIZ */
    @Test
    public void fastestCheckTwoIndexCombinationInSingleArrayMatching() {
        // find all matches in half and little bit more loop 
        int len = 4;
        // TODO: print all combination of two index
    }

    /* ANSWER */
    @Test
    public void fastestCheckTwoIndexCombinationInSingleArrayMatchingA() {
        // find all matches in half and little bit more loop 
        int len = 4;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                System.out.println(i + ", " + j);
                System.out.println((len - 1 - i) + ", " + (len - 1 - j));
                System.out.println("---");
            }
        }
    }

    void printAllSubsets(int[] arr) {
        List<Integer> currentSubset = new ArrayList<>();
        findAllSubsetsRecursive(arr, 0, currentSubset);
    }

    void findAllSubsetsRecursive(int[] arr, int startIndex, List<Integer> currentSubset) {
        System.out.println("--- " + startIndex + currentSubset);

        // 배열의 끝까지 탐색
        for (int i = startIndex; i < arr.length; i++) {
            // 현재 요소를 부분집합에 추가
            currentSubset.add(arr[i]);

            // 다음 요소를 가지고 재귀 호출
            findAllSubsetsRecursive(arr, i + 1, currentSubset);
            System.out.println("+++ " + currentSubset);
            // 백트래킹: 다음 경우의 수를 위해 마지막에 추가했던 요소 제거
            currentSubset.remove(currentSubset.size() - 1);
        }
    }

    @Test
    void realSubsetTest() {
        int[] a = {1, 2, 3};
        printAllSubsets(a);
    }


    /**
     * The reason why use recursive call instead loop (for) choice depth
     * It needs each loop (for) and variable per depth to get each out[depth] choice  
     */
    @Test
    void combinationWithLoop_FixedR() {
        String[] data = {"a", "b", "c", "d", "e"};
        int n = data.length;
        int r = 3; // 3개를 뽑는 조합

        // r = 3 이므로 3개를 뽑는 조합
        // 뽑는 대상이 3개 이므로 변수 3개
        // 변수 3개이므로 3중 for 루프 사용
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    System.out.println(data[i] + " " + data[j] + " " + data[k]);
                }
            }
        }
    }
}
