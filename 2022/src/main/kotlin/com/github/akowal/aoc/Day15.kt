package com.github.akowal.aoc

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day15 {
    private val sensor2beacon: Map<Point, Point>

    init {
        val pattern = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
        sensor2beacon = inputFile("day15").readLines().associate { line ->
            val coord = pattern.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
            val sensor = Point(coord[0], coord[1])
            val beacon = Point(coord[2], coord[3])
            sensor to beacon
        }
    }

    fun solvePart1(): Int {
        val row = 2_000_000
        val coverage = rowCoverage(row)
        val beaconsOverlap = sensor2beacon.values.toSet().count { b ->
            coverage.any { b.y == row && b.x in it }
        }

        return coverage.sumOf { it.last - it.first + 1 } - beaconsOverlap
    }

    private fun rowCoverage(row: Int): Set<IntRange> {
        val coverage = mutableSetOf<IntRange>()

        for ((sensor, beacon) in sensor2beacon) {
            val d1 = sensor.distanceTo(beacon)
            val d2 = sensor.distanceTo(sensor.copy(y = row))
            if (d2 >= d1) {
                continue
            }

            var x1 = sensor.x - (d1 - d2)
            var x2 = sensor.x + (d1 - d2)

            val overlapping = coverage.filter { it.overlaps(x1..x2) }.toSet()
            if (overlapping.isNotEmpty()) {
                coverage -= overlapping
                x1 = min(x1, overlapping.minOf { it.first })
                x2 = max(x2, overlapping.maxOf { it.last })
            }

            coverage += x1..x2
        }
        return coverage
    }

    fun solvePart2(): Long {
        val limit = 4_000_000
        for(y in 0..limit) {
            val coverage = rowCoverage(y)
            for (segment in coverage) {
                when {
                    segment.first > 0 -> return tuningFreq(segment.first - 1, y)
                    segment.last < limit -> return tuningFreq(segment.last + 1, y)
                }
            }
        }
        error("xoxoxo")
    }

    private fun tuningFreq(x: Int, y: Int) = x * 4_000_000L + y

    private fun Point.distanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
}

fun main() {
    val solution = Day15()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
