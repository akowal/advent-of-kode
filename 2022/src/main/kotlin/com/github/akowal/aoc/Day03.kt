package com.github.akowal.aoc

class Day03 {
    private val rucksacks = inputFile("day03").readLines()

    fun solvePart1(): Int {
        return rucksacks.sumOf { rucksack ->
            val items1 = rucksack.take(rucksack.length / 2).toCharArray().toSet()
            val items2 = rucksack.drop(rucksack.length / 2).toCharArray().toSet()
            val common = items1.intersect(items2).single()
            val p = priority(common)
            p
        }
    }

    fun solvePart2(): Int {
        val groups = rucksacks.windowed(3, 3)
        return groups.sumOf { rucksack ->
            val badge = rucksack.map { it.toCharArray().toSet() }
                .reduce(Set<Char>::intersect)
                .single()
            priority(badge)
        }
    }

    private fun priority(c: Char) = when (c) {
        in 'a'..'z' -> 1 + c.code - 'a'.code
        in 'A'..'Z' -> 27 + c.code - 'A'.code
        else -> error("xoxoxo")
    }

}

fun main() {
    val solution = Day03()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
