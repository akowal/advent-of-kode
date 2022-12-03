package com.github.akowal.aoc

import com.github.akowal.aoc.Day02.Shape.*

class Day02 {
    private val pairs = inputScanner("day02").let { input ->
        val result = mutableListOf<Pair<Char, Char>>()
        while (input.hasNextLine()) {
            val line = input.nextLine()
            result += line[0] to line[2]
        }
        result
    }

    fun solvePart1(): Int {
        val shapes = mapOf(
            'A' to ROCK,
            'X' to ROCK,
            'B' to PAPER,
            'Y' to PAPER,
            'C' to SCISSORS,
            'Z' to SCISSORS,
        )

        return pairs.sumOf {
            val other = shapes.getValue(it.first)
            val your = shapes.getValue(it.second)
            score(other, your)
        }
    }

    fun solvePart2(): Int {
        val shapes = mapOf(
            'A' to ROCK,
            'B' to PAPER,
            'C' to SCISSORS,
        )
        val winners = mapOf(
            ROCK to PAPER,
            PAPER to SCISSORS,
            SCISSORS to ROCK,
        )
        val losers = mapOf(
            ROCK to SCISSORS,
            PAPER to ROCK,
            SCISSORS to PAPER,
        )

        return pairs.sumOf {
            val other = shapes.getValue(it.first)
            val your = when(it.second) {
                'X' -> losers.getValue(other)
                'Z' -> winners.getValue(other)
                else -> other
            }
            score(other, your)
        }
    }

    enum class Shape {
        ROCK {
            override fun beats(other: Shape) = other == SCISSORS
        },
        PAPER {
            override fun beats(other: Shape) = other == ROCK
        },
        SCISSORS {
            override fun beats(other: Shape) = other == PAPER
        };

        abstract infix fun beats(other: Shape): Boolean
    }

    private fun score(other: Shape, yours: Shape): Int {
        var score = when (yours) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
        }
        score += when {
            other == yours -> 3
            yours beats other -> 6
            else -> 0
        }
        return score
    }
}

fun main() {
    val solution = Day02()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
