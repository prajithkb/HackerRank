package com.hackerrank.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kprajith on 4/8/2017.
 */
public class Node<T> {

    public  T value;
    public int id;


    public Set<Node> children = new HashSet<>();

    public static Node NULL;

    static {
        NULL = Node.builder().build();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public void add(Node child){
        children.add(child);
    }

    private Node() {
    }

    public static NodeBuilder builder() {
        NodeBuilder nodeBuilder = new NodeBuilder();
        nodeBuilder.node = new Node();
        return nodeBuilder;
    }

   public  static class NodeBuilder<T> {
        Node node;

       public NodeBuilder child(Node child) {
            node.children.add(child);
            return this;
        }

       public NodeBuilder children(List<Node> children) {
            node.children.addAll(children);
            return this;
        }


       public  NodeBuilder value(T value) {
            node.value = value;
            return this;
        }
       public NodeBuilder id(int id) {
            node.id = id;
            return this;
        }

       public Node build() {
            return node;
        }
    }

    public String toString() {
        return value + "->" + children;
    }
}
