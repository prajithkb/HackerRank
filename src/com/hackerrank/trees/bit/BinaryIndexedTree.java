package com.hackerrank.trees.bit;


import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

/**
 * We have an array arr[0 . . . n-1]. We should be able to
 1 Find the sum of first i elements.
 2 Change value of a specified element of the array arr[i] = x where 0 <= i <= n-1.
 */
public class BinaryIndexedTree {

    int[] BITree;

    BinaryOperator<Integer> operation;

    int length;

    BinaryIndexedTree(int[] input, BinaryOperator<Integer> operation) {
        this.operation = operation;
        this.length = input.length;
        this.BITree = new int[length + 1];
    }

    int operate(int a, int b) {
        return operation.apply(a, b);
    }

    int query(int index) {
        int sum = 0; // Iniialize result
        // index in BITree[] is 1 more than the index in arr[]
        index = index + 1;
        // Traverse ancestors of BITree[index]
        while (index > 0) {
            // Add current element of BITree to sum
            sum = operate(BITree[index], sum);
            // Move index to parent node in query View
            index -= index & (-index);
        }
        return sum;
    }

    void update(int index, int val) {
        // index in BITree[] is 1 more than the index in arr[]
        index = index + 1;
        // Traverse all ancestors and add 'val'
        while (index <= length) {
            // Add 'val' to current node of BI Tree
            BITree[index] = operate(val, BITree[index]);
            // Update index to that of parent in update View
            index += index & (-index);
        }
    }



    public static void main(String[] args) {

        int freq[] = {2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9};
        BinaryIndexedTree bit = new BinaryIndexedTree(freq, (Integer a, Integer b) -> a + b);
        IntStream.range(0, freq.length).forEach(i -> bit.update(i, freq[i]));
        System.out.println(bit.query(5));
        bit.update(3,6);
        System.out.println(bit.query(5));
    }
}
