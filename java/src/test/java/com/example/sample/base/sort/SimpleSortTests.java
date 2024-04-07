package com.example.sample.base.sort;

import org.junit.jupiter.api.Test;

public class SimpleSortTests {
    void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    void print(int[] a) {
        for (var e : a) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    /*
     * !!! useful sort algorithms are insertion, merge, quick, dual pivot quick
     */
    void insertionSort(int a[]) {
        // TODO:

        print(a);
    }

    void XselectionSort(int a[]) {
        // TODO:

        print(a);
    }

    void XbubbleSort(int a[]) {
        // TODO:

        print(a);
    }


    @Test
    public void run() {
        // TODO Auto-generated method stub
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        insertionSort(a.clone());
        XselectionSort(a.clone());
        XbubbleSort(a.clone());
    }

    /*
     * https://www.geeksforgeeks.org/insertion-sort
     * https://en.wikipedia.org/wiki/Insertion_sort
     */
    void insertionSortA(int a[]) {
        int len = a.length;
        int i, j, key;

        // swap a[i] with very first pos of a[j] in 'a[j] > a[i]'' 

        for (i = 1; i < len; ++i) {
            // store a[i]'s value to key 
            key = a[i];

            // if a[j] > k, move a[j] to a[j+1] (one step to high position)
            for (j = i - 1; (j >= 0) && (a[j] > key); j--) {
                a[j + 1] = a[j];
            }

            // set key to last j position (== j+1)
            // if j for statement is not executed then j+1 == i
            // if j for statement is executed at least once last j will be j+1            
            a[j + 1] = key;
        }

        print(a);
    }

    void insertionSortWithWhile(int a[]) {
        // TODO:
        int i, j, k;
        for (i = 1; i < a.length; i++) {
            k = a[i];
            j = i - 1;
            while (j >= 0 && a[j] > k) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = k;
        }

        print(a);
    }


    void XselectionSortA(int a[]) {
        for (int i = 0; i < a.length - 1; ++i) {
            int min = i;
            for (int j = i + 1; j < a.length; ++j) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            swap(a, min, i);
        }

        print(a);
    }

    void XbubbleSortA(int a[]) {
        int len = a.length;

        for (int i = 1; i < len; ++i) {
            for (int j = 0; j < len - 1; ++j) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }

        print(a);
    }


    @Test
    public void runA() {
        // TODO Auto-generated method stub
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        insertionSortA(a.clone());
        XselectionSortA(a.clone());
        XbubbleSortA(a.clone());
    }
}
