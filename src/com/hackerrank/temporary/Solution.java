package com.hackerrank.temporary;

import static com.hackerrank.math.Binomial.nCrModPFermat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

import com.hackerrank.graphs.DisjointSet;
import lombok.Builder;
import lombok.Getter;

public class Solution {

    @Builder
    @Getter
    static class Row {
        int from, to;
        char color;
    }

    private static final Character BLACK = 'b';
    private static long MOD = 1000000007;

    static OutputStreamWriter outputStreamWriter;

    static {
        try {
            outputStreamWriter = (System.getenv("OUTPUT_PATH") != null) ?
                    new FileWriter(System.getenv("OUTPUT_PATH")) :
                    new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        int n = Integer.parseInt(scanner
                                         .nextLine()
                                         .trim());

        Row[] gb = new Row[n-1];

        for (int gbRowItr = 0; gbRowItr < n-1; gbRowItr++) {
            String[] gbRowItems = scanner
                    .nextLine()
                    .split(" ");

            gb[gbRowItr] = Row
                    .builder()
                    .from(Integer.parseInt(gbRowItems[0]))
                    .to(Integer.parseInt(gbRowItems[1]))
                    .color(gbRowItems[2].charAt(0))
                    .build();
        }
        componentsInGraph(gb, n);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    private static void componentsInGraph(final Row[] gb, final int n) {
        DisjointSet<Integer> disjointSet = new DisjointSet<>(n);
        Arrays.stream(gb).forEach(r -> {
                if(r.getColor() == BLACK){
                disjointSet.union(DisjointSet.createElement(r.getFrom(), r.getFrom()),
                                  DisjointSet.createElement(r.getTo(), r.getTo()));
            }
        });
        //System.out.println(disjointSet);
        long totalImPossibleOnes = disjointSet.getConnectedComponents()
                   .stream()
                   .mapToInt(c -> c.size())
                   .mapToLong(s -> ((NC2(s)) * (n-s))  + NC3(s))
                   .sum();

        final long total = NC3(n);
        System.out.println(total);
        System.out.println(totalImPossibleOnes);
        System.out.println(total - totalImPossibleOnes);
        System.out.println((total - totalImPossibleOnes) % MOD);
    }

    private static long NC3(long N){
        return ((N % MOD) * ((N-1) % MOD) * ((N-2) % MOD))/6;
    }

    private static long NC2(long N){
        return ((N % MOD) * ((N-1) % MOD) )/2;
    }





}

