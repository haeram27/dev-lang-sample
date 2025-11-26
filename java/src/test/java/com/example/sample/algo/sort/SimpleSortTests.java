package com.example.sample.algo.sort;

import org.junit.jupiter.api.Test;

/* NOT IMPORTANT!!! */
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

    void selectionSort(int a[]) {
        // TODO:

        print(a);
    }

    void bubbleSort(int a[]) {
        // TODO:

        print(a);
    }

    @Test
    public void run() {
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        selectionSort(a.clone());
        bubbleSort(a.clone());
    }

    void selectionSortA(int a[]) {
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

    void bubbleSortA(int a[]) {
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
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        selectionSortA(a.clone());
        bubbleSortA(a.clone());
    }
}
