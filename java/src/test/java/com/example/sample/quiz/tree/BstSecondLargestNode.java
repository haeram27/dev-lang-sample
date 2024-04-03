package com.example.sample.quiz.tree;

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

    Node largest;
    Node parent;

    // find second largest value in BST
    void secondLargestNodeInBstA(Node root) {
        if (root == null)
            return;

        if (root.right != null) {
            parent = root;
            secondLargestNodeInBstA(root.right);
        } else if (root.left != null) {
            parent = root;
            secondLargestNodeInBstA(root.left);
        }
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

        secondLargestNodeInBstA(root);
        System.out.println(parent.data);
    }
}
