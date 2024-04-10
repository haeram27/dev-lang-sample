package com.example.sample.base.sort;

import org.junit.jupiter.api.Test;

public class InsertionSortTests {

    void printa(int[] a) {
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
    }

    @Test
    public void run() {
        // TODO Auto-generated method stub
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        insertionSort(a.clone());
    }

    /*
    * https://www.geeksforgeeks.org/insertion-sort
    * https://en.wikipedia.org/wiki/Insertion_sort
    */
    void insertionSortA(int a[]) {
        int len = a.length;

        /*
         * insertion sort is simple swap i(key), j(smallest than key)
         * @i : index of key
         * @j : index of smallest than key
         * @k : key == index i's value, temp for swap 
         */
        int i, j, k;

        // swap a[i] with very first pos of a[j] in 'a[j] > a[i]'' 

        for (i = 1; i < len; ++i) {
            // store a[i]'s value to key 
            k = a[i];

            // if a[j] > k, move a[j] to a[j+1] (one step to high position)
            for (j = i - 1; (j >= 0) && (a[j] > k); j--) {
                a[j + 1] = a[j];
            }

            // set k(key) to last j position (== j+1)
            // if j for statement is not executed then j+1 == i
            // if j for statement is executed at least once last j will be j+1            
            a[j + 1] = k;
        }

        printa(a);
    }

    void insertionSortWithWhile(int a[]) {
        /*
         * insertion sort is simple swap i(key), j(smallest than key)
         * @i : index of key
         * @j : index of smallest than key
         * @k : key == index i's value, temp for swap 
         */
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

        printa(a);
    }

    @Test
    public void runA() {
        // TODO Auto-generated method stub
        int a[] = {68, 32, 31, 26, 14, 9, 7, 2};

        insertionSortA(a.clone());
        insertionSortWithWhile(a.clone());
    }
}
