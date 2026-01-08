package com.example.sample.algo.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

// BFS traverse graph with queue
public class M_BFSTests {
    /* directional graph class using adjacency list */
    class Graph {
        private int numberOfVertex;
        private List<List<Integer>> adj; // adjacency list

        /** Constructor **/
        Graph(int size) {
            // initialize number of vertexes
            numberOfVertex = size;

            // initialize adjacency list
            adj = new ArrayList<List<Integer>>();
            for (int i = 0; i < size; ++i) {
                adj.add(new ArrayList<Integer>());
            }
        }

        /** 
         * connect links s->n 
         *  s - start node
         *  n - adjacent node
         */
        void addEdge(int s, int n) {
            adj.get(s).add(n);
        }

        /**
         * BFS traversal using Queue
         * @param s start vertex
         * @param n number of vertex
         * @param g graph
         */
        void BFSTraversal(int s, int n, List<List<Integer>> g) {
            // TODO
        }

        /**
         * Answer:
         * BFS traversal using Queue
         * @param s start vertex
         * @param n number of vertex
         * @param g graph
         */
        void BFSTraversalA(int s, int n, List<List<Integer>> g) {
            // visited : to check vertext is already comsumed(enqueue or enstack) or not
            boolean[] visited = new boolean[n];
            visited[s] = true;

            // queue : to make order(FIFO) of next visiting vertex
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.offer(s);

            // traversal graph with queue
            while (!queue.isEmpty()) {
                // get current vertext
                int cur = queue.poll();
                System.out.print(cur + " ");

                // get adjacent of current vertex
                for (int next : g.get(cur)) {
                    if (!visited[next]) {
                        // if vertex is visited(consumed) once it can NOT be visited again
                        visited[next] = true;
                        // insert next vertex to visit into queue(FIFO order)
                        queue.offer(next);
                    }
                }
            }
            System.out.println();
        }

        void BFS(int start) {
            BFSTraversal(start, numberOfVertex, adj);
        }

        void BFS() {
            for (int start = 0; start < numberOfVertex; ++start) {
                BFSTraversal(start, numberOfVertex, adj);
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
        g.BFS(0); /* BFS searching start from given node */
        g.BFS();
    }
}
