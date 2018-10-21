package com.hackerrank.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ConnectedComponents {


    @Builder.Default private Map<Integer, Node> nodesMap = new HashMap<>();

    public void connect(int from, int to) {
        node(from).connectWith(node(to));
        node(to).connectWith(node(from));
    }
    private Node node(int id) {
        nodesMap.putIfAbsent(id, Node
                .builder()
                .value(id)
                .build());
        return nodesMap.get(id);
    }

    public List<Set<Node>> getConnectedNodes() {
        Map<Integer, Boolean> visited = new HashMap<>();
        return nodesMap
                .entrySet()
                .stream()
                .filter(e -> isNotVisited(e.getValue(), visited))
                .map(e -> createConnectedNodes(e.getValue(), visited))
                .collect(Collectors.toList());

    }

    private static Set<Node> createConnectedNodes(Node node, Map<Integer, Boolean> visited) {
        Set<Node> connections = new HashSet<>();
        node.visit(n -> {
            visited.put(n.getValue(), true);
            connections.add(n);
        }, n -> isNotVisited(n, visited));
        return connections;
    }

    private static boolean isNotVisited(Node node, Map<Integer, Boolean> visited){
        return !visited.getOrDefault(node.getValue(),false);
    }

    @Builder
    @Getter
    public static class Node {

        private final int value;

        @Builder.Default
        private Set<Node> neighbours = new HashSet<>();

        public void connectWith(Node node) {
            neighbours.add(node);
        }

        public boolean hasNeighbours() {
            return !neighbours.isEmpty();
        }

        public void visit(Consumer<Node> visitor, Predicate<Node> shouldVisit){
            if(shouldVisit.test(this)){
                visitor.accept(this);
                neighbours.forEach(n -> n.visit(visitor, shouldVisit));
            }
        }

        public String toString() {
            return this.getValue() + "";
        }
    }


}