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

    data class Text(
        val text: String,
    ) : MarkdownWidget
}

interface MarkdownWidgetPlugin {

    fun isSupport(enum: MarkdownWidget): Boolean

    @Composable
    fun Content(enum: MarkdownWidget)
}

internal class MarkdownWidgetPluginProxy : MarkdownWidgetPlugin {

    private val plugins = mutableListOf<MarkdownWidgetPlugin>()

    fun add(plugin: MarkdownWidgetPlugin) {
        plugins.add(plugin)
    }

    override fun isSupport(enum: MarkdownWidget): Boolean {
        return plugins.any { it.isSupport(enum) }
    }

    @Composable
    override fun Content(enum: MarkdownWidget) {
        plugins.find { it.isSupport(enum) }?.Content(enum)
    }
}
