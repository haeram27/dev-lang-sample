package com.example.sample.base.algo;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class PermutationTests {
    <T> void printa(T[] a) {
        for (var e : a) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    // TODO: implement permutation() Under Here
    <T> void permutation(T[] data, T[] out, int r, int depth, boolean[] visited) {}

    @Test
    public void run() {
        int r = 2;
        // permutation = {a, b}, {a, c}, {a, d}, {b, a}, {b, c}, {b, d}, {c, a}, {c, b}, {c, d}, {d, a}, {d, b}, {d, c}
        String[] data = {"a", "b", "c", "d"}; // n = 4
        String[] out = new String[r];
        boolean[] visited = new boolean[data.length];
        Arrays.fill(visited, false);

        System.out.println("\n[permutation recursive]===================");

        /* !!!! combination caller !!!! */
        permutation(data, out, r, 0, visited);
    }

    /**
    *
    * @brief permutation
    *
    * @tparam T
    * @param data      element list (size >= r)
    * @param out       result of combination (size = r)
    * @param r         round (number of output elements)
    * @param depth     index of out[](permutation), if depth==2 then 0~(depth-1) index is filled in out[]
    *                  index i on for() statement is index of data[] to be candidate of out[depth]
    * @param visited   check consumed element(index) of data[] while traversal
    */
    public <T> void permutation_answer(T[] data, T[] out, int r, int depth, boolean[] visited) {
        //System.out.println(String.format("depth=%d, start=%d", depth, start));
        if (depth == r) {
            printa(out);
            return;
        }

        /**
         * depth : index of out[], level of permutation selecting tree
         * i : index for data[] to be out[depth], i can be controlled by for() and visited[]
         * visited[i] : if true then data[i] can NOT be used in next depth 
         *              if false then data[i] can be used in previous/currrent depth
         */
        for (int i = 0; i < data.length; i++) {
            if (!visited[i]) {
                out[depth] = data[i];
                visited[i] = true; // data[i] can NOT be selected in next depth
                permutation_answer(data, out, r, depth + 1, visited);
                visited[i] = false; // data[i] can be selected again in previous depth
            }
        }
    }
}
