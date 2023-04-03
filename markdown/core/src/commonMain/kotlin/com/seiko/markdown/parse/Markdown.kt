package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import io.github.aakira.napier.Napier
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

internal fun AnnotatedString.Builder.parseMarkdown(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    fun wrapChildren(textStyle: TextStyle, newline: Boolean = false) {
        withStyle(textStyle.toSpanStyle()) {
            node.children.forEach { child ->
                parseMarkdown(child, configs, inlineTextContent)
            }
            if (newline) {
                append('\n')
            }
        }
    }

    when (node.type) {
        MarkdownElementTypes.MARKDOWN_FILE,
        MarkdownElementTypes.PARAGRAPH,
        MarkdownElementTypes.UNORDERED_LIST,
        MarkdownElementTypes.ORDERED_LIST,
        MarkdownElementTypes.LIST_ITEM,
        MarkdownTokenTypes.ATX_CONTENT -> {
            node.children.forEach { child ->
                parseMarkdown(child, configs, inlineTextContent)
            }
        }
        MarkdownElementTypes.ATX_1 -> wrapChildren(configs.typography.h1)
        MarkdownElementTypes.ATX_2 -> wrapChildren(configs.typography.h2)
        MarkdownElementTypes.ATX_3 -> wrapChildren(configs.typography.h3)
        MarkdownElementTypes.ATX_4 -> wrapChildren(configs.typography.h4)
        MarkdownElementTypes.ATX_5 -> wrapChildren(configs.typography.h5)
        MarkdownElementTypes.ATX_6 -> wrapChildren(configs.typography.h6)
        MarkdownElementTypes.STRONG -> wrapChildren(configs.typography.strong)
        MarkdownElementTypes.EMPH -> wrapChildren(configs.typography.em)
        GFMElementTypes.STRIKETHROUGH -> wrapChildren(configs.typography.del)
        MarkdownElementTypes.CODE_SPAN -> wrapChildren(configs.typography.codeSpan)
        MarkdownElementTypes.CODE_FENCE,
        MarkdownElementTypes.CODE_BLOCK -> {
            parseCodeBlock(node, configs, inlineTextContent)
        }
        MarkdownElementTypes.IMAGE -> {
            parseImage(node, configs, inlineTextContent)
        }
        MarkdownElementTypes.INLINE_LINK -> {
            parseInlineLink(node, configs, inlineTextContent)
        }
        MarkdownTokenTypes.LIST_BULLET -> {
            parseListBullet(node)
        }
        MarkdownTokenTypes.LIST_NUMBER -> {
            parseListNumber(node)
        }
        MarkdownElementTypes.BLOCK_QUOTE -> {
            parseBlockQuote(node, configs, inlineTextContent)
        }
        MarkdownTokenTypes.TEXT -> {
            append(node.text)
        }
        MarkdownTokenTypes.CODE_FENCE_START -> {
            pushStyle(configs.typography.code.toSpanStyle())
        }
        MarkdownTokenTypes.CODE_FENCE_CONTENT -> {
            append(node.text)
        }
        MarkdownTokenTypes.CODE_FENCE_END -> {
            pop()
        }
        MarkdownTokenTypes.HORIZONTAL_RULE -> {
            parseDivider(node, configs, inlineTextContent)
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
        GFMTokenTypes.CELL -> {
            append(node.text.trim())
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
        MarkdownTokenTypes.EXCLAMATION_MARK -> append('!')
        MarkdownTokenTypes.BACKTICK -> Unit // `
        MarkdownTokenTypes.HARD_LINE_BREAK -> append('\n')
        MarkdownTokenTypes.WHITE_SPACE -> append(' ')
        MarkdownTokenTypes.EMPH -> Unit // *
        GFMTokenTypes.TILDE -> Unit // ~
        GFMTokenTypes.CHECK_BOX -> {
            parseCheckbox(node, configs, inlineTextContent)
        }
        GFMElementTypes.TABLE -> {
            parseTable(node, configs, inlineTextContent)
        }
        else -> {
            Napier.d { "??? ${node.type.name} ${node.text}" }
            append(node.text)
        }
    }
}
