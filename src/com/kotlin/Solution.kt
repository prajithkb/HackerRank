import java.util.*

/*
 * Complete the solve function below.
 */
fun solve(t: Array<Int>, size: Int): Int {
    val intervals = getIntervals(t , size)
//    println(intervals.joinToString(","))
    return findMaximumOverlappingPoint(intervals)

}

fun findMaximumOverlappingPoint(intervals: List<Pair<Int , Int>>) : Int {
    val arrivalExitPairs = intervals
            .map { listOf(Pair(it.first, "Arrival"), Pair(it.second, "Exit") )}
            .flatten()
            .sortedWith(compareBy({ it.first }, { it.second }))
    var maximumOverlapCount = 0
    var overlapCount = 0
    var pointOfMaximumOverlap = 0
    arrivalExitPairs.forEach{ (time, type) ->
        when (type) {
            "Arrival" -> {
                overlapCount++
                if( maximumOverlapCount < overlapCount) {
                    maximumOverlapCount = overlapCount
                    pointOfMaximumOverlap = time
                }
            }
            "Exit" -> {
                overlapCount --
            }
        }
    }
//    println(arrivalExitPairs.joinToString(","))
//    println("Maximum overlapping count $maximumOverlapCount")
//    println("Maximum overlapping point: $pointOfMaximumOverlap")
    return pointOfMaximumOverlap + 1
}

fun getIntervals(timeTakenByChildren: Array<Int> ,
                 size: Int): List<Pair<Int , Int>> {
    return timeTakenByChildren.withIndex().map { (index , value) ->
        val possibleIntervals = mutableListOf<Pair<Int , Int>>()
        if (index >= value) {
            possibleIntervals.add(Pair(0 , index - value))
            if(index < size -1){
                possibleIntervals.add(Pair(minOf(index + 1 , size - 1) , size - 1))
            }
        } else {
            possibleIntervals.add(Pair(minOf(index + 1 , size - 1) , minOf(size - value + index , size - 1)))
        }
        possibleIntervals
    }.flatten()
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)
    val tCount = scan
            .nextLine()
            .trim()
            .toInt()
    val t = scan
            .nextLine()
            .split(" ")
            .map { it.trim().toInt() }
            .toTypedArray()
    val id = solve(t, tCount)
    println(id)
}

/*
7
4 2 3 3 0 2 6
 */