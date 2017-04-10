package com.hackerrank.solutions;

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


    static class Order {
        private Long startTime;
        private Long timeToCook;

        Order(long startTime, long timeToCook) {
            this.startTime = startTime;
            this.timeToCook = timeToCook;
        }

        public Long getStartTime() {
            return startTime;
        }

        public Long getTimeToCook() {
            return timeToCook;
        }

        public String toString() {
            return String.format("[%d,%d]", startTime, timeToCook);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long n = in.nextLong();
        PriorityQueue<Order> orders = new PriorityQueue<Order>(Comparator
                .comparing(Order::getStartTime));
        for (int i = 0; i < n; i++) {
            long l = in.nextLong();
            long r = in.nextLong();
            orders.add(new Order(l, r));
        }
        long sum = 0;
        PriorityQueue<Order> best = new PriorityQueue<>(Comparator
                .comparing(Order::getTimeToCook));
        long currentTime = 0;
        while (!orders.isEmpty() || !best.isEmpty()) {
            while (!orders.isEmpty()
                    && orders.peek().getStartTime() <= currentTime) {
                Order o = orders.poll();
                best.add(o);
            }
            if (best.isEmpty() && !orders.isEmpty()) {
                Order o = orders.poll();
                best.add(o);
                currentTime = o.getStartTime();
            }
            if( !best.isEmpty()){
                Order o = best.poll();
                currentTime += o.getTimeToCook();
                sum += (currentTime - o.getStartTime());
            }

        }
        System.out.println(sum / n);
    }

}


