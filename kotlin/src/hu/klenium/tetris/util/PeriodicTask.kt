package hu.klenium.tetris.util

import java.util.*
import kotlin.concurrent.timer

class PeriodicTask(private val task: () -> Unit, delay: Int) {
    private val time: Long = delay.toLong()
    private var clock: Timer? = null
    fun start() {
        clock = timer(
            daemon = true,
            initialDelay = time,
            period = time,
            action = { task.invoke() }
        )
    }
    fun stop() {
        clock?.cancel()
    }
    fun reset() {
        stop()
        start()
    }
}