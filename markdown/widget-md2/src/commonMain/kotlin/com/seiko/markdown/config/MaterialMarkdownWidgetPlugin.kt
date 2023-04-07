package com.seiko.markdown.config

import androidx.compose.runtime.Composable

object MaterialMarkdownWidgetPlugin : MarkdownWidgetPlugin {

    override fun isSupport(enum: MarkdownWidget): Boolean {
        return when (enum) {
            is MarkdownWidget.Text -> true
            is MarkdownWidget.Checkbox -> true
            MarkdownWidget.Divider -> true
            else -> false
        }
    }

    @Composable
    override fun Content(enum: MarkdownWidget) {
        when (enum) {
            is MarkdownWidget.Text -> {
                androidx.compose.material.Text(enum.text)
            }
            is MarkdownWidget.Checkbox -> {
                androidx.compose.material.Checkbox(enum.checked, {})
            }
            MarkdownWidget.Divider -> {
                androidx.compose.material.Divider()
            }
            else -> Unit
        }
    }
}
