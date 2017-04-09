package com.hackerrank.solutions;

import java.math.BigInteger;
import java.util.Scanner;

public class PrimeFactorization {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        BigInteger[] a = new BigInteger[n];
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = new BigInteger(in.next());
        }
        if(n == 1){
            System.out.println(a[0].add(BigInteger.ONE));
        }
        BigInteger[] prefixes = new BigInteger[n];
        BigInteger[] suffixes = new BigInteger[n];
        prefixes[0] = a[0];
        suffixes[n - 1] = a[n - 1];
        for (int i = 1; i < a.length; i++) {
            prefixes[i] = a[i].gcd(prefixes[i - 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            suffixes[i] = a[i].gcd(suffixes[i + 1]);
        }
        for (int i = 0; i < a.length; i++) {
            BigInteger gcd;
            if (i == 0) {
                gcd = suffixes[1];
            } else if (i == n - 1) {
                gcd = prefixes[n - 2];
            } else {
                gcd = prefixes[i - 1].gcd(suffixes[i + 1]);
            }
            if (!a[i].mod(gcd).equals(BigInteger.ZERO)) {
                System.out.println(gcd);
                return;
            }
        }
    }
}
