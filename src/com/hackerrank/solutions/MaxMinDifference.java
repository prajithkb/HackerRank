package com.hackerrank.solutions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.function.BiFunction;


public class MaxMinDifference {

    static class QueryableQueue {

        static class Creator {

            public static QueryableQueue create(BiFunction<Integer, Integer, Boolean> query) {
                QueryableQueue q = new QueryableQueue(query);
                return q;
            }

        }

        private QueryableQueue(BiFunction query) {
            this.query = query;
        }

        BiFunction<Integer, Integer, Boolean> query;
        Deque<Integer> values = new ArrayDeque<>();
        Deque<Integer> queries = new ArrayDeque<>();

        public void push(int item) {
            values.addLast(item);
            while (!queries.isEmpty() && query.apply(queries.peekLast(), item)) {
                queries.removeLast();
            }
            queries.addLast(item);
        }

        public int pop() {
            if (!queries.isEmpty() && queries.peekFirst() == values.peekFirst()) {
                queries.removeFirst();
            }
            return values.isEmpty() ? query.apply(Integer.MAX_VALUE, Integer.MIN_VALUE) ? Integer.MIN_VALUE : Integer.MAX_VALUE : values.removeFirst();
        }

        public int query() {
            return queries.peekFirst();
        }

        public int size() {
            return values.size();
        }


    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        int[] a = new int[n];
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();
        }

        for (int a0 = 0; a0 < q; a0++) {
            int low = in.nextInt();
            int high = in.nextInt();
            long ans = numberOfRanges(n, a, high) - numberOfRanges(n, a, low - 1);
            System.out.println(ans);
        }

//        minQ.push(10);
//        maxQ.push(10);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.push(9);
//        maxQ.push(9);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.push(8);
//        maxQ.push(8);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.push(11);
//        maxQ.push(11);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.push(2);
//        maxQ.push(2);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.push(20);
//        maxQ.push(20);
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());
//        minQ.pop();
//        maxQ.pop();
//        System.out.println("minQ query " + minQ.query());
//        System.out.println("maxQ query " + maxQ.query());


    }

    private static long numberOfRanges(int n, int[] input, int high) {
        QueryableQueue minQ = QueryableQueue.Creator.create((x, y) -> x > y);
        QueryableQueue maxQ = QueryableQueue.Creator.create((x, y) -> x < y);
        long ans = 0;
        for (int a_i = 0; a_i < n; a_i++) {
            minQ.push(input[a_i]);
            maxQ.push(input[a_i]);
            while (maxQ.query() - minQ.query() > high) {
                minQ.pop();
                maxQ.pop();
            }
            ans += maxQ.size();
        }
        return ans;
    }
}
