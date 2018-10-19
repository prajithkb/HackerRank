package com.hackerrank.temporary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static class ConnectedComponents {

        ConnectedComponents(Map<Integer, Node> nodesMap) {
            this.nodesMap = nodesMap;
        }

        public static ConnectedComponentsBuilder builder() {
            return new ConnectedComponentsBuilder();
        }

        public Map<Integer, Node> getNodesMap() {
            return this.nodesMap;
        }

        public String toString() {
            return "ConnectedComponents(nodesMap=" + this.getNodesMap() + ")";
        }

        public static class Node {

            private final int value;

            private Set<Node> neighbours = new HashSet<>();

            Node(int value, Set<Node> neighbours) {
                this.value = value;
                this.neighbours = neighbours;
            }

            public static NodeBuilder builder() {
                return new NodeBuilder();
            }

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

            public int getValue() {
                return this.value;
            }

            public Set<Node> getNeighbours() {
                return this.neighbours;
            }

            public static class NodeBuilder {
                private int value;
                private Set<Node> neighbours = new HashSet<>();

                NodeBuilder() {
                }

                public Node.NodeBuilder value(int value) {
                    this.value = value;
                    return this;
                }

                public Node.NodeBuilder neighbours(Set<Node> neighbours) {
                    this.neighbours = neighbours;
                    return this;
                }

                public Node build() {
                    return new Node(value, neighbours);
                }

                public String toString() {
                    return "ConnectedComponents.Node.NodeBuilder(value=" + this.value + ", neighbours=" + this.neighbours + ")";
                }
            }
        }

        private Map<Integer, Node> nodesMap = new HashMap<>();

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

        public static class ConnectedComponentsBuilder {
            private Map<Integer, Node> nodesMap = new HashMap<>();

            ConnectedComponentsBuilder() {
            }

            public ConnectedComponents.ConnectedComponentsBuilder nodesMap(Map<Integer, Node> nodesMap) {
                this.nodesMap = nodesMap;
                return this;
            }

            public ConnectedComponents build() {
                return new ConnectedComponents(nodesMap);
            }

            public String toString() {
                return "ConnectedComponents.ConnectedComponentsBuilder(nodesMap=" + this.nodesMap + ")";
            }
        }
    }

    static int[] componentsInGraph(int[][] gb) {
        ConnectedComponents connectedComponents = ConnectedComponents
                .builder()
                .build();
        for (int i = 0; i < gb.length; i++) {
            connectedComponents.connect(gb[i][0], gb[i][1]);
        }
        final List<Set<ConnectedComponents.Node>> connectedNodes = connectedComponents.getConnectedComponentsSizes();
        final List<Integer> sizes = connectedNodes
                .stream()
                .mapToInt(cc -> cc.size())
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        return new int[] { sizes.get(0), sizes.get(sizes.size()-1) };

    }

    static OutputStreamWriter outputStreamWriter;

    static {
        try {
            outputStreamWriter = (System.getenv("OUTPUT_PATH") != null) ?
                    new FileWriter(System.getenv("OUTPUT_PATH")) :
                    new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        int n = Integer.parseInt(scanner
                                         .nextLine()
                                         .trim());

        int[][] gb = new int[n][2];

        for (int gbRowItr = 0; gbRowItr < n; gbRowItr++) {
            String[] gbRowItems = scanner
                    .nextLine()
                    .split(" ");

            for (int gbColumnItr = 0; gbColumnItr < 2; gbColumnItr++) {
                int gbItem = Integer.parseInt(gbRowItems[gbColumnItr].trim());
                gb[gbRowItr][gbColumnItr] = gbItem;
            }
        }

        int[] result = componentsInGraph(gb);

        for (int SPACEItr = 0; SPACEItr < result.length; SPACEItr++) {
            bufferedWriter.write(String.valueOf(result[SPACEItr]));

            if (SPACEItr != result.length - 1) {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

