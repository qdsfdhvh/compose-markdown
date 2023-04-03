package com.seiko.markdown.config

import androidx.compose.runtime.Composable

sealed interface MarkdownWidget {
    data class Image(
        val url: String,
    ) : MarkdownWidget

    data class Checkbox(
        val checked: Boolean,
    ) : MarkdownWidget

    object Divider : MarkdownWidget
}

interface MarkdownWidgetPlugin {

    @Composable
    fun Content(enum: MarkdownWidget): Boolean
}

internal class MarkdownWidgetPluginProxy : MarkdownWidgetPlugin {

    private val plugins = mutableListOf<MarkdownWidgetPlugin>()

    fun add(plugin: MarkdownWidgetPlugin) {
        plugins.add(plugin)
    }

    @Composable
    override fun Content(enum: MarkdownWidget): Boolean {
        plugins.forEach { plugin ->
            if (plugin.Content(enum)) return true
        }
        return false
    }
}
