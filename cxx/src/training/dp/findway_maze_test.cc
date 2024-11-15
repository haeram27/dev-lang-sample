#include <bits/stdc++.h>

#include "gtest/gtest.h"

using namespace std;
using matrix_t = vector<vector<int>>;

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

class FindWayOfMazeTest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    FindWayOfMazeTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~FindWayOfMazeTest() {
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


/***** TEST UNIT CONTEXT UNDER HERE *****/
static int N = 8;
static int GOAL_X = N - 1;
static int GOAL_Y = N - 1;
static int ROADS_COLOR = 0;
static int WALL_COLOR = 1;
static int BLOCKED_COLOR = 2;
static int PATH_COLOR = 3;

bool boundCheck(int value) {
    return (value >= 0 && value <= (N - 1)) ? true : false;
}

void findMazePath(uint startx, uint starty, matrix_t& maze) {
    pair<int, int> point{startx, starty};
    stack<pair<int, int>> stk;
    stk.push(point);

    while (!stk.empty()) {
        pair<int, int> current = stk.top(); stk.pop();
        int x = current.first;
        int y = current.second;

        if (x < 0 || y < 0 || x > N - 1 || y > N - 1) {
            cout << (x + "," + y) << endl;
            continue;
        } else if (x == GOAL_X && y == GOAL_Y) {
            // here is GOAL!
            maze[x][y] = PATH_COLOR;
            break;
        } else if (maze[x][y] == PATH_COLOR) {
            // this is backtracked point
            maze[x][y] = BLOCKED_COLOR;
            continue;
        } else {
            // mark here as PATH
            maze[x][y] = PATH_COLOR;
            stk.emplace(x, y);  // For BackTracking

            /*
             * If there is other ROAD(0) on up/down/left/right from here(PATH(3))
             * then here is PATH.
             */
            if (boundCheck(x + 1) && maze[x + 1][y] == ROADS_COLOR) stk.emplace(x + 1, y);
            if (boundCheck(y + 1) && maze[x][y + 1] == ROADS_COLOR) stk.emplace(x, y + 1);
            if (boundCheck(x - 1) && maze[x - 1][y] == ROADS_COLOR) stk.emplace(x - 1, y);
            if (boundCheck(y - 1) && maze[x][y - 1] == ROADS_COLOR) stk.emplace(x, y - 1);
        }
    }
}

/* Driver code */
TEST_F(FindWayOfMazeTest, runa)
{STARTTIME

/** ON WAY */
matrix_t maze = {
    {0, 0, 0, 0, 0, 0, 0, 1},
    {0, 1, 1, 0, 1, 1, 0, 1},
    {0, 0, 0, 1, 0, 0, 0, 1},
    {0, 1, 0, 0, 1, 1, 0, 0},
    {0, 1, 1, 1, 0, 0, 1, 1},
    {0, 1, 0, 0, 0, 1, 0, 1},
    {0, 0, 0, 1, 0, 0, 0, 1},
    {0, 1, 1, 1, 0, 1, 0, 0}};

/** NO WAY */ /*
matrix_t maze = {
    { 0, 0, 0, 0, 0, 0, 0, 1 },
    { 0, 1, 1, 0, 1, 1, 0, 1 },
    { 0, 0, 0, 1, 0, 0, 0, 1 },
    { 0, 1, 0, 0, 1, 1, 0, 0 },
    { 0, 1, 1, 1, 0, 0, 1, 1 },
    { 0, 1, 0, 0, 0, 1, 0, 1 },
    { 0, 0, 0, 1, 0, 0, 0, 1 },
    { 0, 1, 1, 1, 0, 1, 1, 0 }};
 */

findMazePath(0, 0, maze);
for (int r = 0; r < N; r++) {
    for (int c = 0; c < N; c++) {
        cout << maze[r][c] << " ";
    }
    cout << endl;
    }

ENDTIME}

} ///namespace

