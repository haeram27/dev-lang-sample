package com.example.sample.base.sort;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class QuickSortTests {
    void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /*
    * !!! useful sort algorithms are insertion, merge, quick, dualal pivot quick
    */

    /* QUIZ */
    void quickSort(int[] a, int lo, int hi) {}

    @Test
    public void run() {
        int[] a = {9, 5, 1, 0, 6, 2, 3, 4, 7, 8};
        System.out.println("Before sorting");
        System.out.println(Arrays.toString(a));

        quickSort(a, 0, a.length - 1);
        System.out.println("After sorting");
        System.out.println(Arrays.toString(a));
    }



    /* Answer */
    void quickSortA(int[] a, int lo, int hi) {
        if (lo >= hi)
            return;

        int pivot = partitionA(a, lo, hi); // pivot is pivot's index and is sorted position
        quickSortA(a, lo, pivot - 1); // no need to include partitonIndex
        quickSortA(a, pivot + 1, hi); // no need to include partitonIndex
    }

    /**
     * partition() make divide array as two part based on pivot value and return pivot's index
     * given array after partition() -> | less or equal than pivot | pivot | greater than pivot | 
     * after partition() only pivot value will be placed at sorted position
     * pivot value is standard reference value to divide values as two part of array
     * ahead part includes less or equal than pivot value
     * behind part includes greater than pivot value
     * partition() returns sorted index of pivot so next partition() invoking should NOT include current pivot index
     * @param a
     * @param lo
     * @param hi
     * @return i index of pivot
     */
    int partitionA(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int l = (lo - 1);
        for (int r = lo; r < hi; r++) {
            if (a[r] <= pivot) {
                swap(a, r, ++l);
            }
        }
        swap(a, hi, ++l); // hi is pivot's index, after the swap, pivot values will be placed at ++l index
        return l; // resturn pivot's index
    }

    @Test
    public void runA() {
        int[] a = {9, 5, 1, 0, 6, 2, 3, 4, 7, 8};
        System.out.println("Before sorting");
        System.out.println(Arrays.toString(a));

        quickSortA(a, 0, a.length - 1);
        System.out.println("After sorting");
        System.out.println(Arrays.toString(a));
    }
}
