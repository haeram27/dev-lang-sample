package com.example.sample.base.ds;

import org.junit.jupiter.api.Test;

public class SingleLinkedListTests {

    class Node {
        int data;
        Node next;

        Node(int d) {
            this.data = d;
        }
    }

    // prints content of double linked list
    void printList(Node node) {
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }

    /* quest */
    /* Function to reverse the linked list */
    Node reverse(Node head) {
        // TODO:

        return head; // head;
    }

    @Test
    void run() {
        Node head = new Node(5);
        head.next = new Node(4);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);

        Node r = reverse(head);

        printList(r);
    }

    /* Function to reverse the linked list */
    Node reverseA(Node head) {
        Node cur = head; // current
        Node prev = null;
        Node next = null;

        // key: for loop, final insertion is cur=next;
        while (cur != null) {
            // n = cn = p = c = n
            // next = cur.next = prev = cur = next
            next = cur.next; // next will be current
            cur.next = prev;
            prev = cur; // for next loop, in last loop prev
            cur = next; // for next loop
        }

        head = prev;
        return head;
    }

    @Test
    void runA() {
        Node head = new Node(5);
        head.next = new Node(4);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);

        Node r = reverseA(head);

        printList(r);
    }

}
