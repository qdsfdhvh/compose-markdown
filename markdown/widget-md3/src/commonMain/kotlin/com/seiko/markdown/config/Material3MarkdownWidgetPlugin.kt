package com.seiko.markdown.config

import androidx.compose.runtime.Composable

object Material3MarkdownWidgetPlugin : MarkdownWidgetPlugin {

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
                androidx.compose.material3.Text(enum.text)
            }
            is MarkdownWidget.Checkbox -> {
                androidx.compose.material3.Checkbox(enum.checked, {})
            }
            MarkdownWidget.Divider -> {
                androidx.compose.material3.Divider()
            }
            else -> Unit
        }
    }
}
