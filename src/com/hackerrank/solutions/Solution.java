package com.hackerrank.solutions;

import java.util.HashMap;
import java.util.Map;


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

        static void print(String s) {
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


    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String n = in.next();

        int index = 13;
        System.out.println(( index & (-index)));
        System.out.println(index - ( index & (-index)));


    }

}


