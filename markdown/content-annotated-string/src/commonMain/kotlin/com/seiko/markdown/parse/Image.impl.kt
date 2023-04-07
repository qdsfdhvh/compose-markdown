package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.MarkdownTextContentBuilder
import com.seiko.markdown.config.MarkdownWidget

internal fun MarkdownTextContentBuilder.appendImageInternal(imageUrl: String) {
    inlineTextContent.getOrPut(IMAGE_KEY) {
        InlineTextContent(
            placeholder = Placeholder(
                width = configs.imageWidthSp,
                height = configs.imageHeightSp,
                PlaceholderVerticalAlign.TextCenter,
            ),
        ) { imageUrl ->
            configs.Content(
                MarkdownWidget.Image(url = imageUrl),
            )
        }
    }
    appendInlineContent(IMAGE_KEY, imageUrl)
}

private const val IMAGE_KEY = "[Image]"
