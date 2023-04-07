package com.seiko.markdown.parse

import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.config.MarkdownConfigs
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.flavours.gfm.GFMElementTypes

internal fun MarkdownContentBuilder.visitChildrenInternal(node: MarkdownNode) {
    withStyle(node, configs) {
        node.children.forEach { child ->
            append(child)
        }
    }
}

private fun <R : Any> MarkdownContentBuilder.withStyle(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    block: MarkdownContentBuilder.() -> R,
) {
    val style = when (node.type) {
        MarkdownElementTypes.ATX_1 -> configs.typography.h1
        MarkdownElementTypes.ATX_2 -> configs.typography.h2
        MarkdownElementTypes.ATX_3 -> configs.typography.h3
        MarkdownElementTypes.ATX_4 -> configs.typography.h4
        MarkdownElementTypes.ATX_5 -> configs.typography.h5
        MarkdownElementTypes.ATX_6 -> configs.typography.h6
        MarkdownElementTypes.STRONG -> configs.typography.strong
        MarkdownElementTypes.EMPH -> configs.typography.em
        GFMElementTypes.STRIKETHROUGH -> configs.typography.del
        MarkdownElementTypes.CODE_SPAN -> configs.typography.codeSpan
        else -> null
    }
    if (style != null) {
        withStyle(style, block)
    } else {
        block()
    }
}
