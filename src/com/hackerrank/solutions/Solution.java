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


    static Set<Character> vowels = new HashSet<>();


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int m = in.nextInt();
            List<String> words = new ArrayList<>();
            possible.clear();
            notPossible.clear();
            for (int j = 0; j < m; j++) {
                words.add(in.next());
            }
            String password = in.next();
            if (isPassword(words, password)) {
                possible.forEach(o -> System.out.print(o + " "));
                System.out.println();
            } else {
                System.out.println("WRONG PASSWORD");
            }
        }
    }

    static List<String> possible = new ArrayList<>();
    static Set<String> notPossible = new HashSet<>();

    static boolean isPassword(List<String> words, String password) {
        if (password.isEmpty()) {
            return true;
        } else if (notPossible.contains(password)) {
            return false;
        } else {
            for (int i = 0; i < words.size(); i++) {
                String w = words.get(i);

                if (password.startsWith(w)) {
                    possible.add(w);
                    if (isPassword(words, password.substring(w.length(), password.length()))) {
                        return true;
                    }
                    notPossible.add(password);
                    possible.remove(w);
                }
            }
            return false;
        }
    }
}


