package com.seiko.markdown.config

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.seiko.imageloader.rememberAsyncImagePainter

object ImageMarkdownWidgetPlugin : MarkdownWidgetPlugin {
    @Composable
    override fun Content(enum: MarkdownWidget): Boolean {
        return when (enum) {
            is MarkdownWidget.Image -> {
                Image(
                    painter = rememberAsyncImagePainter(enum.url),
                    contentDescription = null,
                )
                true
            }
            else -> false
        }
    }
}
