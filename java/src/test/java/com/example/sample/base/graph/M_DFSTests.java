package com.example.sample.base.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class M_DFSTests {
    class Graph {
        private int numberOfVertex;
        private List<List<Integer>> adj;

        Graph(int size) {
            numberOfVertex = size;
            adj = new ArrayList<List<Integer>>();
            for (int i = 0; i < size; ++i) {
                adj.add(new ArrayList<Integer>());
            }
        }

        void addEdge(int src, int dest) {
            adj.get(src).add(dest);
        }

        /**
         * DFS traversal using Stack
         * @param s start vertex
         * @param n number of vertex
         * @param g graph
         */
        void DFSTraversal(int s, int n, List<List<Integer>> g) {
            // TODO
        }

        /**
         * Answer:
         * DFS traversal using Stack
         * @param s start vertex
         * @param n number of vertex
         * @param g graph
         */
        void DFSTraversalA(int s, int n, List<List<Integer>> g) {
            // visited : to check vertext is already comsumed(enqueue or enstack) or not
            boolean[] visited = new boolean[n];
            visited[s] = true;

            // stack : to make order(LIFO) of next visiting vertex
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            stack.push(s);

            // traversal graph with stack
            while (!stack.isEmpty()) {
                // get current visited vertex
                int cur = stack.pop();
                System.out.print(cur + " ");

                // get adjcents of current visited vertex
                for (int next : g.get(cur)) {
                    if (!visited[next]) {
                        // if vertex is visited(consumed) once it can NOT be visited again
                        visited[next] = true;
                        // insert next candidate vertex to visit into stack(LIFO order)
                        stack.push(next);
                    }
                }
            }
            System.out.println();
        }

        // !!! ReverseAdjecentOrder makes same order of recursive call
        void DFSTraversalReverseAdjecentOrder(int s, int n, List<List<Integer>> g) {
            boolean visited[] = new boolean[n];
            ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

            // initialize stack with start point
            visited[s] = true;
            stack.push(s);

            while (!stack.isEmpty()) {
                int cur = stack.pop();
                System.out.print(cur + " ");

                List<Integer> adjacents = new ArrayList<>(g.get(cur));
                Collections.reverse(adjacents);

                for (int next : adjacents) {
                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);
                    }
                }
            }
            System.out.println();
        }

        void DFS(int start) {
            DFSTraversal(start, numberOfVertex, adj);
        }

        void DFS() {
            for (int start = 0; start < numberOfVertex; start++) {
                DFSTraversal(start, numberOfVertex, adj);
            }
        }
    }

    @Test
    public void run() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 4);
        g.addEdge(1, 0);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 1);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 0);
        g.addEdge(4, 2);
        g.addEdge(4, 3);

        g.DFS(2);
        System.out.println();
        g.DFS();
    }
}
