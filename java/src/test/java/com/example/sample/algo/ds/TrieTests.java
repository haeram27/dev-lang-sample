package com.example.sample.algo.ds;

import org.junit.jupiter.api.Test;

public class TrieTests {
    class TrieNode {
        // Alphabet size (# of symbols)
        static final int ALPHABET_SIZE = 26;

        /** link to children TrieNode */
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        /** 
         * isEndOfWord is true if the node represents
         * End Of a Word
         */
        boolean isEndOfWord;
    };

    TrieNode _root;

    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    void insert(String key) {
        if (key == null || key.isEmpty())
            return;

        int len = key.length();
        TrieNode node = _root;

        for (int level = 0; level < len; ++level) {
            int idx = key.charAt(level) - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }

        // mark last node as leaf
        node.isEndOfWord = true;
    }

    // Returns true if key presents in trie, else false
    boolean search(String key) {
        if (key == null || key.isEmpty())
            return false;

        int len = key.length();
        TrieNode node = _root;

        for (int level = 0; level < len; ++level) {
            int idx = key.charAt(level) - 'a';
            if (node.children[idx] == null) {
                return false;
            }
            node = node.children[idx];
        }

        return node.isEndOfWord;
    }

    @Test
    public void run() {
        // Input keys (use only 'a' through 'z' and lower case)
        String keys[] = {"this", "these", "there", "apple", "appoint"};
        String output[] = {"Not present in trie", "Present in trie"};

        _root = new TrieNode();

        // Construct trie
        for (int i = 0; i < keys.length; i++)
            insert(keys[i]);

        // Search for different keys
        if (search("these") == true)
            System.out.println("these --- " + output[1]);
        else
            System.out.println("these --- " + output[0]);

        if (search("those") == true)
            System.out.println("those --- " + output[1]);
        else
            System.out.println("those --- " + output[0]);

        if (search("apple") == true)
            System.out.println("apple --- " + output[1]);
        else
            System.out.println("apple --- " + output[0]);

        if (search("apply") == true)
            System.out.println("apply --- " + output[1]);
        else
            System.out.println("apply --- " + output[0]);
    }
}
