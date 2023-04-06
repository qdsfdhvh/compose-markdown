package com.seiko.markdown.parse

import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.model.MarkdownNode

internal fun MarkdownContentBuilder.appendListNumber(node: MarkdownNode) {
    withStyle(configs.typography.listNumber) {
        append("    ")
        val text = node.text.trim()
        if (text.endsWith(ORDER_SUFFIX)) {
            append(text)
        } else {
            append(text.dropLast(1))
            append(ORDER_SUFFIX)
        }
        append(' ')
    }
}

private const val ORDER_SUFFIX = '.'
