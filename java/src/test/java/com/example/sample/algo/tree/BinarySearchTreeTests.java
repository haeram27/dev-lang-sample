package com.example.sample.algo.tree;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTests {
    /* Class to represent Tree node */
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
        }
    }

    // A recursive function to
    // insert a new key in BST
    Node insert(Node root, int key) {
        // TODO:

        return root;
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
        Node root = insertA(null, 10);
        insert(root, 5);
        insert(root, 20);
        insert(root, 1);
        insert(root, 5);
        insert(root, 15);

        smallestValueNodeInBst(root);
        largestValueNodeInBst(root);
        secondLargestValueNodeInBst(root);
        preorder(root);
        System.out.println();
        inorder(root);
        System.out.println();
        postorder(root);
        System.out.println();
    }

    /*
     * Answer
     */

    // traversal time complexity O(n)
    void preorder(Node root) {
        if (root == null)
            return;
        System.out.print(root.key + " ");
        inorder(root.left);
        inorder(root.right);
    }

    // IN BST, inorder-traversal prints sorted values
    // traversal time complexity O(n)
    void inorder(Node root) {
        if (root == null)
            return;
        inorder(root.left);
        System.out.print(root.key + " ");
        inorder(root.right);
    }

    // traversal time complexity O(n)
    void postorder(Node root) {
        if (root == null)
            return;
        inorder(root.left);
        inorder(root.right);
        System.out.print(root.key + " ");
    }

    // A recursive function to
    // insert a new key in BST
    Node insertA(Node root, int key) {
        // If the tree is empty,
        // return a new node
        if (root == null) {
            root = new Node(key);
            System.out.println("created");
            return root;
        }

        // Otherwise, recur down the tree
        else if (key < root.key)
            root.left = insertA(root.left, key);
        else if (key > root.key)
            root.right = insertA(root.right, key);

        // Return the (unchanged) node pointer
        return root;
    }

    /*
     * time complexity O(n)
     * balanced binary tree O(h)
     * not balanced bst O(n)
     */
    void smallestValueNodeInBstA(Node root) {
        if (root == null)
            return;

        while (root.left != null) {
            root = root.left;
        }

        System.out.println("smallest: " + root.key);
    }

    // find largest value in BST
    void largestValueNodeInBstA(Node root) {
        if (root == null)
            return;

        while (root.right != null) {
            root = root.right;
        }

        System.out.println("largest: " + root.key);
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
            second = root.left.key;
        } else if (parent != null) {
            second = parent.key;
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
        Node root = insertA(null, 10);
        insertA(root, 5);
        insertA(root, 20);
        insertA(root, 1);
        insertA(root, 5);
        insertA(root, 15);

        smallestValueNodeInBstA(root);
        largestValueNodeInBstA(root);
        secondLargestValueNodeInBstA(root);
        preorder(root);
        System.out.println();
        inorder(root);
        System.out.println();
        postorder(root);
        System.out.println();
    }
}
