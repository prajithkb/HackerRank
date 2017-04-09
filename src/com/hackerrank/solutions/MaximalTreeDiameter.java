package com.hackerrank.solutions;

import java.util.*;


public class MaximalTreeDiameter {

    static class Node {
        int id;
        int height;
        int secondaryHeight;
        List<Node> children = new ArrayList<>();

        boolean hasChildren() {
            return !children.isEmpty();
        }

        public int getHeight() {
            return height;
        }

        public int getSecondaryHeight(){
            return  secondaryHeight;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(id).append("|").append(height).append(")");
            return sb.toString();
        }
    }
    static Node NULL;
    static {
        NULL = new Node();
        NULL.id = -1;
    }

    static Map<Integer, Node> nodes = new HashMap<>();

    static boolean visited[];

    static int diameter = 0;

    static Set<Integer> nodesInDiameter = new HashSet<>();

    static int heightOfNonDiameterNodes = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        visited = new boolean[n + 1];
        for (int a0 = 0; a0 < n - 1; a0++) {
            int u = in.nextInt();
            int v = in.nextInt();
            Node nodeU = node(u);
            Node nodeV = node(v);
            nodeU.children.add(nodeV);
            nodeV.children.add(nodeU);
        }
        Arrays.fill(visited, false);
        setHeightsUsingDFS(nodes.get(1));
        // System.out.println(nodes);
        Arrays.fill(visited, false);
        int diameter = getDiameterUsingDFS(nodes.get(1));
        //System.out.println(diameter);
        Arrays.fill(visited, false);
        setSecondaryHeightsUsingDFS(nodes.get(1));
        Node longestNonDiameterNode = nodes.entrySet()
                .stream()
                .filter(k -> !nodesInDiameter.contains(k.getKey()))
                .map(k -> k.getValue())
                .max(Comparator.comparing(Node::getSecondaryHeight))
                .orElse(NULL);

        if(longestNonDiameterNode != NULL){
            System.out.println(longestNonDiameterNode.getSecondaryHeight() + diameter + 1);
        }else{
            System.out.println(diameter);
        }
    }

    static int setHeightsUsingDFS(Node node) {
        if (!node.hasChildren()) {
            return 0;
        }
        visited[node.id] = true;
        int maximumHeight = Integer.MIN_VALUE;
        for (Node n : node.children) {
            if (!visited[n.id]) {
                maximumHeight = Math.max(setHeightsUsingDFS(n), maximumHeight);
            }
        }
        if (maximumHeight != Integer.MIN_VALUE) {
            node.height = maximumHeight + 1;
        }
        return node.height;

    }
    static int setSecondaryHeightsUsingDFS(Node node) {
        if (!node.hasChildren()) {
            return 0;
        }
        visited[node.id] = true;
        int maximumHeight = Integer.MIN_VALUE;
        for (Node n : node.children) {
            if (!visited[n.id] && !nodesInDiameter.contains(n.id)) {
                maximumHeight = Math.max(setSecondaryHeightsUsingDFS(n), maximumHeight);
            }
        }
        if (maximumHeight != Integer.MIN_VALUE) {
            node.secondaryHeight = maximumHeight + 1;
        }
        return node.secondaryHeight;

    }


    static void setNonDiameterHeightsUsingDFS(Node node) {
        if (!node.hasChildren()) {
            return;
        }
        visited[node.id] = true;
        int height = 0;
        for (Node n : node.children) {
            if (!nodesInDiameter.contains(n.id) && !visited[n.id]) {
                setNonDiameterHeightsUsingDFS(n);
                height = Math.max(height, n.getHeight());
            }
        }
        heightOfNonDiameterNodes = Math.max(height + 1, heightOfNonDiameterNodes);
    }

    static int getDiameterUsingDFS(Node node) {
        if (!node.hasChildren()) {
            return 0;
        }
        visited[node.id] = true;
        List<Node> heights = new ArrayList<>();
        int i = 0;
        for (Node n : node.children) {
            if (!visited[n.id]) {
                diameter = Math.max(getDiameterUsingDFS(n), diameter);
                heights.add(n);
            }
            i++;
        }
        if (heights.size() >= 2) {
            Collections.sort(heights, Comparator.comparing(Node::getHeight));
            Node highestSubtreeHeight = heights.get(heights.size() - 1);
            Node secondHighestSubtreeHeight = heights.get(heights.size() - 2);
            if (highestSubtreeHeight.getHeight() + secondHighestSubtreeHeight.getHeight() + 2 > diameter) {
                nodesInDiameter.add(highestSubtreeHeight.id);
                nodesInDiameter.add(secondHighestSubtreeHeight.id);
                nodesInDiameter.add(node.id);
                diameter = highestSubtreeHeight.getHeight() + secondHighestSubtreeHeight.getHeight() + 2;
            }
        } else if (heights.size() == 1) {
            Node highestSubtreeHeight = heights.get(0);
            if (highestSubtreeHeight.getHeight() + 1 > diameter) {
                nodesInDiameter.add(highestSubtreeHeight.id);
                diameter = highestSubtreeHeight.getHeight() + 1;
                nodesInDiameter.add(node.id);
            }
        }
        return diameter;
    }


    static Node node(int i) {
        if (nodes.get(i) == null) {
            Node n = new Node();
            n.id = i;
            nodes.put(i, n);
        }
        return nodes.get(i);
    }


}
