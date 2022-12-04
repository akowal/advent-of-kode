package com.github.akowal.aoc

class Day04 {
    private val assignments = inputScanner("day04").let { input ->
        val result = mutableListOf<Pair<IntRange, IntRange>>()
        while (input.hasNextLine()) {
            val (a, b, c, d) = input.nextLine().split('-', ',').map { it.toInt() }
            result += (a..b) to (c..d)
        }
        result
    }

    fun solvePart1(): Int {
        return assignments.count { (a, b) ->
            (a.first in b && a.last in b) || (b.first in a && b.last in a)
        }
    }

    fun solvePart2(): Int {
        return assignments.count { (a, b) ->
            a.first in b || a.last in b || b.first in a || b.last in a
        }
    }
}

fun main() {
    val solution = Day04()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
