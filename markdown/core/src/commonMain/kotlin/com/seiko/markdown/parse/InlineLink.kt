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
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

@OptIn(ExperimentalTextApi::class)
fun AnnotatedString.Builder.parseInlineLink(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val linkDestination = node.children
        .firstOrNull { it.type == MarkdownElementTypes.LINK_DESTINATION }
        ?.getTextInNode(content)
        ?.toString() ?: return
    val linkTextNode = node.children
        .firstOrNull { it.type == MarkdownElementTypes.LINK_TEXT } ?: return
    withAnnotation(UrlAnnotation(linkDestination)) {
        withStyle(
            SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
            ),
        ) {
            linkTextNode.children.forEach { child ->
                parseMarkdown(child, content, configs, inlineTextContent)
            }
        }
    }
}