package com.seiko.markdown.parse

import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.MarkdownNode

internal fun MarkdownContentBuilder.appendImageInternal(node: MarkdownNode) {
    val imageUrlNode = node.children.getOrNull(node.children.size - 1) ?: return
    if (imageUrlNode.children.size > 2) {
        val imageUrl = imageUrlNode.children
            .getOrNull(imageUrlNode.children.size - 2)
            ?.text ?: return
        appendImage(imageUrl)
    }
}
