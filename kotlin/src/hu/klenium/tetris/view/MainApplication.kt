package hu.klenium.tetris.view

import hu.klenium.tetris.util.Dimension
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage

typealias WindowReadyHandler = (MainApplication) -> Unit
private lateinit var readyTask: WindowReadyHandler

class MainApplication : Application() {
    private lateinit var scene: Scene
    override fun start(primaryStage: Stage) {
        val root = HBox()
        scene = Scene(root)
        primaryStage.title = "Tetris"
        primaryStage.isResizable = false
        primaryStage.scene = scene
        primaryStage.show()
        root.requestFocus()
        readyTask(this)
    }
    fun createFrame(gridSize: Dimension, squareSize: Int) : GraphicGameFrame {
        return GraphicGameFrame(scene, gridSize, squareSize)
    }
    companion object {
        fun init(task: WindowReadyHandler) {
            readyTask = task
            Application.launch(MainApplication::class.java)
        }
    }
}