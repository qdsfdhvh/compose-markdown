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
import com.seiko.markdown.parse.appendBlockQuoteInternal
import com.seiko.markdown.parse.appendCheckboxInternal
import com.seiko.markdown.parse.appendCodeBlockInternal
import com.seiko.markdown.parse.appendDividerInternal
import com.seiko.markdown.parse.appendImageInternal
import com.seiko.markdown.parse.appendTableInternal

class MarkdownTextContentBuilder internal constructor(
    override val configs: MarkdownConfigs,
) : MarkdownContentBuilder {

    internal val inlineTextContent = mutableMapOf<String, InlineTextContent>()
    private val annotatedStringBuilder = AnnotatedString.Builder()

    override fun append(text: String) {
        annotatedStringBuilder.append(text)
    }

    override fun append(char: Char) {
        annotatedStringBuilder.append(char)
    }

    override fun pushStyle(style: TextStyle) {
        annotatedStringBuilder.pushStyle(style.toSpanStyle())
    }

    override fun pop() {
        annotatedStringBuilder.pop()
    }

    override fun <R : Any> withStyle(style: TextStyle, builder: MarkdownContentBuilder.() -> R) {
        annotatedStringBuilder.withStyle(style.toSpanStyle()) {
            builder()
        }
    }

    @OptIn(ExperimentalTextApi::class)
    override fun withClick(url: String, builder: MarkdownContentBuilder.() -> Unit) {
        annotatedStringBuilder.withAnnotation(UrlAnnotation(url)) {
            builder()
        }
    }

    fun appendInlineContent(id: String, alternateText: String) {
        annotatedStringBuilder.appendInlineContent(id, alternateText)
    }

    override fun appendBlockQuote(node: MarkdownNode) {
        appendBlockQuoteInternal(node)
    }

    override fun appendCheckbox(node: MarkdownNode) {
        appendCheckboxInternal(node)
    }

    override fun appendCodeBlock(node: MarkdownNode) {
        appendCodeBlockInternal(node)
    }

    override fun appendDivider(node: MarkdownNode) {
        appendDividerInternal(node)
    }

    override fun appendImage(imageUrl: String) {
        appendImageInternal(imageUrl)
    }

    override fun appendTable(node: MarkdownNode) {
        appendTableInternal(node)
    }

    internal fun build() = MarkdownTextContent(
        annotatedString = annotatedStringBuilder.toAnnotatedString(),
        inlineTextContent = inlineTextContent.toMap(),
    )
}

fun buildMarkdownTextContent(
    configs: MarkdownConfigs,
    builder: MarkdownContentBuilder.() -> Unit
) = MarkdownTextContentBuilder(configs).apply(builder).build()
