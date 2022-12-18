package com.github.akowal.aoc

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day16 {
    private val pattern = """Valve (\S+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""".toRegex()

    private val flows = mutableMapOf<String, Int>()
    private val dists: Map<String, Map<String, Int>>
    private val valves = mutableMapOf<String, List<String>>()

    init {
        inputFile("day16").readLines().map { line ->
            val parts = pattern.matchEntire(line)!!.groupValues.drop(1)
            val valve = parts[0]
            flows[valve] = parts[1].toInt()
            valves[valve] = parts[2].split(", ")
        }

        dists = calcDistancesBetweenWorkingValves()
    }

    fun solvePart1(): Int {
        return findMaxPressureRelease("AA", 30, emptySet(), 0)
    }

    @OptIn(ExperimentalTime::class)
    fun solvePart2(): Int {
        return measureTimedValue { findMaxPressureReleaseWithElephant("AA", 26, emptySet(), 0, true) }.also { println(it.duration) }.value
    }

    private fun findMaxPressureRelease(valve: String, t: Int, open: Set<String>, score: Int): Int {
        return dists[valve]!!.maxOf { (v, dist) ->
            val tleft = t - dist - 1
            if (v in open || tleft < 1) {
                score
            } else {
                findMaxPressureRelease(v, tleft, open + v, score + flows[v]!! * tleft)
            }
        }
    }

    private fun findMaxPressureReleaseWithElephant(valve: String, t: Int, open: Set<String>, score: Int, first: Boolean = false): Int {
        // brute force =(
        val scores = dists[valve]!!.map { (v, dist) ->
            val tleft = t - dist - 1
            if (v in open || tleft < 1) {
                score
            } else {
                findMaxPressureReleaseWithElephant(v, tleft, open + v, score + flows[v]!! * tleft, first)
            }
        } + if (first) findMaxPressureReleaseWithElephant("AA", 26, open, score) else 0
        return scores.max()
    }

    private fun calcDistancesBetweenWorkingValves(): Map<String, Map<String, Int>> {
        val dists = mutableMapOf<Pair<String, String>, Int>()
        valves.forEach { (v, connected) ->
            connected.forEach { c ->
                dists[v to c] = 1
            }
        }
        for (k in valves.keys) {
            for (i in valves.keys) {
                val ik = dists[i to k] ?: 2022
                for (j in valves.keys) {
                    val ij = dists[i to j] ?: 2022
                    val kj = dists[k to j] ?: 2022
                    if (ij > ik + kj) {
                        dists[i to j] = ik + kj
                    }
                }
            }
        }
        dists.entries.removeIf { (tunnel, _) -> flows[tunnel.second] == 0 }
        return dists.entries.groupBy { it.key.first }.mapValues { it.value.associate { e -> e.key.second to e.value } }
    }
}

fun main() {
    val solution = Day16()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
