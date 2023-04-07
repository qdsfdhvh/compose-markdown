package com.seiko.markdown

import androidx.compose.ui.text.TextStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.parse.appendImageInternal
import com.seiko.markdown.parse.appendInternal
import com.seiko.markdown.parse.visitChildrenInternal

interface MarkdownContentBuilder {

    val configs: MarkdownConfigs

    fun append(node: MarkdownNode) {
        appendInternal(node)
    }

    fun visitChildren(node: MarkdownNode) {
        visitChildrenInternal(node)
    }

    fun pushStyle(style: TextStyle)
    fun pop()
    fun <R : Any> withStyle(style: TextStyle, builder: MarkdownContentBuilder.() -> R)

    fun withClick(url: String, builder: MarkdownContentBuilder.() -> Unit)

    fun append(text: String)
    fun append(char: Char)
    fun appendBlockQuote(node: MarkdownNode)
    fun appendCheckbox(node: MarkdownNode)
    fun appendCodeBlock(node: MarkdownNode)
    fun appendDivider(node: MarkdownNode)
    fun appendImage(node: MarkdownNode) {
        appendImageInternal(node)
    }
    fun appendImage(imageUrl: String)
    fun appendTable(node: MarkdownNode)
}
