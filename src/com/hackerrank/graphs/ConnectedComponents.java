package com.hackerrank.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ConnectedComponents {

    @Builder
    @Getter
    public static class Node {

        private final int value;

        @Builder.Default
        @ToString.Exclude
        private Set<Node> neighbours = new HashSet<>();

        public void connect(Node node) {
            neighbours.add(node);
        }

        public boolean hasNeighbours() {
            return !neighbours.isEmpty();
        }

        public void visit(Consumer<Node> visitor, Function<Node,Boolean> condition){
            if(condition.apply(this)){
                visitor.accept(this);
                neighbours.forEach(n -> n.visit(visitor, condition));
            }
        }

        public String toString() {
            return this.getValue() + "";
        }
    }

    @Builder.Default private Map<Integer, Node> nodesMap = new HashMap<>();

    public void connect(int from, int to) {
        node(from).connect(node(to));
        node(to).connect(node(from));
    }

    public Node node(int id) {
        nodesMap.putIfAbsent(id, Node
                .builder()
                .value(id)
                .build());
        return nodesMap.get(id);
    }

    public List<Set<Node>> getConnectedComponentsSizes() {
        Map<Integer, Boolean> visited = new HashMap<>();
        return nodesMap
                .entrySet()
                .stream()
                .filter(e -> isNotVisited(e.getValue(), visited))
                .map(e -> createConnections(e.getValue(), visited))
                .collect(Collectors.toList());

    }

    public static Set<Node> createConnections(Node node,  Map<Integer, Boolean> visited) {
        Set<Node> connections = new HashSet<>();
        node.visit(n -> {
            visited.put(n.getValue(), true);
            connections.add(n);
        }, n -> isNotVisited(n, visited));
        return connections;
    }

    static boolean isNotVisited(Node node, Map<Integer, Boolean> visited){
        return !visited.getOrDefault(node.getValue(),false);
    }

    static int visit(Node node, Map<Integer, Boolean> visited) {
        if (visited.getOrDefault(node.getValue(), false)) {
            return 0;
        }
        visited.put(node.getValue(), true);
        if (node.hasNeighbours()) {
            return node
                    .getNeighbours()
                    .stream()
                    .filter(n -> !visited.getOrDefault(node.getValue(), false))
                    .mapToInt(n -> visit(node, visited))
                    .sum();
        } else{
            return 1;
        }

    }

}