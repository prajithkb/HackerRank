
package com.hackerrank.solutions;


import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.function.BiPredicate;


public class Solution {

    static long MOD = (long) (Math.pow(10, 9) + 7);

    static long C(int n, int r) {
        return (f[n] * ((inverseEuler(f[r]) * inverseEuler(f[n - r])) % MOD)) % MOD;
    }

    static long inverseEuler(long n) {
        return pow(n, MOD - 2);
    }

    static long[] f = new long[1000005];

    static {
        f[0] = 1;
        for (int i = 1; i <= 500000; i++)
            f[i] = (f[i - 1] * i) % MOD;
    }

    static long pow(long a, long b) {
        long x = 1, y = a;
        while (b > 0) {
            if (b % 2 == 1) {
                x = (x * y);
                if (x > MOD) x %= MOD;
            }
            y = (y * y);
            if (y > MOD) y %= MOD;
            b /= 2;
        }
        return x;
    }


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


    static class Tuple<T, R> {
        T first;
        T second;
        R value;

        public Tuple() {
        }

        public Tuple(T first, T second) {
            this.first = first;
            this.second = second;
        }

        public Tuple(T first, T second, R value) {
            this(first, second);
            this.value = value;
        }

        public T getFirst() {
            return first;
        }

        public T getSecond() {
            return second;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(T second) {
            this.second = second;
        }

        public void setValue(R value) {
            this.value = value;
        }

        public R getValue() {
            return value;
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


    static Map<Integer, Set<Integer>> tree = new HashMap<>();


    static boolean[] visited(int n) {
        return new boolean[n + 1];
    }

    static void add(Integer from, Integer to) {
        Set<Integer> s = tree.get(from);
        if (s == null) {
            s = new HashSet<>();
            tree.put(from, s);
        }
        s.add(to);
        s = tree.get(to);
        if (s == null)
            s = new HashSet<>();
        {
            tree.put(to, s);
        }
        s.add(from);

    }

    static class MaxMinQueue<T extends Comparable<T>> {
        Deque<T> items = new ArrayDeque<>(), minItems = new ArrayDeque<>(), maxItems = new ArrayDeque<>();


        void enqeue(T item) {
            items.addFirst(item);
            removeItemsConditionallyAndAdd(item, (T a1, T a2) -> a1.compareTo(a2) < 0, minItems);
            removeItemsConditionallyAndAdd(item, (T a1, T a2) -> a1.compareTo(a2) > 0, maxItems);
        }


        T min() {
            return minItems.getLast();
        }

        T max() {
            return maxItems.getLast();
        }

        T dequeue() {
            removeConditionally(minItems, items);
            removeConditionally(maxItems, items);
            return items.removeLast();
        }

        boolean isEmpty() {
            return items.isEmpty();
        }

        int size() {
            return items.size();
        }

        public String toString() {
            return String.format("Max-%d,Min-%d,Q:%s", max(), min(), items);
        }

        private static <T> void removeItemsConditionallyAndAdd(T item, BiPredicate<T, T> condition, Deque<T> auxilliaryItems) {
            while (!auxilliaryItems.isEmpty() && condition.test(item, auxilliaryItems.getFirst())) {
                auxilliaryItems.removeFirst();
            }
            auxilliaryItems.addFirst(item);

        }

        private static <T> void removeConditionally(Deque<T> auxilliaryItems, Deque<T> items) {
            if (auxilliaryItems.getLast().equals(items.getLast())) {
                auxilliaryItems.removeLast();
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Timer.createTimer();
        int n = in.nextInt();
        int q = in.nextInt();
        Integer[] input = new Integer[n];
        MaxMinQueue<Integer> queue = new MaxMinQueue<>();
        for (int i = 0; i < n; i++) {
            input[i] = in.nextInt();
        }

        for (int i = 0; i < q; i++) {
            int low = in.nextInt();
            int high = in.nextInt();
            System.out.println(numberOfPairsLesser(high, input) - numberOfPairsLesser(low - 1, input));
        }

        Timer.elapsedTime();
    }

    static long numberOfPairsLesser(int high, Integer[] input) {
        long result = 0;
        MaxMinQueue<Integer> queue = new MaxMinQueue<>();
        for (int i = 0; i < input.length; i++) {
            queue.enqeue(input[i]);
//            Printer.printFormat("Query - %d, %s\n", high, queue);
            while (!queue.isEmpty() && (queue.max() - queue.min() > high)) {
                queue.dequeue();
            }
            result += queue.size();
        }
        return result;

    }


}




