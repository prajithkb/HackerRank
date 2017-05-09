package com.hackerrank.trees.bit;

/**
 * Created by kprajith on 5/9/2017.
 */
public class TwoDFenWickTree {

    int[][] bit = new int[100][100];
    int r, c;

    public TwoDFenWickTree(int[][] mat, int r, int c) {
        this.r = r;
        this.c = c;
        construct_bittree(mat, r, c);
    }

    int sum(int x, int y) {
        int ans = 0;
        while (x > 0) {
            int Y = y;
            while (Y > 0) {
                ans += bit[x][Y];
                Y -= (Y & -Y);
            }
            x -= (x & -x);
        }
        return ans;
    }

    int getsum(int x1, int y1, int x2, int y2) {
        int n1 = sum(x2, y2);
        int n2 = sum(x1 - 1, y1 - 1);
        int n3 = sum(x1 - 1, y2);
        int n4 = sum(x2, y1 - 1);
        //System.out.format("%d %d %d %d\n", n1, n2, n3, n4);
        return (n1 + n2 - n3 - n4);
    }

    void update(int x, int y, int val) {
        while (x <= r) {
            int Y = y;
            while (Y <= c) {
                bit[x][Y] += val;
                Y += (Y & -Y);
            }
            x += (x & -x);
        }
    }

    void construct_bittree(int mat[][], int r, int c) {
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                update(i, j, mat[i-1][j-1]);
            }
        }
    }

    public static void main(String[] args) {
        int x1, y1, x2, y2, ans;
        int mat[][] =  {{1, 2, 3, 4},
                        {5, 3, 8, 1},
                        {4, 6, 7, 5},
                        {2, 4, 8, 9}};
        TwoDFenWickTree t = new TwoDFenWickTree(mat,4,4);
        int[][] q = {{2, 2, 4, 4}, {2, 3, 3, 3}, {1, 1, 1, 1}};
        for (int i = 0; i < q.length; i++) {
            System.out.println(t.getsum(q[i][0], q[i][1], q[i][2], q[i][3]));
        }
    }
}
