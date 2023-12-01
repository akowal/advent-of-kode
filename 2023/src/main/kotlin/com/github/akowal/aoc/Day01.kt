package com.github.akowal.aoc

import java.io.File

class Day01(input: File) : Problem<Int> {
    private val lines = input.readLines()

    override fun solve1(): Int {
        return lines.sumOf { s ->
            s.first(Char::isDigit).digitToInt() * 10 + s.last(Char::isDigit).digitToInt()
        }
    }

    override fun solve2(): Int {
        val words = "three|seven|eight|four|five|nine|six|one|two"
        val r = "\\d|$words".toRegex()
        val rr = "\\d|${words.reversed()}".toRegex()
        return lines.sumOf { s ->
            val firstDigit = r.find(s)!!.value.asDigit()
            val lastDigit = rr.find(s.reversed())!!.value.reversed().asDigit()
            firstDigit * 10 + lastDigit
        }
    }

    private fun String.asDigit() = when (this) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.single().digitToInt()
    }
}

fun main() {
    Launcher("day01", ::Day01).run()
}
