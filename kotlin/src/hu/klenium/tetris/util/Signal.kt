package hu.klenium.tetris.util

class Signal {
    private var listenerts = listOfNotNull<() -> Unit>()
    operator fun plusAssign(handler: () -> Unit) {
        listenerts += handler
    }
    operator fun invoke() {
        for (listener in listenerts)
            listener.invoke()
    }
}