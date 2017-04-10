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
        int q = in.nextInt();
        int[] input = new int[n];
        for (int i = 0; i < n; i++) {
            input[i] = in.nextInt();
        }
//        int[] input  = {1, 8, 5, 7, 9, 11};
//        int n = input.length;
//
        SegmentTree tree = new SegmentTree(input, n);
//        System.out.println(tree.getSum(n,1,3));
        for (int i = 0; i < q; i++) {
            int d = in.nextInt();
            int[] max = new int[n-d+1];
            for (int j = 0; j < n; j++) {
                if (j + d -1 >= n) {
                    break;
                }
                max[j] = tree.getSum(n,j,j+d-1);

            }
           // System.out.println(Arrays.toString(max));
            System.out.println(Arrays.stream(max).min().getAsInt());
        }


    }

    static class SegmentTree {
        int st[]; // The array that stores segment tree nodes

        /* Constructor to construct segment tree from given array. This
           constructor  allocates memory for segment tree and calls
           constructSTUtil() to  fill the allocated memory */
        SegmentTree(int arr[], int n) {
            // Allocate memory for segment tree
            //Height of segment tree
            int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

            //Maximum size of segment tree
            int max_size = 2 * (int) Math.pow(2, x) - 1;

            st = new int[max_size]; // Memory allocation

            constructSTUtil(arr, 0, n - 1, 0);
          //  System.out.println(Arrays.toString(st));
        }

        // A utility function to get the middle index from corner indexes.
        int getMid(int s, int e) {
            return s + (e - s) / 2;
        }

        /*  A recursive function to get the sum of values in given range
            of the array.  The following are parameters for this function.

          st    --> Pointer to segment tree
          si    --> Index of current node in the segment tree. Initially
                    0 is passed as root is always at index 0
          ss & se  --> Starting and ending indexes of the segment represented
                        by current node, i.e., st[si]
          qs & qe  --> Starting and ending indexes of query range */
        int getSumUtil(int ss, int se, int qs, int qe, int si) {

            // If segment of this node is a part of given range, then return
            // the sum of the segment
            if (qs <= ss && qe >= se) {
//                System.out.println(ss +":"+se);
                return st[si];
            }

            // If segment of this node is outside the given range
            if (se < qs || ss > qe)
                return Integer.MIN_VALUE;

            // If a part of this segment overlaps with the given range
            int mid = getMid(ss, se);
            return Math.max(getSumUtil(ss, mid, qs, qe, 2 * si + 1),
                    getSumUtil(mid + 1, se, qs, qe, 2 * si + 2));
        }

        // Return sum of elements in range from index qs (quey start) to
        // qe (query end).  It mainly uses getSumUtil()
        int getSum(int n, int qs, int qe) {
            // Check for erroneous input values
            if (qs < 0 || qe > n - 1 || qs > qe) {
                System.out.println("Invalid Input");
                return -1;
            }
            return getSumUtil(0, n - 1, qs, qe, 0);
        }

        // A recursive function that constructs Segment Tree for array[ss..se].
        // si is index of current node in segment tree st
        int constructSTUtil(int arr[], int ss, int se, int si) {
            // If there is one element in array, store it in current node of
            // segment tree and return
            if (ss == se) {
//               / System.out.println(si +":" + arr[ss]);
                st[si] = arr[ss];
                return arr[ss];
            }

            // If there are more than one elements, then recur for left and
            // right subtrees and store the sum of values in this node
            int mid = getMid(ss, se);
            st[si] = Math.max(constructSTUtil(arr, ss, mid, si * 2 + 1) ,
                    constructSTUtil(arr, mid + 1, se, si * 2 + 2));
            return st[si];
        }


    }
}


