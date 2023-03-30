package com.seiko.markdown.demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
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
