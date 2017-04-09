package com.hackerrank.search;

public class SearchEngine {

    public static void main(String[] args) {
        String[] input = new String[]{"he", "heman", "man", "his"};
        Trie trie = Trie.initializeTrie(input);
        System.out.println(trie);
        System.out.println(trie.search("heheimanmanhehis"));
    }
}
