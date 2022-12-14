package com.github.akowal.aoc

import kotlin.math.max
import kotlin.math.min

class Day14 {
    private val map = inputFile("day14").readLines()
        .map { line ->
            line.split(" -> ").map { point ->
                point.split(",").map(String::toInt).let { (x, y) -> Point(x, y) }
            }
        }
        .flatMap { wall ->
            wall.windowed(2, 1).flatMap { (a, b) ->
                if (a.x == b.x) {
                    (min(a.y, b.y)..max(a.y, b.y)).map { y -> Point(a.x, y) }
                } else {
                    (min(a.x, b.x)..max(a.x, b.x)).map { x -> Point(x, a.y) }
                }
            }
        }.toMutableSet()

    private val origin = Point(500, 0)
    private var stableGrains = 0
    private var totalGrains = 0

    init {
        val floor = map.maxOf(Point::y) + 1
        while (origin !in map) {
            var grain = origin
            while (grain.canFall() && grain.y < floor) {
                when {
                    grain.down !in map -> grain = grain.down
                    grain.ldown !in map -> grain = grain.ldown
                    grain.rdown !in map -> grain = grain.rdown
                }
                if (grain.y >= floor && stableGrains == 0) {
                    stableGrains = totalGrains
                }
            }
            map += grain
            totalGrains++
        }
    }


    fun solvePart1(): Int {
        return stableGrains
    }

    fun solvePart2(): Int {
        return totalGrains
    }

    private val Point.down get() = Point(x, y + 1)
    private val Point.ldown get() = Point(x - 1, y + 1)
    private val Point.rdown get() = Point(x + 1, y + 1)
    private fun Point.canFall() = down !in map || ldown !in map || rdown !in map
}

fun main() {
    val solution = Day14()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
