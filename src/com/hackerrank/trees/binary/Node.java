package com.hackerrank.trees.binary;

/**
 * Created by kprajith on 4/8/2017.
 */
class Node {

    Node left = NULL;
    Node right = NULL;
    int value;
    static Node NULL;

    static {
        NULL = Node.builder().build();
        NULL.value = -1;
    }

    private Node() {
    }

    static NodeBuilder builder() {
        NodeBuilder nodeBuilder = new NodeBuilder();
        nodeBuilder.node = new Node();
        return nodeBuilder;
    }

    static class NodeBuilder {
        Node node;

        NodeBuilder left(Node left) {
            node.left = left;
            return this;
        }

        NodeBuilder right(Node right) {
            node.right = right;
            return this;
        }

        NodeBuilder value(int value) {
            node.value = value;
            return this;
        }

        Node build() {
            return node;
        }
    }

    public String toString() {
        return value + "->(" + left.value + " , " + right.value + ")";
    }
}
