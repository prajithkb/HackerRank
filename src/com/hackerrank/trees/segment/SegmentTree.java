package com.hackerrank.trees.segment;


import java.util.Arrays;

class SegmentTree {
    long[] tree;  // To store segment tree
    long[] lazy;  // To store pending updates
    final int n;
    static long MOD = (long) (Math.pow(10, 9) + 7);


    SegmentTree(long[] input) {
        n = input.length;
        //Height of segment tree
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

        //Maximum size of segment tree
        int maxSize = 2 * (int) Math.pow(2, x) - 1;

        tree = new long[maxSize]; // Memory allocation
        lazy = new long[maxSize];
        Arrays.fill(tree,1);
        initialize(input, 0, n - 1, 0);
        System.out.println(Arrays.toString(tree));
    }

    /*  si -> index of current node in segment tree
        ss and se -> Starting and ending indexes of elements for
                     which current nodes stores product.
        us and eu -> starting and ending indexes of update query
        ue  -> ending index of update query
        diff -> which we need to add in the range us to ue */
    void update(int si, int ss, int se, int us,
                int ue, long diff) {
        // If lazy value is non-zero for current node of segment
        // tree, then there are some pending updates. So we need
        // to make sure that the pending updates are done before
        // making new updates. Because this value may be used by
        // parent after recursive calls (See last line of this
        // function)
        if (lazy[si] != 0) {
            // Make pending updates using value stored in lazy
            // nodes
//            tree[si] += (se - ss + 1) * lazy[si];
            tree[si] = modPow(lazy[si], se - ss + 1);

            // checking if it is not leaf node because if
            // it is leaf node then we cannot go further
            if (ss != se) {
                // We can postpone updating children we don't
                // need their new values now.
                // Since we are not yet updating children of si,
                // we need to set lazy flags for the children
                lazy[si * 2 + 1] = lazy[si];
                lazy[si * 2 + 2] = lazy[si];
            }

            // Set the lazy value for current node as 0 as it
            // has been updated
            lazy[si] = 0;
        }

        // out of range
        if (ss > se || ss > ue || se < us)
            return;

        // Current segment is fully in range
        if (ss >= us && se <= ue) {
            // Add the difference to current node
//            tree[si] += (se - ss + 1) * diff;
            tree[si] = modPow(diff, se - ss + 1);

            // same logic for checking leaf node or not
            if (ss != se) {
                // This is where we store values in lazy nodes,
                // rather than updating the segment tree itelf
                // Since we don't need these updated values now
                // we postpone updates by storing values in lazy[]
                lazy[si * 2 + 1] = diff;
                lazy[si * 2 + 2] = diff;
            }
            return;
        }

        // If not completely in rang, but overlaps, recur for
        // children,
        int mid = (ss + se) / 2;
        update(si * 2 + 1, ss, mid, us, ue, diff);
        update(si * 2 + 2, mid + 1, se, us, ue, diff);

        // And use the result of children calls to update this
        // node
//        tree[si] = tree[si * 2 + 1] * tree[si * 2 + 2];
        tree[si] = modProduct(tree[si * 2 + 1], tree[si * 2 + 2]);
    }

    // Function to update a range of values in segment
    // tree
    /*  us and eu -> starting and ending indexes of update query
        ue  -> ending index of update query
        diff -> which we need to add in the range us to ue */
    void update(int us, int ue, long diff) {

        update(0, 0, n - 1, us, ue, diff);
    }

    /*  A recursive function to get the product of values in given
        range of the array. The following are parameters for
        this function.
        si --> Index of current node in the segment tree.
               Initially 0 is passed as root is always at'
               index 0
        ss & se  --> Starting and ending indexes of the
                     segment represented by current node,
                     i.e., tree[si]
        qs & qe  --> Starting and ending indexes of query
                     range */
    long product(int ss, int se, int qs, int qe, int si) {
        // If lazy flag is set for current node of segment tree,
        // then there are some pending updates. So we need to
        // make sure that the pending updates are done before
        // processing the sub product query
        if (lazy[si] != 0) {
            // Make pending updates to this node. Note that this
            // node represents product of elements in arr[ss..se] and
            // all these elements must be increased by lazy[si]
//            tree[si] += (se - ss + 1) * lazy[si];
            tree[si] = modPow(lazy[si], se - ss + 1);

            // checking if it is not leaf node because if
            // it is leaf node then we cannot go further
            if (ss != se) {
                // Since we are not yet updating children os si,
                // we need to set lazy values for the children
                lazy[si * 2 + 1] = lazy[si];
                lazy[si * 2 + 2] = lazy[si];
            }

            // unset the lazy value for current node as it has
            // been updated
            lazy[si] = 0;
        }

        // Out of range
        if (ss > se || ss > qe || se < qs)
            return 1;

        // At this point sure, pending lazy updates are done
        // for current node. So we can return value (same as
        // was for query in our previous post)

        // If this segment lies in range
        if (ss >= qs && se <= qe)
            return tree[si];

        // If a part of this segment overlaps with the given
        // range
        int mid = (ss + se) / 2;
        return modProduct(product(ss, mid, qs, qe, 2 * si + 1),
                product(mid + 1, se, qs, qe, 2 * si + 2));
    }

    // Return product of elements in range from index qs (query
    // start) to qe (query end).  It mainly uses product()
    long product(int qs, int qe) {
        // Check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        System.out.println(Arrays.toString(lazy));
        return product(0, n - 1, qs, qe, 0);
    }

    /* A recursive function that constructs Segment Tree for
      array[ss..se]. si is index of current node in segment
      tree st. */
    void initialize(long[] arr, int ss, int se, int si) {
        // out of range as ss can never be greater than se
        if (ss > se)
            return;

        /* If there is one element in array, store it in
         current node of segment tree and return */
        if (ss == se) {
            tree[si] = arr[ss];
            return;
        }

        /* If there are more than one elements, then recur
           for left and right subtrees and store the product
           of values in this node */
        int mid = (ss + se) / 2;
        initialize(arr, ss, mid, si * 2 + 1);
        initialize(arr, mid + 1, se, si * 2 + 2);

        tree[si] = modProduct(tree[si * 2 + 1], tree[si * 2 + 2]);
    }

    /* Function to construct segment tree from given array.
       This function allocates memory for segment tree and
       calls initialize() to fill the allocated memory */
    void initialize(long[] arr) {
        // Fill the allocated memory st
        initialize(arr, 0, n - 1, 0);
    }

    long modPow(long x, int y) {
        long p = MOD;
        long res = 1;      // Initialize result

        x = x % p;  // Update x if it is more than or
        // equal to p

        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) > 0) {
                res = (res * x) % p;
            }
            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }

    long modProduct(long x, long y) {
        return (x % MOD * y % MOD) % MOD;
    }


    // Driver program to test above functions
    public static void main(String args[]) {
        long[] arr = {1, 3, 5, 7, 9, 11};
        int n = arr.length;
        SegmentTree tree = new SegmentTree(arr);

        // Print product of values in array from index 1 to 3
        System.out.println("Product of values in given range = " +
                tree.product(1, 3));

        // Add 10 to all nodes at indexes from 1 to 5.
        tree.update(0, 4, 10);

        // Find product after the value is updated
        System.out.println("Updated product of values in given range = " +
                tree.product(1, 2));

        // Find product after the value is updated
        System.out.println("Updated product of values in given range = " +
                tree.product(4, 5));
    }
}
