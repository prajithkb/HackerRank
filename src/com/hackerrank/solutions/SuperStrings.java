package com.hackerrank.solutions;

import java.util.Stack;

public class SuperStrings {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String s = in.next();
//        long [] sum = new long[s.length()];
//        sum[0] = Long.valueOf(s.charAt(0));
//        for (int i = 1; i < s.length(); i++) {
//            sum[i] += sum[i-1];
//        }

        Stack a = new Stack();
        a.add(1);
        a.add(2);
        System.out.println(a);
        a.add(a.remove(0));
        System.out.println(a);
        System.out.println(a.pop());
        System.out.println(a.remove(0));
    }
}
