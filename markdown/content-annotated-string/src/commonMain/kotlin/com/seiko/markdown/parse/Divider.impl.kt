package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.MarkdownTextContentBuilder
import com.seiko.markdown.config.MarkdownWidget

internal fun MarkdownTextContentBuilder.appendDividerInternal(node: MarkdownNode) {
    inlineTextContent.getOrPut(DIVIDER_KEY) {
        InlineTextContent(
            placeholder = Placeholder(
                width = configs.maxWidthSP,
                height = configs.dividerHeightSp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
            ),
        ) {
            configs.Content(
                MarkdownWidget.Divider,
            )
        }
    }
    appendInlineContent(DIVIDER_KEY, node.text)
}

private const val DIVIDER_KEY = "[Divider]"
