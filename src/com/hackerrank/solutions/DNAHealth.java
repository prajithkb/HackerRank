package com.hackerrank.solutions;

import java.util.Scanner;

public class DNAHealth {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] genes = new String[n];
        for(int genes_i=0; genes_i < n; genes_i++){
            genes[genes_i] = in.next();
        }
        int[] health = new int[n];
        for(int health_i=0; health_i < n; health_i++){
            health[health_i] = in.nextInt();
        }
        int s = in.nextInt();
        for(int a0 = 0; a0 < s; a0++){
            int first = in.nextInt();
            int last = in.nextInt();
            String d = in.next();
            // your code goes here
        }
    }


}