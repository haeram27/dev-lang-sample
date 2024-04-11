package com.example.sample.base.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import com.example.sample.EvaluatedTimeTests;

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
    public void fastestTwoIndexCombinationInSingleArray() {
        // recursive way is so slow than just for loop
        int len = 4;
        // TODO: print all combination of two index
    }

    /* ANSWER */
    @Test
    public void fastestTwoIndexCombinationInSingleArrayA() {
        // recursive way is so slow than just for loop
        int len = 4;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                System.out.println(i + " " + j);
            }
        }
    }

    /* QUIZ */
    @Test
    public void fastestTwoIndexCombinationInSingleArrayMatching() {
        // find all matches in half and little bit more loop 
        int len = 4;
        // TODO: print all combination of two index
    }

    /* ANSWER */
    @Test
    public void fastestTwoIndexCombinationInSingleArrayMatchingA() {
        // find all matches in half and little bit more loop 
        int len = 4;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                System.out.println(i + ", " + j);
                System.out.println((len - 1 - i) + ", " + (len - 1 - j));
            }
        }
    }
}
