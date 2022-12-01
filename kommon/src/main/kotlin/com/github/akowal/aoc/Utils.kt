package com.github.akowal.aoc

fun String.toIntArray(delimiter: Char = ',') = split(delimiter).map { it.toInt() }.toIntArray()
