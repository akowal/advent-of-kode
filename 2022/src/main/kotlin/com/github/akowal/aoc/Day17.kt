package com.github.akowal.aoc

import kotlin.math.min

class Day17 {
    private val wind = inputFile("day17").readLines().single().map {
        when (it) {
            '>' -> 1
            else -> -1
        }
    }

    private val shapes = listOf(
        intArrayOf(
            0b0011110,
        ),
        intArrayOf(
            0b0001000,
            0b0011100,
            0b0001000,
        ),
        intArrayOf(
            0b0011100,
            0b0000100,
            0b0000100,
        ),
        intArrayOf(
            0b0010000,
            0b0010000,
            0b0010000,
            0b0010000,
        ),
        intArrayOf(
            0b0011000,
            0b0011000,
        ),
    )

    fun solvePart1(): Int {
        val chamber = mutableListOf<Int>()

        repeat(2022) { n ->
                val windShift = calcWindShift(n)
            val shape = shapes[n % shapes.size].shifted(windShift)

//            println(">>>>> $n")
//            println(shape.render())
//            println("---<")
            chamber.asReversed().forEach { println(it.render())}

            val stopDepth = (chamber.lastIndex downTo 0).firstOrNull {
                collides(it, chamber, shape)
            }?.inc() ?: 0

            if (stopDepth + shape.size >= chamber.size) {
                repeat(stopDepth + shape.size - chamber.size) { chamber.add(0) }
            }

            shape.forEachIndexed { i, layer ->
                chamber[stopDepth + i] = chamber[stopDepth + i] or layer
            }

            println("--->")
            chamber.asReversed().forEach { println(it.render())}
        }

        return chamber.size - 1
    }

    private fun collides(depth: Int, chamber: List<Int>, shape: IntArray): Boolean {
        if (chamber.isEmpty() || depth < 0) {
            return true
        }

        for (i in depth until min(depth + shape.size, chamber.size)) {
            if (chamber[i] and shape[i - depth] != 0) {
                return true
            }
        }

        return false
    }

    private fun calcWindShift(i: Int): Int {
        var offset = 0
        repeat(3) { j ->
            offset += wind[(i + j) % wind.size]
        }
        return offset
    }

    fun solvePart2(): Int {
        return 0
    }


    fun IntArray.render() = joinToString("\n") { it.render() }

    fun Int.render() = "%7s".format(Integer.toBinaryString(this)).replace(' ', '.').replace('0', '.').replace('1', '#')

    private fun IntArray.shifted(n: Int): IntArray {
        val rightSpace = minOf(Int::countTrailingZeroBits)
        val leftSpace = 7 - maxOf(Int::takeHighestOneBit).shl(1).countTrailingZeroBits()
        return when {
            n > 0 -> map { layer -> layer shr min(n, rightSpace) }.toIntArray()
            n < 0 -> map { layer -> layer shl min(-n, leftSpace) }.toIntArray()
            else -> this
        }
    }
}

fun main() {
    val solution = Day17()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
