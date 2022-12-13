package com.github.akowal.aoc

class Day13 {
    private val messages = inputFile("day13").readLines()
        .filter { it.isNotEmpty() }
        .map { decode(it) }

    fun solvePart1(): Int {
        return messages
            .windowed(2, 2)
            .map { it[0] to it[1] }
            .mapIndexed { i, (left, right) ->
                if (compare(left, right) < 0) i + 1 else 0
            }
            .sum()
    }

    fun solvePart2(): Int {
        val div1 = listOf(listOf(2))
        val div2 = listOf(listOf(6))
        return messages.toMutableList()
            .apply {
                add(div1)
                add(div2)
            }
            .sortedWith(::compare)
            .let {
                it.indexOf(div1).inc() * it.indexOf(div2).inc()
            }
    }

    private fun compare(left: Any?, right: Any?): Int {
        return if (left is Int && right is Int) {
            left.compareTo(right)
        } else {
            val ll = if (left is List<*>) left else listOf(left)
            val rl = if (right is List<*>) right else listOf(right)
            ll.zip(rl) { l, r -> compare(l, r) }.firstOrNull { it != 0 } ?: ll.size.compareTo(rl.size)
        }
    }


    private fun decode(s: String): List<Any> {
        var last = emptyList<Any>()
        val q = ArrayDeque<MutableList<Any>>()
        var i = 0
        while (i < s.length) {
            when (s[i]) {
                '[' -> q.add(mutableListOf())
                ']' -> last = q.removeLast().also { q.lastOrNull()?.add(it) }
                ',' -> {}
                else -> {
                    val j = s.indexOfAny("],".toCharArray(), i)
                    q.last().add(s.substring(i, j).toInt())
                    i += j - i - 1
                }
            }
            i++
        }
        return last
    }
}

fun main() {
    val solution = Day13()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
