package com.hackerrank.trees.segment;


import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * Segment Tree
 */
class SegmentTree<T extends Comparable<T>> {

    SegmentTree(Supplier<Item<T>> supplier[]) {
        //Height of segment tree
        int x = (int) (Math.ceil(Math.log(supplier.length) / Math.log(2)));

        //Maximum size of segment tree
        MAX = 2 * (int) Math.pow(2, x) - 1;

        tree = new Item[MAX];
        lazy = new Item[MAX];
        Arrays.fill(tree, Item.NULL);
        Arrays.fill(lazy, Item.NULL);
        this.supplier = supplier;
        constructSTUtil(0, supplier.length - 1, 0);
    }

    Supplier<Item<T>> supplier[];

    final int MAX;        // Max tree size
    final Item[] tree;  // To store segment tree
    final Item[] lazy;  // To store pending updates

    // A utility function to get the middle index from corner indexes.
    int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    /*  A recursive function to get the sum of values in given range
        of the array.  The following are parameters for this function.

      st    --> Pointer to segment tree
      si    --> Index of current node in the segment tree. Initially
                0 is passed as root is always at index 0
      ss & se  --> Starting and ending indexes of the segment represented
                    by current node, i.e., st[si]
      qs & qe  --> Starting and ending indexes of query range */
    Item<T> getSumUtil(int ss, int se, int qs, int qe, int si) {
        // If segment of this node is a part of given range, then return
        // the sum of the segment
        if (qs <= ss && qe >= se)
            return tree[si];

        // If segment of this node is outside the given range
        if (se < qs || ss > qe)
            return Item.NULL;

        // If a part of this segment overlaps with the given range
        int mid = getMid(ss, se);
        return getSumUtil(ss, mid, qs, qe, 2 * si + 1).aggregate(
                getSumUtil(mid + 1, se, qs, qe, 2 * si + 2));
    }

    /* A recursive function to update the nodes which have the given
       index in their range. The following are parameters
        st, si, ss and se are same as getSumUtil()
        i    --> index of the element to be updated. This index is in
                 input array.
       diff --> Value to be added to all nodes which have i in range */
    void updateValueUtil(int ss, int se, int i, Item<T> diff, int si) {
        // Base Case: If the input index lies outside the range of
        // this segment
        if (i < ss || i > se)
            return;

        // If the input index is in range of this node, then update the
        // value of the node and its children
        tree[si] = tree[si].aggregate(diff);
        if (se != ss) {
            int mid = getMid(ss, se);
            updateValueUtil(ss, mid, i, diff, 2 * si + 1);
            updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
        }
    }

    // The function to update a value in input array and segment tree.
    // It uses updateValueUtil() to update the value in segment tree
    void updateValue(int arr[], int n, int i, Item<T> item) {
        // Check for erroneous input index
        if (i < 0 || i > n - 1) {
            System.out.println("Invalid Input");
            return;
        }
        // Update the values of nodes in segment tree
        updateValueUtil(0, n - 1, i, item, 0);
    }

    // Return sum of elements in range from index qs (quey start) to
    // qe (query end).  It mainly uses getSumUtil()
    Item<T> query(int n, int qs, int qe) {
        // Check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return Item.NULL;
        }
        return getSumUtil(0, n - 1, qs, qe, 0);
    }

    // A recursive function that constructs Segment Tree for array[ss..se].
    // si is index of current node in segment tree st
    Item<T> constructSTUtil(int ss, int se, int si) {
        // If there is one element in array, store it in current node of
        // segment tree and return
        if (ss == se) {
            //tree[si] = Item.<Integer>builder().aggregator(aggregator).multiplier(multiplier).value(arr[ss]).build();
            tree[si] = supplier[ss].get();
            return tree[si];
        }

        // If there are more than one elements, then recur for left and
        // right subtrees and store the sum of values in this node
        int mid = getMid(ss, se);
        tree[si] = constructSTUtil(ss, mid, si * 2 + 1).aggregate(
                constructSTUtil(mid + 1, se, si * 2 + 2));
        return tree[si];
    }

    static Supplier<Item<Integer>>[] suppliers(int[] arr) {
        BinaryOperator<Integer> aggregator = (Integer a, Integer b) ->  Integer.valueOf(a) + Integer.valueOf(b);
        ;
        BiFunction<Integer, Integer, Integer> multiplier = (Integer a, Integer b) -> Integer.valueOf(a) * Integer.valueOf(b);
        Supplier<Item<Integer>> supplier[] = new Supplier[arr.length];
        for (int i = 0; i < supplier.length; i++) {
            final int v = arr[i];
            supplier[i] = () -> Item.<Integer>builder().aggregator(aggregator).multiplier(multiplier).value(v).build();
        }
        return supplier;
    }


    public static class Item<T extends Comparable<T>> implements Comparable<T> {

        BinaryOperator<T> aggregator;

        BiFunction<Integer, T, T> multiplier;

        T value;

        static Item NULL;


        static {
            NULL = new Item();
        }

        public Item<T> clone() {
            Item<T> clone = new Item<T>();
            clone.aggregator = aggregator;
            clone.multiplier = multiplier;
            return clone;
        }

        private Item<T> clone(T value) {
            Item<T> clone = clone();
            clone.value = value;
            return clone;
        }

        Item<T> aggregate(Item<T> a) {
            if (aggregator == null) {
                return clone(a.value);
            } else {
                T v = aggregator.apply(value, a.value);
                return clone(v);
            }
        }

        Item<T> multiply(int numberOfItems) {
            if (multiplier == null) {
                return this;
            } else {
                T v = multiplier.apply(numberOfItems, value);
                return clone(v);
            }
        }


        @Override
        public int compareTo(T o) {
            return value.compareTo(o);
        }

        static <T extends Comparable<T>> ItemBuilder<T> builder() {
            ItemBuilder ItemBuilder = new ItemBuilder();
            ItemBuilder.Item = new Item();
            return ItemBuilder;
        }

        static class ItemBuilder<T extends Comparable<T>> {
            Item<T> Item;

            ItemBuilder<T> aggregator(BinaryOperator<T> aggregator) {
                Item.aggregator = aggregator;
                return this;
            }

            ItemBuilder<T> multiplier(BiFunction<Integer, T, T> multiplier) {
                Item.multiplier = multiplier;
                return this;
            }

            ItemBuilder<T> value(T value) {
                Item.value = value;
                return this;
            }

            Item<T> build() {
                return Item;
            }
        }


    }


    // Driver program to test above functions
    public static void main(String args[]) {
        int arr[] = {1, 3, 5, 7, 9, 11};
        int n = arr.length;
        SegmentTree tree = new SegmentTree(suppliers(arr));

        // Print sum of values in array from index 1 to 3
        System.out.println("Sum of values in given range = " +
                tree.query(n, 1, 3));

    }
}
