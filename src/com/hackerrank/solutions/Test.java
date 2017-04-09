package com.hackerrank.solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by kprajith on 7/30/2016.
 */
public class Test {
    public static class IOUtils implements AutoCloseable {
        BufferedReader br;
        StringTokenizer st;
        boolean eof;

        IOUtils() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextToken() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    eof = true;
                    return null;
                }
            }
            return st.nextToken();
        }

        String nextString() {
            return nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(nextToken());
        }

        long nextLong() throws IOException {
            return Long.parseLong(nextToken());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(nextToken());
        }

        @Override
        public void close() throws Exception {
            br.close();
            eof = true;
        }

    }

    public static void main(String[] args) throws Exception {
        try (IOUtils ioUtils = new IOUtils()) {
            int N = ioUtils.nextInt();
            int [] itemCount = new int[101];
            // populate the integer array with all the count of the values;
            for (int i = 0; i < N ; i++) {
                int item = ioUtils.nextInt();
                itemCount[item]++;
            }
            final int result = N - Arrays.stream(itemCount).max().getAsInt();
            System.out.println(result);
        }

    }
}
