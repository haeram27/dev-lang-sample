package com.example.sample.algo.array;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/* NOT IMPORTANT!!! */
public class XStringPermutationTests {

    /*
     * StringPermutation: StringPermutation is slower than ArrayPermutation exponentially
     */
    public void StringPermutation(String prefix, String str, LinkedHashSet<String> set) {
        int n = str.length();
        // if (n == 0)
        //     System.out.println(prefix);

        if (!prefix.equals("")) {
            set.add(prefix);
        }

        for (int i = 0; i < n; i++)
            StringPermutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), set);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg"}) // 3, 2
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void StringPermutationTest(String str) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        long start = System.nanoTime();
        StringPermutation("", str, set);
        long end = System.nanoTime();

        set.forEach(e -> System.out.println(e));
        System.out.println(set.size());
        System.out.println(end - start);
    }



    /*
     * ArrayPermutation: ArrayPermutation is faster than StringPermutation exponentially
     */
    void arrayPermutation(char[] data, char[] out, int r, int depth, boolean[] visited, LinkedHashSet<String> set) {
        if (depth == r) {
            set.add(new String(out));
            return;
        }
        for (int i = 0; i < data.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                out[depth] = data[i];
                arrayPermutation(data, out, r, depth + 1, visited, set);
                visited[i] = false;
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg"}) // 3, 2
    @Timeout(value = 3, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void ArrayPermutationTest(String str) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        char[] data = str.toCharArray();

        long start = System.nanoTime();
        for (int i = 1; i <= str.length(); i++) {
            char[] out = new char[i];
            boolean[] visited = new boolean[str.length()];
            arrayPermutation(data, out, i, 0, visited, set);
        }
        long end = System.nanoTime();

        set.forEach(e -> System.out.println(e));
        System.out.println(set.size());
        System.out.println(end - start);
    }
}
