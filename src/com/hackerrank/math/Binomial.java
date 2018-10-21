package com.hackerrank.math;

public class Binomial {
    /* Iterative Function to calculate
    (x^y)%p in O(log y) */
    static long power(long x, long y, long p) {
        // Initialize result
        long res = 1;
        // Update x if it is more than or
        // equal to p
        x = x % p;
        while (y > 0) {
            // If y is odd, multiply x
            // with result
            if (y % 2 == 1) {
                res = (res % p * x % p) % p;
            }
            // y must be even now
            y = y >> 1; // y = y/2
            x = ((x % p) * (x % p)) % p;
        }

        return res;
    }

    // Returns n^(-1) mod p
    static long modInverse(long n, long p) {
        return power(n, p - 2, p) % p;
    }

    // Returns nCr % p using Fermat's
    // little theorem.
    public  static long nCrModPFermat(
            int n, int r, long p) {
        if (r > n) {
            return 0;
        }
        // Base case
        if (r == 0) {
            return 1;
        }

        // Fill factorial array so that we
        // can find all factorial of r, n
        // and n-r
        long[] fac = new long[n + 1];
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = (fac[i - 1] % p) * (i % p) % p;
        }
        return ((fac[n] % p * modInverse(fac[r], p) % p) % p) % p * (modInverse(fac[n - r], p) % p) % p;
    }

}
