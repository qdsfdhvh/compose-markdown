package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.config.MarkdownConfigs
import io.github.aakira.napier.Napier
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

fun AnnotatedString.Builder.parseMarkdown(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    // Napier.d { "${node.type.name} ${node.getTextInNode(content)}" }
    when (node.type) {
        MarkdownElementTypes.MARKDOWN_FILE,
        MarkdownElementTypes.PARAGRAPH,
        MarkdownElementTypes.UNORDERED_LIST,
        MarkdownElementTypes.ORDERED_LIST,
        MarkdownElementTypes.LIST_ITEM,
        MarkdownTokenTypes.ATX_CONTENT -> {
            node.children.forEach { child ->
                parseMarkdown(child, content, configs, inlineTextContent)
            }
        }
        MarkdownElementTypes.SETEXT_1,
        MarkdownElementTypes.SETEXT_2,
        MarkdownElementTypes.ATX_1,
        MarkdownElementTypes.ATX_2,
        MarkdownElementTypes.ATX_3,
        MarkdownElementTypes.ATX_4,
        MarkdownElementTypes.ATX_5,
        MarkdownElementTypes.ATX_6 -> {
            parseHeader(node, content, configs, inlineTextContent)
        }
        MarkdownElementTypes.STRONG -> {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                node.children.forEach { child ->
                    parseMarkdown(child, content, configs, inlineTextContent)
                }
            }
        }
        MarkdownElementTypes.EMPH -> {
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                node.children.forEach { child ->
                    parseMarkdown(child, content, configs, inlineTextContent)
                }
            }
        }
        MarkdownElementTypes.CODE_SPAN -> {
            withStyle(
                SpanStyle(
                    fontFamily = FontFamily.Monospace,
                    background = Color.LightGray.copy(alpha = 0.5f),
                ),
            ) {
                node.children.forEach { child ->
                    parseMarkdown(child, content, configs, inlineTextContent)
                }
            }
        }
        MarkdownElementTypes.CODE_FENCE,
        MarkdownElementTypes.CODE_BLOCK -> {
            parseCodeBlock(node, content, configs, inlineTextContent)
        }
        MarkdownElementTypes.IMAGE -> {
            parseImage(node, content, configs, inlineTextContent)
        }
        MarkdownElementTypes.INLINE_LINK -> {
            parseInlineLink(node, content, configs, inlineTextContent)
        }
        GFMElementTypes.STRIKETHROUGH -> {
            withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                node.children.forEach { child ->
                    parseMarkdown(child, content, configs, inlineTextContent)
                }
            }
        }
        MarkdownTokenTypes.LIST_BULLET -> {
            parseListBullet(node)
        }
        MarkdownTokenTypes.LIST_NUMBER -> {
            parseListNumber(node, content)
        }
        MarkdownElementTypes.BLOCK_QUOTE -> {
            parseBlockQuote(node, content, configs, inlineTextContent)
        }
        MarkdownTokenTypes.TEXT -> {
            append(node.getTextInNode(content))
        }
        MarkdownTokenTypes.CODE_FENCE_START -> {
            pushStyle(configs.typography.code.toParagraphStyle())
            pushStyle(configs.typography.code.toSpanStyle())
        }
        MarkdownTokenTypes.CODE_FENCE_CONTENT -> {
            append(node.getTextInNode(content))
        }
        MarkdownTokenTypes.CODE_FENCE_END -> {
            pop()
            pop()
        }
        MarkdownTokenTypes.HORIZONTAL_RULE -> {
            parseDivider(node, content, configs, inlineTextContent)
        }
        MarkdownTokenTypes.EOL -> {
            when (val parentType = node.parent?.type) {
                MarkdownElementTypes.MARKDOWN_FILE -> append('\n')
                MarkdownElementTypes.PARAGRAPH -> append('\n')
                MarkdownElementTypes.CODE_FENCE -> append('\n')
                MarkdownElementTypes.CODE_BLOCK -> Unit
                MarkdownElementTypes.ORDERED_LIST -> append('\n')
                MarkdownElementTypes.UNORDERED_LIST -> append('\n')
                MarkdownElementTypes.BLOCK_QUOTE -> append('\n')
                GFMElementTypes.TABLE -> append('\n')
                else -> {
                    Napier.d { "??? EOL ${parentType?.name}" }
                }
            }
        }
        GFMTokenTypes.TABLE_SEPARATOR -> {
            append(node.getTextInNode(content))
        }
        GFMElementTypes.HEADER,
        GFMElementTypes.ROW -> {
            append(node.getTextInNode(content))
        }
        MarkdownTokenTypes.ATX_HEADER -> Unit // #
        MarkdownTokenTypes.SINGLE_QUOTE -> append('\'')
        MarkdownTokenTypes.DOUBLE_QUOTE -> append('\"')
        MarkdownTokenTypes.LPAREN -> append('(')
        MarkdownTokenTypes.RPAREN -> append(')')
        MarkdownTokenTypes.LBRACKET -> Unit // [
        MarkdownTokenTypes.RBRACKET -> Unit // ]
        MarkdownTokenTypes.LT -> Unit // <
        MarkdownTokenTypes.GT -> Unit // >
        MarkdownTokenTypes.COLON -> Unit // :
        MarkdownTokenTypes.BLOCK_QUOTE -> Unit // >
        MarkdownTokenTypes.EXCLAMATION_MARK -> append('!') // !
        MarkdownTokenTypes.BACKTICK -> Unit // `
        MarkdownTokenTypes.HARD_LINE_BREAK -> append('\n')
        MarkdownTokenTypes.WHITE_SPACE -> append(' ')
        MarkdownTokenTypes.EMPH -> Unit // *
        GFMTokenTypes.TILDE -> Unit // ~
        GFMTokenTypes.CHECK_BOX -> {
            parseCheckbox(node, content, configs, inlineTextContent)
        }
        GFMElementTypes.TABLE -> {
            parseTable(node, content, configs, inlineTextContent)
        }
        else -> {
            Napier.d { "??? ${node.type.name} ${node.getTextInNode(content)}" }
            append(node.getTextInNode(content))
        }
    }
}
