package com.github.akowal.aoc

import kotlin.math.min



class Day12 {
    private val map: Map<Point, Int>
    private val start: Point
    private val end: Point

    init {
        val lines = inputFile("day12").readLines()
        start = lines.findPoint('S')
        end = lines.findPoint('E')
        map = lines.flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                val height = when (c) {
                    'S' -> 'a'.code
                    'E' -> 'z'.code
                    else -> c.code
                }
                Point(x, y) to height
            }
        }.toMap()
    }

    fun solvePart1(): Int {
        val dist = travel(end)
        return dist.getValue(start)
    }

    fun solvePart2(): Int {
        val dist = travel(end)
        return map.filter { it.value == 'a'.code }.minOf { (p, _) -> dist[p] ?: Int.MAX_VALUE }
    }

    private fun travel(from: Point): Map<Point, Int> {
        val visited = mutableSetOf<Point>()
        val dist = mutableMapOf<Point, Int>()
        var q = listOf(from)
        var d = 0

        dist[from] = 0

        while (q.isNotEmpty()) {
            val nextQ = mutableListOf<Point>()

            for (p in q) {
                if (!visited.add(p)) {
                    continue
                }
                dist[p] = min(d, dist[p] ?: Int.MAX_VALUE)
                p.neighbors().filter { it in map }.forEach { n ->
                    if (map.getValue(p) - map.getValue(n) <= 1) {
                        nextQ += n
                    }
                }
            }
            d++
            q = nextQ
        }
        return dist
    }

    private fun List<String>.findPoint(c: Char) = indexOfFirst { c in it }.let { row -> Point(this[row].indexOf(c), row) }

    private fun Point.neighbors() = listOf(
        Point(x - 1, y),
        Point(x + 1, y),
        Point(x, y - 1),
        Point(x, y + 1),
    )
}

fun main() {
    val solution = Day12()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
