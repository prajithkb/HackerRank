package com.hackerrank.temporary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.hackerrank.graphs.DisjointSet;

public class Solution {

    static class Edge {
        int from, to, weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public static EdgeBuilder builder() {
            return new EdgeBuilder();
        }

        public int getFrom() {
            return this.from;
        }

        public int getTo() {
            return this.to;
        }

        public int getWeight() {
            return this.weight;
        }

        public static class EdgeBuilder {
            private int from;
            private int to;
            private int weight;

            EdgeBuilder() {
            }

            public Edge.EdgeBuilder from(int from) {
                this.from = from;
                return this;
            }

            public Edge.EdgeBuilder to(int to) {
                this.to = to;
                return this;
            }

            public Edge.EdgeBuilder weight(int weight) {
                this.weight = weight;
                return this;
            }

            public Edge build() {
                return new Edge(from, to, weight);
            }

            public String toString() {
                return "Solution.Edge.EdgeBuilder(from=" + this.from + ", to=" + this.to + ", weight=" + this.weight + ")";
            }
        }
    }

    static class Query {
        int left, right;

        Query(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public static QueryBuilder builder() {
            return new QueryBuilder();
        }

        public int getLeft() {
            return this.left;
        }

        public int getRight() {
            return this.right;
        }

        public String toString() {
            return "Solution.Query(left=" + this.getLeft() + ", right=" + this.getRight() + ")";
        }

        public static class QueryBuilder {
            private int left;
            private int right;

            QueryBuilder() {
            }

            public Query.QueryBuilder left(int left) {
                this.left = left;
                return this;
            }

            public Query.QueryBuilder right(int right) {
                this.right = right;
                return this;
            }

            public Query build() {
                return new Query(left, right);
            }

            public String toString() {
                return "Solution.Query.QueryBuilder(left=" + this.left + ", right=" + this.right + ")";
            }
        }
    }

    static OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
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
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        Edge[] edges = new Edge[N-1];
        Query[] queries = new Query[Q];
        for (int i = 0; i < N-1; i++) {
            edges[i] = Edge
                    .builder()
                    .from(scanner.nextInt())
                    .to(scanner.nextInt())
                    .weight(scanner.nextInt())
                    .build();
        }

        for (int i = 0; i < Q; i++) {
            queries[i] = Query
                    .builder()
                    .left(scanner.nextInt())
                    .right(scanner.nextInt())
                    .build();
        }
        final TreeMap<Integer, Long> numberOfPairs = numberOfPairs(edges, N);
        for (int i = 0; i < Q; i++) {
            final Long left = Optional.ofNullable(numberOfPairs
                                                          .floorEntry(queries[i].getLeft() -1))
                                      .map(Map.Entry::getValue)
                                      .orElse(0L);
            final Long right = Optional.ofNullable(numberOfPairs
                                                          .floorEntry(queries[i].getRight()))
                                      .map(Map.Entry::getValue)
                                      .orElse(0L);
            System.out.println(right - left);
        }
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    private static TreeMap<Integer, Long> numberOfPairs(final Edge[] edges, final int n) {
        Arrays.sort(edges, Comparator.comparing(Edge::getWeight));
        TreeMap<Integer, Long> numberOfPairs = new TreeMap<>();
        DisjointSet<Integer> disjointSet = new DisjointSet<>(n);
        Arrays.stream(edges)
              .forEach(edge -> {
                  final DisjointSet.Element<Integer> from = DisjointSet.createElement(edge.getFrom(), edge.getFrom());
                  final DisjointSet.Element<Integer> to = DisjointSet.createElement(edge.getTo(), edge.getTo());
                  long sizeFrom = disjointSet.sizeOfConnectedComponents(from);
                  long sizeTo = disjointSet.sizeOfConnectedComponents(to);
                  disjointSet.union(from, to);
                  long existingPairCount = numberOfPairs.getOrDefault(edge.getWeight(), 0L);
                  numberOfPairs.put(edge.getWeight(), existingPairCount + (sizeFrom *  sizeTo));
              });

        TreeMap<Integer, Long> cummulativeNumberOfPairs = new TreeMap<>();
        AtomicReference<Long> valueSum = new AtomicReference<>(0L);
        cummulativeNumberOfPairs.putAll(numberOfPairs
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                                          e -> valueSum.accumulateAndGet(e.getValue(), (x, y) -> x + y)
                )));
        return cummulativeNumberOfPairs;
    }

    private static long NC2(long N){
        return (N  * (N-1) )/2;
    }

}

