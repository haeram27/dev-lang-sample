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

class TreeLevelOrderTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TreeLevelOrderTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TreeLevelOrderTest() {
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

// Data structure to store a binary tree node
struct Node {
    int data;
    Node *left, *right;

    Node(int data) {
        this->data = data;
        this->left = this->right = nullptr;
    }
};

// Function to print level order traversal of a given binary tree
void levelOrderTraversal(Node* root) {
    return;
}

void levelOrderTraversal_answer(Node* root) {
    // base case
    if (!root) return;  // if (root == nullptr)  return;

    /* level order use BFS !!!!!!!!!!!! */
    // create an empty queue and enqueue the root node
    list<Node*> q;
    q.push_back(root);


    // loop till queue is empty
    // q make order of acessing adjacent node(child nodes)
    while (q.size())  // while (!q.empty())
    {
        // process each node in the queue and enqueue their
        // non-empty left and right child
        // print front of queue and remove it from queue

        // Node* curr = nullptr;
        auto curr = q.front();
        q.pop_front();
        cout << curr->data << " ";

        // enqueue left child
        if (curr->left) q.push_back(curr->left);

        // enqueue left child
        if (curr->right) q.push_back(curr->right);
    }
}

TEST_F(TreeLevelOrderTest, run) {
    STARTTIME

    Node* root = new Node(15);
    root->left = new Node(10);
    root->right = new Node(20);
    root->left->left = new Node(8);
    root->left->right = new Node(12);
    root->right->left = new Node(16);
    root->right->right = new Node(25);

    levelOrderTraversal(root);  // 15 10 20 8 12 16 25

    cout << endl;

    ENDTIME
}

}  // namespace
