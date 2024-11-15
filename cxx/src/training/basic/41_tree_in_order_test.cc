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

class TreeInOrderTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TreeInOrderTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TreeInOrderTest() {
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

// Recursive function to perform inorder traversal on the tree
void inorder(Node* root) {
    return;
}

void inorder_answer(Node* root) {
    // return if the current node is empty
    if (!root) return;  // if (root == nullptr)  return;

    // Traverse the left subtree
    inorder(root->left);

    // Display the data part of the root (or current node)
    cout << root->data << " ";

    // Traverse the right subtree
    inorder(root->right);
}

TEST_F(TreeInOrderTest, run) {
    STARTTIME

    /* Construct the following tree
               1
             /   \
            /     \
           2       3
          /      /   \
         /      /     \
        4      5       6
              / \
             /   \
            7     8
    */

    Node* root = new Node(1);
    root->left = new Node(2);
    root->right = new Node(3);
    root->left->left = new Node(4);
    root->right->left = new Node(5);
    root->right->right = new Node(6);
    root->right->left->left = new Node(7);
    root->right->left->right = new Node(8);

    // in : left > root > right
    //      4 2 1 "7 5 8" 3 6
    inorder(root);

    cout << endl;

    ENDTIME
}

}  // namespace
