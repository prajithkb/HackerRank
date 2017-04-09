package com.hackerrank.search;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Trie {

    /**
     * The class which marks start and end of a word
     */
    class Indices {
        int start;
        int end;

        public String toString() {
            return String.format("[%s,%s]", start, end);
        }
    }

    private int state = 0;

    @Getter
    @Setter
    private Node root = new Node(state);

    List<Node> states = Lists.newArrayList();

    private Trie() {
        states.add(root);

    }

    static Trie initializeTrie(String[] words) {
        Trie trie = new Trie();
        for (int i = 0; i < words.length; i++) {
            trie.addWord(words[i]);
        }
        trie.buildFailures();
        return trie;
    }


    private void addWord(@NonNull String word) {
        addWord(root, 0, word.toLowerCase());
    }

    private void addWord(Node parent, Node child, Character character) {
        states.add(child);
        parent.add(character, child);
    }

    private void addWord(Node node, int index, String word) {
        if (index >= word.length()) {
            node.setWord(word);
            return;
        }
        Node currentNode = node.get(word.charAt(index));
        if (currentNode != null) {
            addWord(currentNode, ++index, word);
        } else {
            Node child = new Node(++state);
            addWord(node, child, word.charAt(index));
            addWord(child, ++index, word);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        states.forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }

    private void buildFailures() {
        root.setFailure(root);
        // Failures to root , should point back to root
        root.nextStates().forEach(v -> v.setFailure(root));
        Queue<Node> queue = Queues.newArrayDeque();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            queue.addAll(node.nextStates());
            if (node.equals(root)) {
                continue;
            }
            Node parentFailureNode = node.getFailure();
            for (Character character : node.characters()) {
                Node child = node.get(character);
                Node failure = node.getFailure();
                while (!failure.hasCharacter(character) && !root.equals(failure)) {
                    failure = failure.getFailure();
                }
                child.setFailure(failure.get(character));
                if (!child.hasFailure()) {
                    child.setFailure(root);
                }
            }
        }
    }

    private void resetFailures() {
        Queue<Node> queue = Queues.newArrayDeque();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            queue.addAll(node.nextStates());
            if (node.equals(root)) {
                continue;
            }
            node.clearFailure();
        }
    }

    public Map<String, List<Indices>> search(@NonNull String text) {
        Map<String, List<Indices>> results = Maps.newHashMap();
        Node node = root;
        for (int i = 0; i < text.length(); i++) {
            while (!node.equals(root) && !node.hasCharacter(text.charAt(i))) {
                node = node.getFailure();
            }
            if (node.equals(root)) {
                if (node.hasCharacter(text.charAt(i))) {
                    node = node.get(text.charAt(i));
                } else {
                    node = root;
                }
            } else {
                node = node.get(text.charAt(i));
            }
            if (node.isLeaf()) {
                Indices indices = new Indices();
                indices.start = i - node.getWord().length() + 1;
                indices.end = i;
                if (results.get(node.getWord()) == null) {
                    results.put(node.getWord(), Lists.newArrayList());
                }
                results.get(node.getWord()).add(indices);
            }
        }
        return results;
    }
}
