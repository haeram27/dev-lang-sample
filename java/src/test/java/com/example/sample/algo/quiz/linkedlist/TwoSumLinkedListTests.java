package com.example.sample.algo.quiz.linkedlist;

import org.junit.jupiter.api.Test;

class Node {
    int data;
    Node next;

    Node() {}

    Node(int data) {
        this.data = data;
    }
}


public class TwoSumLinkedListTests {
    // https://leetcode.com/problems/add-two-numbers/description/

    void printNode(Node n) {
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.next;
        }
        System.out.println();
    }

    Node nodeSumTest(Node l1, Node l2) {
        // TODO:
        Node dummy = null;

        return dummy.next;
    }

    @Test
    void run1() {
        Node a, b, c;
        Node l1, l2;

        // Input: l1 = [2, 4, 3], l2 = [5, 6, 4]
        // Output: [7, 0, 8]

        a = new Node(2);
        b = new Node(4);
        c = new Node(3);
        a.next = b;
        b.next = c;
        l1 = a;
        printNode(l1);

        a = new Node(5);
        b = new Node(6);
        c = new Node(4);
        a.next = b;
        b.next = c;
        l2 = a;
        printNode(l2);

        Node n = nodeSumTest(l1, l2);
        printNode(n);
    }

    @Test
    void run2() {
        Node a;
        Node l1, l2;

        // Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
        // Output: [8,9,9,9,0,0,0,1]

        var dummy = new Node(9);
        dummy.next = new Node(9);
        a = dummy.next;
        for (int i = 0; i < 6; i++) {
            a.next = new Node(9);
            a = a.next;
        }
        l1 = dummy.next;
        dummy = null;
        printNode(l1);

        dummy = new Node(9);
        dummy.next = new Node(9);
        a = dummy.next;
        for (int i = 0; i < 3; i++) {
            a.next = new Node(9);
            a = a.next;
        }
        l2 = dummy.next;
        dummy.next = null;
        dummy = null;
        printNode(l2);

        Node n = nodeSumTest(l1, l2);
        printNode(n);
    }

    Node nodeSumTestA(Node l1, Node l2) {
        // ANSWER:
        Node dummy = new Node(0), last = dummy;
        int c = 0; //carry

        while (l1 != null || l2 != null || c > 0) {
            int v1 = (l1 == null) ? 0 : l1.data;
            int v2 = (l2 == null) ? 0 : l2.data;
            int v = v1 + v2 + c;

            c = v / 10;
            last.next = new Node(v % 10);
            last = last.next;

            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }

        return dummy.next;
    }

    @Test
    void runA1() {
        Node a, b, c;
        Node l1, l2;

        // Input: l1 = [2, 4, 3], l2 = [5, 6, 4]
        // Output: [7, 0, 8]

        a = new Node(2);
        b = new Node(4);
        c = new Node(3);
        a.next = b;
        b.next = c;
        l1 = a;
        printNode(l1);

        a = new Node(5);
        b = new Node(6);
        c = new Node(4);
        a.next = b;
        b.next = c;
        l2 = a;
        printNode(l2);

        Node n = nodeSumTestA(l1, l2);
        printNode(n);
    }

    @Test
    void runA2() {
        Node a;
        Node l1, l2;

        // Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
        // Output: [8,9,9,9,0,0,0,1]

        var dummy = new Node(9);
        dummy.next = new Node(9);
        a = dummy.next;
        for (int i = 0; i < 6; i++) {
            a.next = new Node(9);
            a = a.next;
        }
        l1 = dummy.next;
        dummy = null;
        printNode(l1);

        dummy = new Node(9);
        dummy.next = new Node(9);
        a = dummy.next;
        for (int i = 0; i < 3; i++) {
            a.next = new Node(9);
            a = a.next;
        }
        l2 = dummy.next;
        dummy.next = null;
        dummy = null;
        printNode(l2);

        Node n = nodeSumTestA(l1, l2);
        printNode(n);
    }

}
