package com.seiko.markdown

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import com.seiko.markdown.parse.appendInternal
import com.seiko.markdown.parse.visitChildrenInternal

class MarkdownContentBuilder internal constructor(
    internal val configs: MarkdownConfigs,
) {
    internal val inlineTextContent = mutableMapOf<String, InlineTextContent>()
    private val annotatedStringBuilder = AnnotatedString.Builder()

    fun visitChildren(node: MarkdownNode) {
        visitChildrenInternal(node)
    }

    fun append(node: MarkdownNode) {
        appendInternal(node)
    }

    fun append(text: String) {
        annotatedStringBuilder.append(text)
    }

    fun append(char: Char) {
        annotatedStringBuilder.append(char)
    }

    fun pushStyle(style: TextStyle) = annotatedStringBuilder.pushStyle(style.toSpanStyle())

    fun pop() = annotatedStringBuilder.pop()

    fun <R : Any> withStyle(style: TextStyle, builder: MarkdownContentBuilder.() -> R) {
        annotatedStringBuilder.withStyle(style.toSpanStyle()) {
            builder()
        }
    }

    @OptIn(ExperimentalTextApi::class)
    fun withClick(url: String, builder: MarkdownContentBuilder.() -> Unit) {
        annotatedStringBuilder.withAnnotation(UrlAnnotation(url)) {
            builder()
        }
    }

    // TODO remove it
    fun appendInlineContent(id: String, alternateText: String) {
        annotatedStringBuilder.appendInlineContent(id, alternateText)
    }

    fun build() = MarkdownTextContent(
        annotatedString = annotatedStringBuilder.toAnnotatedString(),
        inlineTextContent = inlineTextContent.toMap(),
    )
}

fun buildMarkdownContent(
    configs: MarkdownConfigs,
    builder: MarkdownContentBuilder.() -> Unit
) = MarkdownContentBuilder(configs).apply(builder).build()
