package com.github.akowal.aoc

class Day01 {
    private val nums = inputScanner("day01").let { input ->
        val result = mutableListOf<Int>()
        var current = 0
        while (input.hasNextLine()) {
            val line = input.nextLine()
            if (line.isEmpty()) {
                result += current
                current = 0
            } else {
                current += line.toInt()
            }
        }
        result
    }

    fun solvePart1(): Int {
        return nums.max()
    }

    fun solvePart2(): Int {
        return nums.sortedDescending().take(3).sum()
    }
}

fun main() {
    val solution = Day01()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
