package com.seiko.markdown

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType
import org.intellij.markdown.ast.getTextInNode

/**
 * Find a child node recursive
 */
internal fun ASTNode.findChildOfTypeRecursive(type: IElementType): ASTNode? {
    children.forEach {
        if (it.type == type) {
            return it
        } else {
            val found = it.findChildOfTypeRecursive(type)
            if (found != null) {
                return found
            }
        }
    }
    return null
}

/**
 * Helper function to drop the first and last element in the children list.
 * E.g. we don't want to render the brackets of a link
 */
internal fun List<ASTNode>.innerList(): List<ASTNode> = this.subList(1, this.size - 1)

/**
 * Helper function to filter out items within a list of nodes, not of interest for the bullet list.
 */
internal fun List<ASTNode>.filterNonListTypes(): List<ASTNode> = this.filter { n ->
    n.type != MarkdownElementTypes.ORDERED_LIST && n.type != MarkdownElementTypes.UNORDERED_LIST && n.type != MarkdownTokenTypes.EOL
}

internal fun ASTNode.text(content: String): String {
    return getTextInNode(content).toString()
}

internal fun ASTNode.atxContent(content: String): String? {
    return findChildOfType(MarkdownTokenTypes.ATX_CONTENT)?.getTextInNode(content)?.toString()
}

internal fun ASTNode.linkDestination(content: String): String? {
    return findChildOfType(MarkdownElementTypes.LINK_DESTINATION)?.text(content)
}

internal fun ASTNode.linkLabel(content: String): String? {
    return findChildOfType(MarkdownElementTypes.LINK_LABEL)?.text(content)
}
