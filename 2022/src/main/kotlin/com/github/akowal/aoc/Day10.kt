package com.github.akowal.aoc

import com.github.akowal.aoc.Day10.Cmd.Add
import com.github.akowal.aoc.Day10.Cmd.Noop
import kotlin.math.abs

class Day10 {
    private val cmds = inputFile("day10").readLines().map { line ->
        when (line) {
            "noop" -> Noop
            else -> Add(line.substringAfter(' ').toInt())
        }
    }

    fun solvePart1(): Int {
        var x = 1
        var cycle = 0
        var signals = 0

        for (cmd in cmds) {
            repeat(cmd.duration) {
                cycle++
                if ((cycle - 20) % 40 == 0) {
                    signals += cycle * x
                }
            }
            x += cmd.delta
        }

        return signals
    }

    fun solvePart2(): String {
        val out = StringBuilder()
        var x = 1
        var cycle = 0

        for (cmd in cmds) {
            repeat(cmd.duration) {
                val pos = cycle % 40
                cycle++

                if (abs(x - pos) <= 1) {
                    out.append('#')
                } else {
                    out.append('.')
                }

                if (cycle % 40 == 0) {
                    out.append('\n')
                }
            }
            x += cmd.delta
        }

        return out.toString()
    }

    private sealed interface Cmd {
        val duration: Int
        val delta: Int

        object Noop : Cmd {
            override val duration = 1
            override val delta = 0
        }

        data class Add(override val delta: Int) : Cmd {
            override val duration = 2
        }
    }
}

fun main() {
    val solution = Day10()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
