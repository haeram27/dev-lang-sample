package com.example.sample.algo.sort;

import org.junit.jupiter.api.Test;

public class HeapSortTests {
    void sort(int a[]) {
        int len = a.length;

        // Build heap (rearrange array)
        buildHeap(a);

        // One by one extract an element from heap
        for (int i = len - 1; i >= 0; --i) {
            // Move current root to end
            swap(a, i, 0);
            // call max heapify on the reduced heap
            heapify(a, i, 0);
        }
    }

    // make array as MAX heap arrange
    void buildHeap(int a[]) {
        int len = a.length;

        // (len/2-1) is index of last parent node in total tree
        for (int i = len / 2 - 1; i >= 0; i--)
            heapify(a, len, i);
    }

    /**
     * Heapify for MaxHeap
     * a[len] - len is size of heap array
     * p - index of parent node
     * l - index of left child node
     * r - index of right child node
     * largest - index of node which has largest value among p, l, r
     */
    void heapify(int a[], int len, int p) {
        int largest = p; // Initialize largest_index as parent index
        int l = 2 * p + 1; // left = 2*p + 1
        int r = l + 1; // right = 2*p + 2

        // If left child is larger than parent
        if (l < len && a[l] > a[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < len && a[r] > a[largest])
            largest = r;

        // If root is not largest
        if (p != largest) {
            swap(a, p, largest);
            // Value of largest(left or right Child) is changed
            // so subtree which has left or right Child as parent
            // should be processed heapify also.
            // Recursively heapify the affected sub-tree
            heapify(a, len, largest);
        }
    }

    public void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /* A utility function to print array of size n */
    void printarray(int a[]) {
        int n = a.length;
        for (int i = 0; i < n; ++i)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    @Test
    public void run() {
        int a[] = {12, 11, 13, 5, 6, 7};

        HeapSortTests ob = new HeapSortTests();
        ob.sort(a);

        System.out.println("Sorted array is");
        printarray(a);
    }
}
