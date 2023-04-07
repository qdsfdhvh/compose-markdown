package com.seiko.markdown

import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

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

fun MarkdownNode(
    content: String,
    flavour: MarkdownFlavourDescriptor = GFMFlavourDescriptor(),
): MarkdownNode {
    val rootNode = MarkdownParser(flavour).buildMarkdownTreeFromString(content)
    return MarkdownNode(rootNode, null, content)
}
