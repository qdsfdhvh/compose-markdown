package com.seiko.markdown

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.parser.MarkdownParser

@Composable
fun rememberParseMarkdown(
    content: String,
    flavour: MarkdownFlavourDescriptor = remember { GFMFlavourDescriptor() },
    configs: MarkdownConfigs = MarkdownConfigs.Default,
): AnnotatedString = remember(content, flavour, configs) {
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(content)
    buildAnnotatedString {
        parsedTree.children.forEach { node ->
            if (!handleElement(node, content, configs)) {
                node.children.forEach { child ->
                    handleElement(child, content, configs)
                }
            }
        }
    }
}

private fun AnnotatedString.Builder.handleElement(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
): Boolean {
    when (node.type) {
        MarkdownTokenTypes.TEXT -> append(node, content)
        MarkdownTokenTypes.EOL -> Unit
        MarkdownElementTypes.CODE_FENCE -> {}
        MarkdownElementTypes.CODE_BLOCK -> {}
        MarkdownElementTypes.ATX_1 -> appendHeader(node, content, configs.typography.h1)
        MarkdownElementTypes.ATX_2 -> appendHeader(node, content, configs.typography.h2)
        MarkdownElementTypes.ATX_3 -> appendHeader(node, content, configs.typography.h3)
        MarkdownElementTypes.ATX_4 -> appendHeader(node, content, configs.typography.h4)
        MarkdownElementTypes.ATX_5 -> appendHeader(node, content, configs.typography.h5)
        MarkdownElementTypes.ATX_6 -> appendHeader(node, content, configs.typography.h6)
        MarkdownElementTypes.BLOCK_QUOTE -> {}
        MarkdownElementTypes.PARAGRAPH -> {
            withStyle(configs.typography.paragraph) {
                buildMarkdownAnnotatedString(content, node.children, configs)
                append('\n')
            }
        }
        MarkdownElementTypes.ORDERED_LIST -> {
            append(node, content)
            append('\n')
        }
        MarkdownElementTypes.UNORDERED_LIST -> {}
        MarkdownElementTypes.IMAGE -> {}
        MarkdownElementTypes.LINK_DEFINITION -> {
            val url = node.linkDestination(content) ?: node.linkLabel(content)
            if (!url.isNullOrEmpty()) {
                appendUrl(url, configs.typography.url)
            }
        }
        else -> return false
    }
    return true
}

private fun AnnotatedString.Builder.buildMarkdownAnnotatedString(
    content: String,
    children: List<ASTNode>,
    configs: MarkdownConfigs,
) {
    children.forEach { child ->
        when (child.type) {
            MarkdownElementTypes.PARAGRAPH -> {
                buildMarkdownAnnotatedString(content, child.children, configs)
            }
            MarkdownElementTypes.IMAGE -> {
                child.findChildOfTypeRecursive(MarkdownElementTypes.LINK_DESTINATION)?.let {
                    appendInlineContent(TAG_IMAGE, it.getTextInNode(content).toString())
                }
            }
            MarkdownElementTypes.EMPH -> {
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    buildMarkdownAnnotatedString(content, child.children, configs)
                }
            }
            MarkdownElementTypes.STRONG -> {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    buildMarkdownAnnotatedString(content, child.children, configs)
                }
            }
            MarkdownElementTypes.CODE_SPAN -> {
                withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                    append(' ')
                    buildMarkdownAnnotatedString(content, child.children.innerList(), configs)
                    append(' ')
                }
            }
            MarkdownElementTypes.AUTOLINK -> {
                appendUrl(child.text(content), configs.typography.url)
            }
            MarkdownElementTypes.INLINE_LINK,
            MarkdownElementTypes.SHORT_REFERENCE_LINK,
            MarkdownElementTypes.FULL_REFERENCE_LINK -> {
                appendMarkdownLink(content, child, configs)
            }
            MarkdownTokenTypes.TEXT -> append(child.getTextInNode(content).toString())
            GFMTokenTypes.GFM_AUTOLINK -> {
                if (child.parent == MarkdownElementTypes.LINK_TEXT) {
                    append(child.getTextInNode(content).toString())
                } else {
                    appendUrl(child.text(content), configs.typography.url)
                }
            }
            MarkdownTokenTypes.SINGLE_QUOTE -> append('\'')
            MarkdownTokenTypes.DOUBLE_QUOTE -> append('\"')
            MarkdownTokenTypes.LPAREN -> append('(')
            MarkdownTokenTypes.RPAREN -> append(')')
            MarkdownTokenTypes.LBRACKET -> append('[')
            MarkdownTokenTypes.RBRACKET -> append(']')
            MarkdownTokenTypes.LT -> append('<')
            MarkdownTokenTypes.GT -> append('>')
            MarkdownTokenTypes.COLON -> append(':')
            MarkdownTokenTypes.EXCLAMATION_MARK -> append('!')
            MarkdownTokenTypes.BACKTICK -> append('`')
            MarkdownTokenTypes.HARD_LINE_BREAK -> append("\n\n")
            MarkdownTokenTypes.EOL -> append('\n')
            MarkdownTokenTypes.WHITE_SPACE -> append(' ')
        }
    }
}

private fun AnnotatedString.Builder.appendHeader(
    node: ASTNode,
    content: String,
    textStyle: TextStyle,
) {
    val header = node.atxContent(content)
    if (!header.isNullOrEmpty()) {
        withStyle(textStyle) {
            append(header.trim())
        }
    }
}

private fun AnnotatedString.Builder.appendMarkdownLink(
    content: String,
    node: ASTNode,
    configs: MarkdownConfigs,
) {
    val linkText = node.findChildOfType(MarkdownElementTypes.LINK_TEXT)?.children?.innerList()
    if (linkText == null) {
        append(node.getTextInNode(content).toString())
        return
    }
    val url = node.linkDestination(content) ?: node.linkLabel(content)
    if (!url.isNullOrEmpty()) {
        withUrlStyle(url, configs.typography.url) {
            buildMarkdownAnnotatedString(content, linkText, configs)
        }
    }
}

private const val TAG_IMAGE = "image"
