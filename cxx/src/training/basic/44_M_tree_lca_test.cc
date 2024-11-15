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

class TreeLCATest : public ::testing::Test {
   protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    TreeLCATest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~TreeLCATest() {
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
Node* find_lca(Node *node, int n1, int n2) {
    // find LCA(lowest common ancestor)
    // if only one item of n1 or n2 exists in a tree then existing one is LCA.

    return nullptr;
}

Node* find_lca_answer_optimize(Node *node, int n1, int n2) {
    // find LCA(lowest common ancestor)
    // if only one item of n1 or n2 exists in a tree then existing one is LCA.

    if (!node) return nullptr;

    if (node->data == n1 || node->data == n2) return node;  // node is LCA.

    auto left = find_lca(node->left, n1, n2);
    auto right = find_lca(node->right, n1, n2);
    if (left && right) return node; // node is LCA

    return left ? left : right; // raise founded LCA in sub tree
}

Node* find_lca_answer(Node *node, int n1, int n2) {
    // find LCA(lowest common ancestor)
    // if only one item of n1 or n2 exists in a tree then existing one is LCA.

    // Base case 
    if (node == nullptr)
        return nullptr;

    // If either n1 or n2 matches with node's data, report 
    // the presence by returning node (Note that if a data is 
    // ancestor of other, then the ancestor data becomes LCA 
    if (node->data == n1 || node->data == n2)
        return node;

    // Look for datas in left and right subtrees 
    Node *left_lca = find_lca(node->left, n1, n2);
    Node *right_lca = find_lca(node->right, n1, n2);

    // If both of the above calls return Non-NULL, then one data 
    // is present in once subtree and other is present in other, 
    // So this node is the LCA
    if (left_lca != nullptr && right_lca != nullptr)
        return node;

    // Otherwise check if left subtree or right subtree is LCA 
    return (left_lca != nullptr) ? left_lca : right_lca;
}

TEST_F(TreeLCATest, test) 
{STARTTIME

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

    cout << find_lca(root, 4, 3)->data << endl;    // 1
    cout << find_lca(root, 7, 6)->data << endl;    // 3
    cout << find_lca(root, 6, 9)->data << endl;    // 6
    // cout << find_lca(root, 9, 10)->data << endl;    // nullptr (segmentation fault)

    cout << endl;

ENDTIME}

}  // namespace
