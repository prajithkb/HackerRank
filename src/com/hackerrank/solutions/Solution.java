
package com.hackerrank.solutions;


import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.function.BiConsumer;


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


    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Timer.createTimer();
        int r, c, n;
        r = in.nextInt();
        c = in.nextInt();
        n = in.nextInt();
        char[][] grid = new char[r][c];
        for (int i = 0; i < r; i++) {
            grid[i] = in.next().toCharArray();
        }
//        Grid g = new Grid(grid, r, c);
        Grid h = new Grid(grid, r, c);
//        BomberMan bomberMan = new BomberMan(g);
        BomberMan optimizedBomberMan = new OptimizedBomberMan(h);
//        bomberMan.tick(n);
        optimizedBomberMan.tick(n);
//            System.out.print("After " + i + " seconds ");
//        System.out.println(g.state().equals(h.state()));
        System.out.println(h.state());
        Timer.elapsedTime();
    }

    static class BomberMan {
        enum State {
            START,
            PLANTING,
            OBSERVE;
        }

        BomberMan(Grid grid) {
            this.grid = grid;
        }

        State state = State.START;

        Grid grid;

        void tick(int n) {
            for (int i = 0; i < n; i++) {
                changeState();
                grid.tick();
                if (state == State.PLANTING) {
                    grid.plant();
                }
            }
        }

        public String toString() {
            return String.format("%s \n%s", state, grid);
        }

        void changeState() {
            if (state == State.START || state == State.PLANTING) {
                state = State.OBSERVE;
            } else {
                state = State.PLANTING;
            }
        }
    }


    static class OptimizedBomberMan extends BomberMan {

        OptimizedBomberMan(Grid grid) {
            super(grid);
        }

        void tick(int times) {
            if(times <=5){
                super.tick(times);
                return;
            }
            if(times % 2 == 0){
                super.tick(2);
                return;
            }
            if ((times - 3) % 4 == 0) {
                for (int i = 0; i < 3; i++) {
                    super.tick(1);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    super.tick(1);
                }
            }
        }
    }

    static class Grid {
        final int r, c;
        final char[][] grid;
        final int[][] timeRemaining;
        static int START_TIME = 3;

        Grid(char[][] grid, int r, int c) {
            this.r = r;
            this.c = c;
            this.grid = grid;
            timeRemaining = new int[r][c];
            resetTime();
        }


        static final char BOMB = 'O';
        static final char EMPTY = '.';
        // temporary state to mark it as it will explode
        // BOMB -> EXPLODE -> EMPTY;
        static final char EXPLODE = 'X';

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < r; i++) {
                sb.append(String.valueOf(grid[i]));
                sb.append("\n");
            }
            for (int i = 0; i < r; i++) {
                sb.append(Arrays.toString(timeRemaining[i]));
                sb.append("\n");
            }
            return sb.toString();
        }

        void tick() {
            markCellsToExplode();
            explode();
        }

        private void markCellsToExplode() {
            loop((Integer i, Integer j) -> {
                if (timeRemaining[i][j] != 0) {
                    timeRemaining[i][j]--;
                    if (timeRemaining[i][j] == 0) {
                        markCellToExplode(i, j);
                    }
                }
            });
        }

        void plant() {
            loop((Integer i, Integer j) -> {
                if (grid[i][j] == EMPTY) {
                    grid[i][j] = BOMB;
                }
            });
            resetTime();
        }

        void loop(BiConsumer<Integer, Integer> biConsumer) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    biConsumer.accept(i, j);
                }
            }
        }

        void resetTime() {
            loop((Integer i, Integer j) -> {
                if (grid[i][j] == BOMB && timeRemaining[i][j] == 0) {
                    timeRemaining[i][j] = START_TIME;
                }
            });
        }


        String state() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < r; i++) {
                sb.append(String.valueOf(grid[i]));
                sb.append("\n");
            }
            return sb.toString();
        }

        void markCellToExplode(int i, int j) {
            markToExplode(i, j);
            if (i > 0) {
                markToExplode(i - 1, j);
            }
            if (j > 0) {
                markToExplode(i, j - 1);
            }
            if (i < r - 1) {
                markToExplode(i + 1, j);
            }
            if (j < c - 1) {
                markToExplode(i, j + 1);
            }
        }

        void explode() {
            loop((Integer i, Integer j) -> {
                if (grid[i][j] == EXPLODE) {
                    timeRemaining[i][j] = 0;
                    grid[i][j] = EMPTY;
                }
            });
        }

        void markToExplode(int i, int j) {
            grid[i][j] = EXPLODE;
        }


    }


}




