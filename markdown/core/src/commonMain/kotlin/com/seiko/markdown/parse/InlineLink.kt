package com.seiko.markdown.parse

import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.MarkdownElementTypes

internal fun MarkdownContentBuilder.parseInlineLink(node: MarkdownNode) {
    val label = node.child(MarkdownElementTypes.LINK_TEXT)
    val destination = node.child(MarkdownElementTypes.LINK_DESTINATION)?.text
    if (label != null && destination != null) {
        withClick(destination) {
            withStyle(configs.typography.url) {
                visitChildren(label)
            }
        }
    } else {
        append(node.text)
    }
}
