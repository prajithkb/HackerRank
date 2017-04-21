package com.hackerrank.suffix;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by kprajith on 4/21/2017.
 */
public class SuffixArray {

    int[][] suffixRank = new int[20][10001];

    static class SuffixTuple {
        int index;
        int firstRank;
        int secondRank;

        public SuffixTuple(int index, int firstRank, int secondRank) {
            this.index = index;
            this.firstRank = firstRank;
            this.secondRank = secondRank;
        }

        public int getIndex() {
            return index;
        }

        public int getFirstRank() {
            return firstRank;
        }

        public int getSecondRank() {
            return secondRank;
        }

        static Comparator<SuffixTuple> SUFFIX_TUPLE_COMPARATOR = Comparator
                .comparing(SuffixTuple::getFirstRank)
                .thenComparing(SuffixTuple::getSecondRank);
    }

    private int N;

    char[] word;

    SuffixTuple[] suffixTuples;

    int[] suffixArray;

    int[] lcp;

    public int[] getSuffixArray() {
        return suffixArray;
    }

    SuffixArray(String word) {
        initialize(word);
        createSuffixArray();
        createLCP();
    }

    private void initialize(String word) {
        N = word.length();
        suffixTuples = new SuffixTuple[N];
        this.word = word.toCharArray();
        suffixArray = new int[N];
        for (int i = 0; i < N; i++) {
            suffixRank[0][i] = this.word[i] - 'a';
        }
    }

    private void createSuffixArray() {
        for (int count = 1, step = 1; count < N; count *= 2, ++step) {
            for (int i = 0; i < N; ++i) {
                suffixTuples[i] = new SuffixTuple(i,
                        suffixRank[step - 1][i],
                        i + count < N ? suffixRank[step - 1][i + count] : -1);
            }
            // On the basis of tuples obtained sort the tuple array
            Arrays.sort(suffixTuples, SuffixTuple.SUFFIX_TUPLE_COMPARATOR);
            // Initialize rank for rank 0 suffix after sorting to its original index
            // in suffixRank array
            suffixRank[step][suffixTuples[0].getIndex()] = 0;
            for (int i = 1, currRank = 0; i < N; ++i) {
                // compare ith ranked suffix ( after sorting ) to (i - 1)th ranked suffix
                // if they are equal till now assign same rank to ith as that of (i - 1)th
                // else rank for ith will be currRank ( i.e. rank of (i - 1)th ) plus 1, i.e ( currRank + 1 )
                if (suffixTuples[i - 1].getFirstRank() != suffixTuples[i].getFirstRank()
                        || suffixTuples[i - 1].getSecondRank() != suffixTuples[i].getSecondRank())
                    ++currRank;

                suffixRank[step][suffixTuples[i].getIndex()] = currRank;
            }

        }
        for (int i = 0; i < N; i++) {
            suffixArray[i] = suffixTuples[i].getIndex();

        }
    }

    private void createLCP() {
        int k = 0;
        int[] rank = new int[N];
        int[] lcp = new int[N];

        for (int i = 0; i < N; i++) rank[suffixArray[i]] = i;

        for (int i = 0; i < N; i++) {
            if (rank[i] == N - 1) {
                k = 0;
                continue;
            }
            int j = suffixArray[rank[i] + 1];
            while (i + k < N && j + k < N && word[i + k] == word[j + k]) {
                k++;
            }
            lcp[rank[i]] = k;
            if(k>0){
                k--;
            }
        }
        this.lcp = lcp;
    }

    public String toString() {
        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++) {
            suffixes[i] = new String(Arrays.copyOfRange(word,suffixArray[i],N));
        }
        return String.format("suffix array - %s\nlcp array    - %s\nsuffixes - %s",
                Arrays.toString(suffixArray), Arrays.toString(lcp),Arrays.toString(suffixes));

    }

    public static void main(String[] s) {
        System.out.println(new SuffixArray("aaaaaa"));
        System.out.println(new SuffixArray("abcabcddd"));
    }
}
