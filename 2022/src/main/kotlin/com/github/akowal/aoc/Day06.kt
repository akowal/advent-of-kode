package com.github.akowal.aoc

class Day06 {
    private val data = inputFile("day06").readLines().single()

    fun solvePart1(): Int {
        return solve(4)
    }

    fun solvePart2(): Int {
        return solve(14)
    }

    private fun solve(len: Int) = len + data.asSequence()
        .windowed(len, 1)
        .indexOfFirst { it.toSet().size == len }
}

fun main() {
    val solution = Day06()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
