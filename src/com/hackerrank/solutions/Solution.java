package com.hackerrank.solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Solution {

    private static boolean canPrint;

    static {
        canPrint = "true".equals(System.getProperty("desktop"));
    }

    static class Timer {

        private static long startTime;

        private static Map<String, Long> marker = new HashMap<>();

        static void createTimer() {
            startTime = System.currentTimeMillis();
        }

        static void mark(String m) {
            marker.put(m, System.currentTimeMillis());
        }

        static void elapsedTime(String m) {
            print(String.valueOf(System.currentTimeMillis() - marker.get(m)));
            marker.remove(m);
        }

        static void elapsedTime() {
            print("Elapsed Time : " + (System.currentTimeMillis() - startTime));
        }

        private static void print(String text) {
            if (canPrint) {
                System.out.println(text);
            }
        }

    }

    static class Printer {

        static void print(Object s) {
            if (canPrint) {
                System.out.println(s);
            }
        }

        static void printFormat(String s, Object... o) {
            if (canPrint) {
                System.out.format(s, o);
            }
        }


    }


    static Map<String, Integer> edges = new HashMap<>();

    static Integer getEdge(int src, int dest) {
        return edges.get(src + "-" + dest);
    }

    static void putEdge(int src, int dest, int cost) {
        if (getEdge(src, dest) == null || cost < getEdge(src, dest)) {
            edges.put(src + "-" + dest, cost);
        }
    }

    static Node node(int index) {
        if (nodes[index] == null) {
            nodes[index] = new Node(index);
        }
        return nodes[index];

    }
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }


    static Node[] nodes;


    public static void main(String[] args) {

        FastReader in = new FastReader();
        int q = in.nextInt();
        for (int j = 0; j < q; j++) {
            int n = in.nextInt();
            nodes = new Node[n + 1];
            int e = in.nextInt();
            edges = new HashMap<>(2 * e);
            for (int i = 0; i < e; i++) {
                Node src = node(in.nextInt());
                Node dest = node(in.nextInt());
                int cost = in.nextInt();
                src.nodes.add(dest);
                dest.nodes.add(src);
                putEdge(src.node, dest.node, cost);
                putEdge(dest.node, src.node, cost);
            }
            int s = in.nextInt();
            Dijkstra d = new Dijkstra(n, e);
            d.shortestPath(nodes, s);
            for (int i = 1; i < d.distances.length; i++) {
                if (i != s) {
                    if (d.distances[i] == Integer.MAX_VALUE) {
                        System.out.print("-1 ");
                    } else {
                        System.out.print(d.distances[i] + " ");
                    }
                }
            }
            System.out.println();
        }
    }


    static class Dijkstra {
        private int distances[];
        private TreeSet<Edge> priorityQueue;
        private Node[] nodes;
        private int[] settled;

        public Dijkstra(int numberOfNodes, int numberOfEdges) {
            distances = new int[numberOfNodes + 1];
            Arrays.fill(distances, Integer.MAX_VALUE);
            settled = new int[numberOfNodes + 1];
            priorityQueue = new TreeSet<>(Comparator.comparing(Edge::getCost)
                    .thenComparing(Edge::getSrc)
                    .thenComparing(Edge::getDest));
        }

        public void shortestPath(Node[] nodes, int source) {
            this.nodes = nodes;
            priorityQueue.add(new Edge(source, source, 0));
            int evaluationNode;
            distances[source] = 0;
            while (!priorityQueue.isEmpty()) {
                evaluationNode = getNodeWithMinimumDistanceFromPriorityQueue();
                settled[evaluationNode] = 1;
                evaluateNeighbours(evaluationNode);
            }
        }

        private int getNodeWithMinimumDistanceFromPriorityQueue() {
            Edge e = priorityQueue.pollFirst();
            return e.dest;
        }

        private int getCost(int source, int destination) {
            int edgeDistance = getEdge(source, destination);
            int newDistance = distances[source] + edgeDistance;
            return newDistance;
        }

        private void evaluateNeighbours(int evaluationNode) {
            int edgeDistance = -1;
            int newDistance = -1;
            if (nodes[evaluationNode] == null) {
                return;
            }
            for (int i = 0; i < nodes[evaluationNode].nodes.size(); i++) {
                Node n = nodes[evaluationNode].nodes.get(i);
                if (n == null) {
                    Printer.print("FUCKED ***" + evaluationNode);
                }
                if (settled[n.node] == 0) {
                    newDistance = getCost(evaluationNode, n.node);
                    if (newDistance < distances[n.node]) {
                        distances[n.node] = newDistance;
                        Edge e = new Edge(evaluationNode, n.node, distances[n.node]);
                        priorityQueue.add(e);
                    }
                }
            }
        }


    }

    static class Node {
        public int node;
        public List<Node> nodes = new ArrayList<>();

        public Node() {
        }

        public Node(int node) {
            this.node = node;
        }

        public int getNode() {
            return node;
        }

        public String toString() {
            return String.format("(%d)", node);
        }
    }

    static class Edge {
        public int src;
        public int dest;
        public Integer cost;

        public Edge(int src, int dest, int cost) {
            this.src = src;
            this.dest = dest;
            this.cost = cost;
        }

        public Integer getCost() {
            return cost;
        }

        public int getSrc() {
            return src;
        }

        public int getDest() {
            return dest;
        }

        public String toString() {
            return String.format("(%d ->%d,%d)", src, dest, cost);
        }

    }
}