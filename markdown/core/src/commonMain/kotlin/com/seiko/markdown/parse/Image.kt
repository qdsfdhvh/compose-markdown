package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode

internal fun AnnotatedString.Builder.parseImage(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val imageUrlNode = node.children
        .getOrNull(node.children.size - 1) ?: return
    if (imageUrlNode.children.size > 2) {
        val imageUrl = imageUrlNode.children
            .getOrNull(imageUrlNode.children.size - 2)
            ?.text ?: return
        inlineTextContent[imageUrl] = InlineTextContent(
            placeholder = Placeholder(
                width = configs.imageWidthSp,
                height = configs.imageHeightSp,
                PlaceholderVerticalAlign.TextCenter,
            ),
        ) {
            configs.Content(
                MarkdownWidget.Image(url = imageUrl),
            )
        }
        appendInlineContent(imageUrl, imageUrl)
    }
}
