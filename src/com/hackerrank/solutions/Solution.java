
package com.hackerrank.solutions;


import java.io.FileNotFoundException;
import java.util.*;


public class Solution {

    public static final int ROOT = 1;
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

        private static void print(String time) {
            if (canPrint) {
                System.out.println(time + " ms");
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


    static class Tuple {
        int first;
        int second;
        long value;

        public Tuple() {
        }

        public Tuple(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public Tuple(int first, int second, long value) {
            this.first = first;
            this.second = second;
            this.value = value;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public long size() {
            return second - first;
        }

        public String toString() {
            return String.format("(%d,%d) -> %d", first, second, value);
        }
    }

    static String fileName = "C:\\Users\\kprajith\\Desktop\\x ray.txt";
    static Scanner in;

    static {
        try {
            in = !canPrint ? new Scanner(System.in) : new Scanner(new java.io.File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int[] nodes;

    static long[] subtreeCount;
    static long[] redSubtreeCount;

    static long[] subtreeSum;

    static long[] sumFromAllNodes;

    static Map<Integer, List<Tuple>> graph = new HashMap<Integer, List<Tuple>>();

    static void add(int x, int y, long z) {
        List<Tuple> childrenX = graph.get(x);
        List<Tuple> childrenY = graph.get(y);
        if (childrenX == null) {
            childrenX = new ArrayList<>();
            graph.put(x, childrenX);
        }
        if (childrenY == null) {
            childrenY = new ArrayList<>();
            graph.put(y, childrenY);
        }
        childrenX.add(new Tuple(x, y, z));
        childrenY.add(new Tuple(y, x, z));
    }

    static boolean isRed(int node) {
        return nodes[node] == 0;
    }

    static void visited(int n) {
        visited = new boolean[n + 1];
    }

    static int n;
    static long redNodesCount;

    static boolean[] visited;

    public static void main(String[] args) throws FileNotFoundException {
        Timer.createTimer();
        n = in.nextInt();
        subtreeCount = new long[n + 1];
        redSubtreeCount = new long[n + 1];
        subtreeSum = new long[n + 1];
        sumFromAllNodes = new long[n + 1];
        nodes = new int[n + 1];
        // populate red/black
        for (int i = 1; i <= n; i++) {
            nodes[i] = in.nextInt();
            redNodesCount += isRed(i) ? 1 : 0;
        }
        // create the graph
        for (int i = 0; i < n - 1; i++) {
            add(in.nextInt(), in.nextInt(), in.nextLong());
        }
        visited(n);
        // Fix node 1 as root, and find all the subtree sums
        populateSubTreeSum(ROOT);
        // Populate sumFromAllNodes from all (red) nodes to the current node
        // Reset the visited the flags;
        visited(n);
        populateSumFromAllNodes(ROOT);
        Printer.print("Graph - : " + graph);
        Printer.print("subTreeCount : " + Arrays.toString(subtreeCount));
        Printer.print("subTreeSum : " + Arrays.toString(subtreeSum));
        Printer.print("sumOfAllNodes : " + Arrays.toString(sumFromAllNodes));
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            if (!isRed(i)) {
                sum += sumFromAllNodes[i];
            }
        }
        System.out.println(sum);
        Timer.elapsedTime();

    }

    static void populateSubTreeSum(int node) {
        visited[node] = true;
        for (Tuple path : graph.get(node)) {
            if (!visited[path.getSecond()]) {
                populateSubTreeSum(path.getSecond());
                // populate the subTreeCount
                subtreeCount[node] += subtreeCount[path.getSecond()];
                // current nodes subTreeSum = sumFromAllNodes of (a child nodes subtree sumFromAllNodes +
                subtreeSum[node] += subtreeSum[path.getSecond()];
                // number of red children of the child node * edge child -> node)
                subtreeSum[node] += (path.getValue() * subtreeCount[path.getSecond()]);
            }
        }
        if (isRed(node)) {
            // Add the current node to the subtree
            subtreeCount[node]++;
        }

    }

    static void populateSumFromAllNodes(int node) {
        visited[node] = true;
        // If the current node is 1 , then it is root so we have it calculated already
        if (node == ROOT) {
            sumFromAllNodes[node] = subtreeSum[ROOT];
        }
        for (Tuple path : graph.get(node)) {
            if (!visited[path.getSecond()]) {
                sumFromAllNodes[path.getSecond()] = sumFromAllNodes[node]
                        - subtreeCount[path.getSecond()] * path.getValue();
                long countOfNodesFromParent = redNodesCount - subtreeCount[path.getSecond()];
                sumFromAllNodes[path.getSecond()] += (countOfNodesFromParent * path.getValue());
                populateSumFromAllNodes(path.getSecond());
            }
        }
    }


}


