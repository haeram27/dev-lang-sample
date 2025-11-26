package com.example.sample.algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DFSRecursiveTests {
    class Graph {
        private int V;
        private List<List<Integer>> adj;
        private boolean[] visited;

        Graph(int v) {
            V = v;
            visited = new boolean[V];
            adj = new ArrayList<List<Integer>>();
            for (int i = 0; i < v; ++i) {
                adj.add(new ArrayList<Integer>());
            }
        }

        void addEdge(int s, int n) {
            adj.get(s).add(n);
        }

        // recursive can be replace with stack
        void DFSRecursiveTraversal(int s, boolean[] visited) {
            visited[s] = true;
            System.out.print(s + " ");

            for (int n : adj.get(s)) {
                if (!visited[n])
                    DFSRecursiveTraversal(n, visited);
            }
        }

        void DFS(int v) {
            Arrays.fill(visited, false);
            DFSRecursiveTraversal(v, visited);
            System.out.println();
        }

        void DFS() {
            for (int i = 0; i < V; ++i) {
                Arrays.fill(visited, false);
                DFSRecursiveTraversal(i, visited);
                System.out.println();
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
