package com.github.akowal.aoc

import java.io.File

class Day02(input: File) : Problem<Int> {
    private val games = loadGames(input)

    override fun solve1(): Int {
        return games
            .filter { game -> game.grabs.all { it.r <= 12 && it.g <= 13 && it.b <= 14 } }
            .sumOf { it.n }
    }

    override fun solve2(): Int {
        return games.sumOf { game ->
            val minR = game.grabs.maxOf { it.r }
            val minG = game.grabs.maxOf { it.g }
            val minB = game.grabs.maxOf { it.b }
            minR * minG * minB
        }
    }

    private fun loadGames(f: File): List<Game> {
        return f.readLines().mapIndexed { i, line ->
            val grabs = line.substringAfter(':').split(';').map { it.asGrab() }
            Game(i + 1, grabs)
        }
    }

    private data class Game(
        val n: Int,
        val grabs: List<Grab>,
    )

    private data class Grab(
        val r: Int,
        val g: Int,
        val b: Int,
    )

    private fun String.asGrab(): Grab {
        var r = 0
        var g = 0
        var b = 0
        split(',').forEach {
            val (n, color) = it.trim().split(' ')
            when (color) {
                "red" -> r = n.toInt()
                "green" -> g = n.toInt()
                "blue" -> b = n.toInt()
                else -> error("xoxoxo: $color")
            }
        }
        return Grab(r, g, b)
    }
}

fun main() {
    Launcher("day02", ::Day02).run()
}
