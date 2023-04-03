package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode

internal fun AnnotatedString.Builder.parseDivider(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
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
