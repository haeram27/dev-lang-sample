package com.example.sample.base.tree;

import org.junit.jupiter.api.Test;

public class BstSecondLargestNode {
    /* Class to represent Tree node */
    class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
        }
    }

    void smallestValueNodeInBst(Node root) {
        // TODO:

        System.out.println("smallest: ");
    }

    // find largest value in BST
    void largestValueNodeInBst(Node root) {
        // TODO:

        System.out.println("largest: ");
    }

    // find second largest value in BST
    void secondLargestValueNodeInBst(Node root) {
        // TODO:

        System.out.println("second largest: ");
    }

    /*
    @formatter: off
            10
         /     \
        5        20
      /   \    /
     1     7  15   
    @formatter: on    
    */
    @Test
    public void run() {
        // creating a binary tree and entering the nodes
        Node root = new Node(10);
        root.left = new Node(5);
        root.right = new Node(20);
        root.left.left = new Node(1);
        root.left.right = new Node(5);
        root.right.left = new Node(15);

        smallestValueNodeInBstA(root);
        largestValueNodeInBstA(root);
        secondLargestValueNodeInBstA(root);
    }

    void smallestValueNodeInBstA(Node root) {
        if (root == null)
            return;

        while (root.left != null) {
            root = root.left;
        }

        System.out.println("smallest: " + root.data);
    }

    // find largest value in BST
    void largestValueNodeInBstA(Node root) {
        if (root == null)
            return;

        while (root.right != null) {
            root = root.right;
        }

        System.out.println("largest: " + root.data);
    }

    // find second largest value in BST
    void secondLargestValueNodeInBstA(Node root) {
        if (root == null)
            return;

        Node parent = null;
        while (root.right != null) {
            parent = root;
            root = root.right;
        }

        int second = -1;
        if (root.left != null) {
            second = root.left.data;
        } else if (parent != null) {
            second = parent.data;
        }

        System.out.println("second largest: " + second);
    }

    /*
    @formatter: off
            10
         /     \
        5        20
      /   \    /
     1     7  15   
    @formatter: on    
    */
    @Test
    public void runA() {
        // creating a binary tree and entering the nodes
        Node root = new Node(10);
        root.left = new Node(5);
        root.right = new Node(20);
        root.left.left = new Node(1);
        root.left.right = new Node(5);
        root.right.left = new Node(15);

        smallestValueNodeInBstA(root);
        largestValueNodeInBstA(root);
        secondLargestValueNodeInBstA(root);
    }
}
