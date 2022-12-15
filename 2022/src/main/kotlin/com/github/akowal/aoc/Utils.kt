package com.github.akowal.aoc

import java.io.File
import java.util.Scanner

fun inputScanner(name: String) = Scanner(inputFile(name))

fun inputFile(name: String) = File("2022/src/main/resources", "$name.txt")

fun IntRange.overlaps(other: IntRange) = first in other || last in other || other.first in this || other.last in this
