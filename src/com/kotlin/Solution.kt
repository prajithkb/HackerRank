package com.kotlin

import java.net.URL
import java.util.*
import kotlin.system.measureTimeMillis

fun solve(tree: Array<Array<Int>> ,
          queries: Array<Array<Int>> ,
          size: Int): List<Long> {
    val numberOfPairs = numberOfPairs(tree , size + 1)
    return queries.map { (left , right) ->
        val leftSum = numberOfPairs.floorEntry(left - 1)?.value ?: 0
        val rightSum = numberOfPairs.floorEntry(right)?.value ?: 0
        (rightSum - leftSum)
    }
}

fun numberOfPairs(tree: Array<Array<Int>> ,
                  size: Int): TreeMap<Int , Long> {
    tree.sortBy { it[2] }
    return createCumulativePairs(createWeightToPairs(size , tree))
}

private fun createWeightToPairs(size: Int ,
                                tree: Array<Array<Int>>): SortedMap<Int , Long> {
    val numberOfPairs = emptyMap<Int , Long>().toSortedMap()
    val ds = DisjointSetKotlin<Int>(size)
    tree.forEach { (from , to , weight) ->
        val sizeOfFrom = ds.sizeOfConnectedComponents(from)
        val sizeOfTo = ds.sizeOfConnectedComponents(to)
        ds.union(from , to)
        val numberOfExistingPairs = numberOfPairs.getOrDefault(weight , 0)
        numberOfPairs.put(weight , numberOfExistingPairs + sizeOfFrom * sizeOfTo)
    }
    return numberOfPairs
}

private fun createCumulativePairs(numberOfPairs: SortedMap<Int , Long>): TreeMap<Int , Long> {
    return numberOfPairs.entries
            .foldIndexed(mutableListOf<Pair<Int,Long>>()) { index , acc , (key , value) ->
                if (index > 0) {
                    val (_ , sum) = acc.get(index - 1)
                    acc.add(Pair<Int , Long>(key , value + sum))
                } else {
                    acc.add(Pair<Int , Long>(key , value))
                }
                acc
            }
            .toMap(TreeMap())
}

val URL = "https://hr-testcases-us-east-1.s3.amazonaws.com/20577/input04.txt?AWSAccessKeyId=AKIAJ4WZFDFQTZRGO3QA&Expires=1540662398&Signature=i4zPWYWEJ9zwbSMNa2RZNUCSbg8%3D&response-content-type=text%2Fplain"

fun main(args: Array<String>) {
    val scan = Scanner(URL(URL).openStream())
    val elapsedTime = measureTimeMillis {
        val nq = scan.nextLine().split(" ")
        val n = nq[0].trim().toInt()
        val q = nq[1].trim().toInt()
        val tree = Array<Array<Int>>(n - 1 , { Array<Int>(3 , { 0 }) })
        for (treeRowItr in 0 until n - 1) {
            tree[treeRowItr] = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()
        }
        val queries = Array<Array<Int>>(q , { Array<Int>(2 , { 0 }) })
        for (queriesRowItr in 0 until q) {
            queries[queriesRowItr] = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()
        }

        val result = solve(tree , queries , n)
        println(result.joinToString("\n"))
    }
    println("Execution time: $elapsedTime ms")

}
