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

class DfsTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    DfsTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~DfsTest() {
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

// dfs is traversal using stack(pre-order) + visited
void dfs(vector<vector<int>>& graph, int start) {
    return;
}

void dfs_answer(vector<vector<int>>& graph, int start) {
    list<int> sk;  // stack
    vector<bool> visited(graph.size());
    sk.push_front(start);
    visited[start] = true;

    /**
     * sk(stack) : contains adjacent nodes of each passed nodes
     * while : pop next node in sk(stack)
     * for : to push all of adjcent nodes of current node to sk(stack)
     * visited : to check whether adjcent node is pushed(visited) before into sk(stack)
     */
    while (sk.size()) {
        int x = sk.front(); // x is visited (in stack) vertex
        sk.pop_front();
        cout << x << ' ';

        for (int i = 0; i < graph[x].size(); i++) {
            int y = graph[x][i];    // y is adjcent of vertex x
            if (!visited[y]) {
                sk.push_front(y);
                visited[y] = true;
            }
        }
    }
}


TEST_F(DfsTest, run) 
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

    dfs(graph, 1);
    cout << endl;

ENDTIME}

}  // namespace
