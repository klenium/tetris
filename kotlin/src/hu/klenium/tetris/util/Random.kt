package hu.klenium.tetris.util

import java.util.*

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive + 1 - start) + start