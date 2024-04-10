package com.example.sample.base.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Test;

public class TreeTraversalTests {
    /* Class to represent Tree node */
    class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
        }
    }

    // BFS == queue, Level Order Traversal
    void levelOrderTraversal(Node root) {
        // TODO:
    }

    void reverseLevelOrderTraversal(Node root) {
        // TODO:
    }

    // DFS == stack, preOrder, inOrder, postOrder
    // use stack for this question
    void dfsTraversal(Node root) {
        // TODO:
    }

    void preOrderTraversal(Node root) {
        // TODO:
    }

    void inOrderTraversal(Node root) {
        // TODO:
    }

    void postOrderTraversal(Node root) {
        // TODO:
    }

    // max number of link (count leaf from 0)
    public int getMaxDepth(Node root) {
        // TODO:
        return 0;
    }

    // max level of node (count leaf from 1)
    public int getMaxLevel(Node root) {
        // TODO:
        return 0;
    }

    //find Lowest Common Ancester
    Node findLCA(Node root, int n1, int n2) {
        // TODO:
        return null;
    }

    @Test
    public void run() {
        // creating a binary tree and entering the nodes
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println("Level order: ");
        levelOrderTraversal(root);
        System.out.println();

        System.out.println("Reverse Level order: ");
        reverseLevelOrderTraversal(root);
        System.out.println();

        System.out.println("dfs order: ");
        dfsTraversal(root);
        System.out.println();

        System.out.println("pre order: ");
        preOrderTraversal(root);
        System.out.println();

        System.out.println("in order: ");
        inOrderTraversal(root);
        System.out.println();

        System.out.println("post order: ");
        postOrderTraversal(root);
        System.out.println();

        System.out.println("MaxDepth: ");
        System.out.println(getMaxDepth(root));

        System.out.println("MaxLevel: ");
        System.out.println(getMaxLevel(root));

        System.out.println("findLCA: ");
        var lca = findLCA(root, 4, 5);
        if (lca != null)
            System.out.println(lca.data);
    }

    // BFS == queue, Level Order Traversal
    void levelOrderTraversalA(Node root) {
        if (root == null)
            return;

        Deque<Node> q = new ArrayDeque<Node>();

        q.offer(root);
        while (!q.isEmpty()) {
            Node node = q.poll();
            System.out.print(node.data + " ");

            if (node.left != null)
                q.offer(node.left);
            if (node.right != null)
                q.offer(node.right);
        }
    }

    void reverseLevelOrderTraversalA(Node root) {
        if (root == null)
            return;

        Deque<Node> q = new ArrayDeque<>();
        Deque<Integer> stack = new ArrayDeque<>();
        Node node;

        q.offer(root);
        while (!q.isEmpty()) {
            node = q.poll();
            stack.push(node.data);

            if (node.right != null)
                q.offer(node.right);
            if (node.left != null)
                q.offer(node.left);
        }

        // pop all nodes from the stack and print them
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    // DFS == stack, preOrder, inOrder, postOrder
    void dfsTraversalA(Node root) {
        if (root == null)
            return;

        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.data + " ");
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
    }

    void preOrderTraversalA(Node root) {
        if (root == null)
            return;

        System.out.print(root.data + " ");
        preOrderTraversalA(root.left);
        preOrderTraversalA(root.right);
    }

    void inOrderTraversalA(Node root) {
        if (root == null)
            return;

        inOrderTraversalA(root.left);
        System.out.print(root.data + " ");
        inOrderTraversalA(root.right);
    }

    void postOrderTraversalA(Node root) {
        if (root == null)
            return;

        postOrderTraversalA(root.left);
        postOrderTraversalA(root.right);
        System.out.print(root.data + " ");
    }

    // max number of link (count leaf from 0)
    public int getMaxDepthA(Node root) {
        if (root == null)
            return 0;

        // Leaf Node This state except leaf from count
        if (root.left == null && root.right == null)
            return 0;

        int l = getMaxDepthA(root.left);
        int r = getMaxDepthA(root.right);

        return (l > r) ? l + 1 : r + 1;
    }

    // max level of node (count leaf from 1)
    public int getMaxLevelA(Node root) {
        if (root == null)
            return 0;

        // Leaf Node:: This accounts for height = 1.
        if (root.left == null && root.right == null)
            return 1;

        int l = getMaxLevelA(root.left);
        int r = getMaxLevelA(root.right);

        return (l > r) ? l + 1 : r + 1;
    }

    //findLowestCommonAncester
    Node findLCAA(Node root, int n1, int n2) {
        // Base case 
        if (root == null)
            return null;

        // If either n1 or n2 matches with root's key, report 
        // the presence by returning root (Note that if a key is 
        // ancestor of other, then the ancestor key becomes LCA 
        if (root.data == n1 || root.data == n2)
            return root;

        // Look for keys in left and right subtrees 
        Node l = findLCAA(root.left, n1, n2);
        Node r = findLCAA(root.right, n1, n2);

        // If both of the above calls return Non-NULL, then one key 
        // is present in once subtree and other is present in other, 
        // So this node is r LCA 
        if (l != null && r != null)
            return root;

        // Otherwise check if left subtree or right subtree is LCA 
        return (l != null) ? l : r;
    }

    @Test
    public void runA() {
        // creating a binary tree and entering the nodes
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println("Level order(bfs): ");
        levelOrderTraversalA(root);
        System.out.println();

        System.out.println("Reverse Level order: ");
        reverseLevelOrderTraversalA(root);
        System.out.println();

        System.out.println("dfs order: ");
        dfsTraversalA(root);
        System.out.println();

        System.out.println("pre order: ");
        preOrderTraversalA(root);
        System.out.println();

        System.out.println("in order: ");
        inOrderTraversalA(root);
        System.out.println();

        System.out.println("post order: ");
        postOrderTraversalA(root);
        System.out.println();

        System.out.println("MaxDepth: ");
        System.out.println(getMaxDepthA(root));

        System.out.println("MaxLevel: ");
        System.out.println(getMaxLevelA(root));

        System.out.println("findLCA: ");
        var lca = findLCAA(root, 4, 5);
        if (lca != null)
            System.out.println(lca.data);
    }
}
