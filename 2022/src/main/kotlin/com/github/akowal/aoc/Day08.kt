package com.github.akowal.aoc

import kotlin.math.max

class Day08 {
    private val grid = inputFile("day08").readLines().map { it.map(Char::digitToInt) }
    private val cols = grid.size
    private val rows = grid.first().size

    fun solvePart1(): Int {
        var visible = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val h = grid[row][col]
                if (paths(row, col).any { path -> path.all { it < h } }) {
                    visible++
                }
            }
        }
        return visible
    }

    fun solvePart2(): Int {
        var maxScore = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val h = grid[row][col]
                val score = paths(row, col).map { path ->
                    path.indexOfFirst { it >= h }.let {
                        if (it >= 0) it + 1 else path.size
                    }
                }.reduce { a, b -> a * b }

                maxScore = max(score, maxScore)
            }
        }
        return maxScore
    }

    private fun paths(row: Int, col: Int) = listOf(
        grid[row].slice(col - 1 downTo 0),
        grid[row].slice(col + 1 until cols),
        grid.slice(row - 1 downTo 0).map { it[col] },
        grid.slice(row + 1 until cols).map { it[col] },
    )
}

fun main() {
    val solution = Day08()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
