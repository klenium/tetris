package hu.klenium.tetris.util

import java.util.*
import kotlin.concurrent.timer

class PeriodicTask(private val task: () -> Unit, delay: Int) {
    private val time = delay.toLong()
    private var clock: Timer? = null
    fun start() {
        clock = clock ?: timer(
            daemon = true,
            initialDelay = time,
            period = time,
            action = { task.invoke() }
        )
    }
    fun stop() {
        clock?.cancel()
        clock = null
    }
    fun reset() {
        stop()
        start()
    }
}