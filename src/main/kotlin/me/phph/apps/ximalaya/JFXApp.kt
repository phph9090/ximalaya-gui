package me.phph.apps.ximalaya

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage

class JFXApp : Application() {
    override fun start(p0: Stage?) {
        with(p0!!) {
            title = "ximalaya"
            width = 800.0
            height = 600.0
        }

        val label = Label("Hello JavaFX")
        val scene = Scene(label, 400.0, 200.0)

        p0.scene = scene
        p0.show()
    }
}

fun main() {
    Application.launch(JFXApp::class.java)
}