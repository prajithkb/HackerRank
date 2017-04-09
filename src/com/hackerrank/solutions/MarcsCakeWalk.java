package com.hackerrank.solutions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MarcsCakeWalk {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Integer[] calories = new Integer[n];
        for(int calories_i=0; calories_i < n; calories_i++){
            calories[calories_i] = in.nextInt();
        }
        Arrays.sort(calories,Collections.reverseOrder());
        long output = 0;
        for(int calories_i=0; calories_i < n; calories_i++){
            output += Math.pow(2,calories_i) * calories[calories_i];
        }
        System.out.println(output);
    }
}

