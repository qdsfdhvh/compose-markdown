package com.seiko.markdown.config

import androidx.compose.runtime.Composable

object MaterialMarkdownWidgetPlugin : MarkdownWidgetPlugin {

    @Composable
    override fun Content(enum: MarkdownWidget): Boolean {
        return when (enum) {
            is MarkdownWidget.Checkbox -> {
                androidx.compose.material.Checkbox(enum.checked, {})
                true
            }
            MarkdownWidget.Divider -> {
                androidx.compose.material.Divider()
                true
            }
            else -> false
        }
    }
}
