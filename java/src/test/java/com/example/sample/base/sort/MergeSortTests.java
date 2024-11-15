package com.example.sample.base.sort;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class MergeSortTests {

    /*
    * !!! useful sort algorithms are insertion, merge, quick, dualal pivot quick
    */

    // divide array 
    void mergeSort(int[] a, int len) {
        if (len < 2)
            return;

        int mid = len / 2;
        int[] l = new int[mid];
        int[] r = new int[len - mid];

        for (int i = 0; i < mid; ++i) {
            l[i] = a[i];
        }

        for (int i = mid; i < len; ++i) {
            r[i - mid] = a[i];
        }

        // divde array to l and r recursively
        mergeSort(l, mid);
        mergeSort(r, len - mid);

        // after divide recursively, merge l and r array
        merge(a, l, r, mid, len - mid);

    }

    void merge(int[] a, int[] l, int[] r, int llen, int rlen) {
        int i = 0; // i index of l
        int j = 0; // j index of r
        int k = 0; // k index of a

        // insert item until each index reaches to the end of l or r size
        // after this while(), one of l or r's index is completely consumed.
        while (i < llen && j < rlen) {
            a[k++] = (l[i] < r[j]) ? l[i++] : r[j++];
        }

        // consume l array if there is remaining index
        while (i < llen) {
            a[k++] = l[i++];
        }

        // consume r array if there is remaining index
        while (j < rlen) {
            a[k++] = r[j++];
        }
    }

    @Test
    public void run() {
        int[] arr = {9, 5, 1, 0, 6, 2, 3, 4, 7, 8};
        System.out.println("Before sorting");
        System.out.println(Arrays.toString(arr));

        mergeSort(arr, arr.length);
        System.out.println("After sorting");
        System.out.println(Arrays.toString(arr));
    }

}
