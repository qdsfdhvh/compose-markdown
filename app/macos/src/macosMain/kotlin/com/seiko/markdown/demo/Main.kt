package com.seiko.markdown.demo

import androidx.compose.ui.window.Window
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    Napier.base(DebugAntilog())
    NSApplication.sharedApplication()
    Window("ComposeDemo") {
        App()
    }
    NSApp?.run()
}
