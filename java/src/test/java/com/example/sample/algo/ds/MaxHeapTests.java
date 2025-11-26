package com.example.sample.algo.ds;

import org.junit.jupiter.api.Test;

public class MaxHeapTests {
    /**
     * array for heap data structure At this example, heap data is stored from index 0
     */
    private static final int MAX_HEAP_SIZE = 100;
    private static int[] maxHeap = new int[MAX_HEAP_SIZE];
    private static int heapSize = 0;

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void offer(int x) {
        // put x at the end of heap and increase heapSize by 1
        maxHeap[heapSize++] = x;

        // i begins index of last node
        for (int i = heapSize - 1; i > 0; i = (i - 1) / 2) {
            int parent = (i - 1) / 2;

            // if last node is bigger than parent then swap
            if (maxHeap[i] > maxHeap[parent]) {
                swap(maxHeap, i, parent);
            } else {
                break;
            }
        }
    }

    public static int poll() {
        // if Heap is Empty
        if (heapSize == 0)
            return -1;

        // save value of root(index 0), this value will be return
        int item = maxHeap[0];
        // copy value of last node to root
        maxHeap[0] = maxHeap[heapSize - 1];
        // reduce heap size by 1 and set 0 at last node as initialization
        maxHeap[--heapSize] = 0;

        // heapify (while i is one of parent node)
        for (int i = 0; i <= heapSize / 2 - 1;) {
            int leftChild = i * 2 + 1;
            int rightChild = leftChild + 1;

            // if last node is bigger than left and right child then escape loop
            if (maxHeap[i] > maxHeap[leftChild] && maxHeap[i] > maxHeap[rightChild]) {
                break;
            }
            // if left child is bigger than right child then swap parent and left
            else if (maxHeap[leftChild] > maxHeap[rightChild]) {
                swap(maxHeap, i, leftChild);
                i = leftChild;
            }
            // if not swap parent and right
            else {
                swap(maxHeap, i, rightChild);
                i = rightChild;
            }
        }
        return item;
    }

    @Test
    public void run() {
        int[] arr = {2, 7, 9, 14, 14, 26, 31, 32, 68};
        for (int i = 0; i < arr.length; ++i) {
            offer(arr[i]);
        }

        System.out.printf("Print Max Heap: Size(%d)\n", heapSize);
        while (heapSize > 0) {
            System.out.print(poll() + " ");
        }
    }
}
