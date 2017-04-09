package com.hackerrank.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kprajith on 2/15/2017.
 */
public class FinallyTest {

    private static  List<String> getStrings() {
        try {
            int data = 25 / 0;
            System.out.println(data);
        } finally {
            return  new ArrayList<>();
        }
    }

    public static void main(String args[]) {
        System.out.println(getStrings());
    }
}
