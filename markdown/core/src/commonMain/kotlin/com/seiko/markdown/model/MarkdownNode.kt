package com.seiko.markdown.model

import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode

class MarkdownNode internal constructor(
    val node: ASTNode,
    val parent: MarkdownNode?,
    val content: String,
) {
    val type: IElementType get() = node.type
    val endOffset: Int get() = node.endOffset
    val startOffset: Int get() = node.startOffset
    val text: String get() = content.substring(startOffset, endOffset)
    val children: List<MarkdownNode> = node.children.map { MarkdownNode(it, this, content) }
    fun child(type: IElementType): MarkdownNode? = children.firstOrNull { it.type == type }
}
