
package com.hackerrank.solutions;


import java.io.FileNotFoundException;
import java.util.*;


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

    static int[] nodes;

    static int[] countOfNodes;

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

    public static void main(String[] args) throws FileNotFoundException {
        Timer.createTimer();
        int n = in.nextInt();
        int k = in.nextInt();
        long[] numbers = new long[n];
        List<Long> possibleCandidates = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            possibleCandidates.add(in.nextLong());
        }
        for (int j = 62; j >= 0; j--) {
            List<Long> possibleCandidatesForThisSetBit = new ArrayList<>();
            for (Long i : possibleCandidates) {
                if ((1L << j & i) > 0) {
                    possibleCandidatesForThisSetBit.add(i);
                }
            }
//            Printer.printFormat("%d , %s\n",j, possibleCandidatesForThisSetBit);
            if (possibleCandidatesForThisSetBit.size() >= k) {
                possibleCandidates = possibleCandidatesForThisSetBit;
            }
        }


        // Maximum possible number
        long M = Long.MAX_VALUE;
        for (Long i : possibleCandidates) {
            M &= i;
        }
        System.out.println(M);
        // Number of possible combinations
        System.out.println(C(possibleCandidates.size(), k));
        Timer.elapsedTime();
    }

    static int[] countOfNumbersBySetBit(long[] numbers) {
        int[] countOfNumbersBySetBit = new int[64];
        int i = 0;
        while (i < 63) {
            for (int j = 0; j < numbers.length; j++) {
//                Printer.printFormat("%d << %d & % d\n", 1L << i, i, numbers[j]);
                if ((1L << i & numbers[j]) > 0) {
                    countOfNumbersBySetBit[i]++;
                }
            }
            i++;
        }
        return countOfNumbersBySetBit;
    }


}




