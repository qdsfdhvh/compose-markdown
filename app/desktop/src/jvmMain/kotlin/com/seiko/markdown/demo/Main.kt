package com.seiko.markdown.demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun main() {
    Napier.base(DebugAntilog())
    application {
        Window(
            title = "ComposeDemo",
            onCloseRequest = {
                exitApplication()
            },
        ) {
            App()
        }
    }
}
