package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.MarkdownElementTypes

@OptIn(ExperimentalTextApi::class)
internal fun AnnotatedString.Builder.parseInlineLink(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val linkDestination = node.child(MarkdownElementTypes.LINK_DESTINATION) ?: return
    val linkTextNode = node.child(MarkdownElementTypes.LINK_TEXT) ?: return
    val linkDestinationString = linkDestination.text
    withAnnotation(UrlAnnotation(linkDestinationString)) {
        withStyle(
            SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
            ),
        ) {
            linkTextNode.children.forEach { child ->
                parseMarkdown(child, configs, inlineTextContent)
            }
        }
    }
}
