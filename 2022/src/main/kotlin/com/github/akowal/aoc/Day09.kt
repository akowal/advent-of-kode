package com.github.akowal.aoc

import com.github.akowal.aoc.Direction.D
import com.github.akowal.aoc.Direction.L
import com.github.akowal.aoc.Direction.R
import com.github.akowal.aoc.Direction.U
import kotlin.math.abs
import kotlin.math.sign

class Day09 {
    private val headRoute = inputFile("day09").readLines().flatMap { line ->
        val (dir, dist) = line.split(' ').let { it[0] to it[1].toInt() }
        List(dist) { Direction.valueOf(dir) }
    }.scan(Point(0, 0)) { prev, move ->
        prev.run {
            when (move) {
                L -> copy(x = x - 1)
                R -> copy(x = x + 1)
                U -> copy(y = y + 1)
                D -> copy(y = y - 1)
            }
        }
    }

    fun solvePart1(): Int {
        val tailRoute = follow(headRoute)
        return tailRoute.toSet().size
    }

    fun solvePart2(): Int {
        var knotRoute = headRoute
        repeat(9) {
            knotRoute = follow(knotRoute)
        }
        return knotRoute.toSet().size
    }

    private fun follow(route: List<Point>) = route.scan(Point(0, 0)) { tail, head ->
        val dx = tail.x - head.x
        val dy = tail.y - head.y
        when {
            abs(dx) < 2 && abs(dy) < 2 -> tail// no pull
            abs(dx) > abs(dy) -> head.copy(x = head.x + dx.sign)// horizontal pull
            abs(dy) > abs(dx) -> head.copy(y = head.y + dy.sign)// vertical pull
            else -> Point(x = head.x + dx.sign, y = head.y + dy.sign) // diagonal pull
        }
    }
}

enum class Direction {
    L, R, U, D
}

fun main() {
    val solution = Day09()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
