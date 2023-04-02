package com.seiko.markdown.config

import androidx.compose.runtime.Composable

object Material3MarkdownWidgetPlugin : MarkdownWidgetPlugin {

    @Composable
    override fun Content(enum: MarkdownWidget): Boolean {
        return when (enum) {
            is MarkdownWidget.Text -> {
                androidx.compose.material3.Text(enum.text)
                true
            }
            is MarkdownWidget.Checkbox -> {
                androidx.compose.material3.Checkbox(enum.checked, {})
                true
            }
            MarkdownWidget.Divider -> {
                androidx.compose.material3.Divider()
                true
            }
            else -> false
        }
    }
}
