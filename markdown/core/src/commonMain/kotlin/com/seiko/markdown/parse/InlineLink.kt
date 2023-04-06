package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.MarkdownElementTypes

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownContentBuilder.parseInlineLink(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val label = node.child(MarkdownElementTypes.LINK_TEXT)
    val destination = node.child(MarkdownElementTypes.LINK_DESTINATION)?.text
    if (label != null && destination != null) {
        withAnnotation(UrlAnnotation(destination)) {
            withStyle(configs.typography.url.toSpanStyle()) {
                label.children.forEach { child ->
                    parseMarkdown(child, configs, inlineTextContent)
                }
            }
        }
    } else {
        append(node.text)
    }
}
