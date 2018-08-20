package hu.klenium.tetris.util

class Event<T> {
    private var listenerts = listOfNotNull<(T) -> Unit>()
    operator fun plusAssign(handler: (T) -> Unit) {
        listenerts += handler
    }
    operator fun invoke(data: T) {
        for (listener in listenerts)
            listener.invoke(data)
    }
}