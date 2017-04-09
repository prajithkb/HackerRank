package com.hackerrank.solutions;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by kprajith on 3/30/2017.
 */
public class ContainerOfBalls {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) {
            int n = in.nextInt();
            long[][] M = new long[n][n];
            for (int M_i = 0; M_i < n; M_i++) {
                for (int M_j = 0; M_j < n; M_j++) {
                    M[M_i][M_j] = in.nextLong();
                }
            }
            long[] containers = new long[n];
            long[] balls = new long[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    containers[i] += M[i][j];
                    balls[i] += M[j][i];
                }
            }
            System.out.println(Arrays.stream(M).map(m -> Arrays.stream(m).sum()).collect(Collectors.toList()));
            Arrays.sort(containers);
            Arrays.sort(balls);
            System.out.println(Arrays.equals(balls, containers) ? "Possible" : "Impossible");
        }

    }
}
