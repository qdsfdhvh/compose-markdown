package com.seiko.markdown.config

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.seiko.imageloader.rememberAsyncImagePainter

object ImageMarkdownWidgetPlugin : MarkdownWidgetPlugin {

    override fun isSupport(enum: MarkdownWidget): Boolean {
        return enum is MarkdownWidget.Image
    }

    @Composable
    override fun Content(enum: MarkdownWidget) {
        return when (enum) {
            is MarkdownWidget.Image -> {
                Image(
                    painter = rememberAsyncImagePainter(enum.url),
                    contentDescription = null,
                )
            }
            else -> Unit
        }
    }
}
