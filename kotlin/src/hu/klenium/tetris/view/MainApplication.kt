package hu.klenium.tetris.view

import hu.klenium.tetris.util.Dimension
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(primaryStage: Stage) {
        val root = HBox()
        root.style = "-fx-background-color: rgb(23, 23, 23);"
        scene = Scene(root)
        primaryStage.title = "Tetris"
        primaryStage.isResizable = false
        primaryStage.scene = scene
        primaryStage.show()
        root.requestFocus()
        readyTask.invoke()
    }

    companion object {
        private lateinit var readyTask: () -> Unit
        private lateinit var scene: Scene

        fun init(task: () -> Unit) {
            readyTask = task
            Application.launch(MainApplication::class.java)
        }
        fun createFrame(gridSize: Dimension, squareSize: Int) : GraphicGameFrame {
            return GraphicGameFrame(scene, gridSize, squareSize)
        }
    }
}