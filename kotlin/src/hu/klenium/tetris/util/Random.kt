package hu.klenium.tetris.util

import java.util.Random

fun ClosedRange<Int>.random() = instance.nextInt(endInclusive + 1 - start) + start

private val instance = Random()