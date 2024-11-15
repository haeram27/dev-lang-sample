#include <bits/stdc++.h>

#include "gtest/gtest.h"

namespace {

/***** DO NOT REMOVE *****/
#define STARTTIME ElapsedTime et;
#define ENDTIME et.elapsed();
using namespace std::chrono;
class ElapsedTime {
   public:
    ElapsedTime() { steadyclk_start = steady_clock::now(); }
    void elapsed() {
        steadyclk_end = steady_clock::now();
        std::cout << "[**********] Elapsed time: "
                  << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
                  << " us.\n";
    }

   private:
    steady_clock::time_point steadyclk_start;
    steady_clock::time_point steadyclk_end;
};

/***** GLOBAL UNIT CONTEXT UNDER HERE *****/

class BfsTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    BfsTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~BfsTest() {
        // You can do clean-up work that doesn't throw exceptions here.
    }

    virtual void SetUp() override {
        // Code here will be called immediately after each test
        // (right before each test).
    }

    virtual void TearDown() override {
        // Code here will be called immediately after each test
        // (right before the destructor).
        // No exception handling allowed here.
    }
};

using namespace std;

/***** TEST UNIT CONTEXT UNDER HERE *****/

/**
 * @param graph     graph
 * @param start     start vertex
*/

// bfs is traversal using queue(level-order) + visited
void bfs(vector<vector<int>>& graph, int start) {
    return;
}

void bfs_answer(vector<vector<int>>& graph, int start) {
    // step.1. create queue to process visited vertex
    // queue is used for ordering of next visited node
    // queue's elements means each node(int)
    list<int> q;

    // step.2. create container to check visited bool
    // visited vector's index means each node(int)
    vector<bool> visited(graph.size());

    // step.3. enqueue start vertext 
    q.push_front(start);
    visited[start] = true;

    /**
     * q(queue) : contains adjacent nodes of each passed nodes
     * while : pop next node in q(queue)
     * for : to push all of adjcent nodes of current node to q(queue)
     * visited : to check whether adjcent node is pushed(visited) before in q(queue)
     */
    // step.4. process vertext ordered by FIFO(queue)
    while (q.size()) {
        /* step.5. pop current vertex and process it
                x is node value (int)
                     index of graph
                     current and visited(enqueued) vertex
                     1st x is start
        */
        int x = q.back();
        q.pop_back();
        cout << x << " ";

        // step.5. push adjacents of current vertext to the queue
        for (const auto& e : graph[x]) { // e is adjcents of vertex x
            if (!visited[e]) {  
                q.push_front(e);
                visited[e] = true;
            }
        }
    }

    return;
}


TEST_F(BfsTest, run) 
{STARTTIME

/* graph ========================
 * 1 : 2 3 8
 * 2 : 1 7
 * 3 : 1 4 5
 * 4 : 3 5
 * 5 : 3 4
 * 6 : 7
 * 7 : 2 6 8
 * 8 : 1 7
 * 
*/
    vector<vector<int>> graph(9);

    graph[1].push_back(2);
    graph[1].push_back(3);
    graph[1].push_back(8);

    graph[2].push_back(1);
    graph[2].push_back(7);

    graph[3].push_back(1);
    graph[3].push_back(4);
    graph[3].push_back(5);

    graph[4].push_back(3);
    graph[4].push_back(5);

    graph[5].push_back(3);
    graph[5].push_back(4);

    graph[6].push_back(7);

    graph[7].push_back(2);
    graph[7].push_back(6);
    graph[7].push_back(8);

    graph[8].push_back(1);
    graph[8].push_back(7);

    bfs(graph, 1);
    // bfs_answer(graph, 1);
    cout << endl;

ENDTIME}

}  // namespace
