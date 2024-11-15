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

class TreePreOrderTest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TreePreOrderTest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TreePreOrderTest() {
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

// Function to print pre order traversal of a given binary tree
void preorder(Node* root) {
    return;
}

void preorder_answer(Node* root) {
    // terminate recursive. if root is null.
    if (!root) return;  // if (root == nullptr)  return;

    // print data
    cout << root->data << " ";

    // search left sub tree
    preorder(root->left);

    // search right sub tree
    preorder(root->right);
}


TEST_F(TreePreOrderTest, run) {
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

    // pre : root > left > right
    //       1 2 4 3 "5 7 8" 6
    preorder(root);

    cout << endl;

    ENDTIME
}

}  // namespace
