package com.seiko.markdown.demo

import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    Window("ComposeDemo") {
        App()
    }
    NSApp?.run()
}
