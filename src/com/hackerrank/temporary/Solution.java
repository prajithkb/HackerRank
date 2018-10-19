package com.hackerrank.temporary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.hackerrank.graphs.ConnectedComponents;

public class Solution {



    static int[] componentsInGraph(int[][] gb) {
        ConnectedComponents connectedComponents = ConnectedComponents
                .builder()
                .build();
        for (int i = 0; i < gb.length; i++) {
            connectedComponents.connect(gb[i][0], gb[i][1]);
        }
        final List<Set<ConnectedComponents.Node>> connectedNodes = connectedComponents.getConnectedNodes();
        final List<Integer> sizes = connectedNodes
                .stream()
                .mapToInt(cc -> cc.size())
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        return new int[] { sizes.get(0), sizes.get(sizes.size()-1) };

    }

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

        int[][] gb = new int[n][2];

        for (int gbRowItr = 0; gbRowItr < n; gbRowItr++) {
            String[] gbRowItems = scanner
                    .nextLine()
                    .split(" ");

            for (int gbColumnItr = 0; gbColumnItr < 2; gbColumnItr++) {
                int gbItem = Integer.parseInt(gbRowItems[gbColumnItr].trim());
                gb[gbRowItr][gbColumnItr] = gbItem;
            }
        }

        int[] result = componentsInGraph(gb);

        for (int SPACEItr = 0; SPACEItr < result.length; SPACEItr++) {
            bufferedWriter.write(String.valueOf(result[SPACEItr]));

            if (SPACEItr != result.length - 1) {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

