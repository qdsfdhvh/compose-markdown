package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import io.github.aakira.napier.Napier
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

internal fun MarkdownContentBuilder.parseMarkdown(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    when (node.type) {
        MarkdownTokenTypes.ATX_CONTENT,
        MarkdownElementTypes.MARKDOWN_FILE,
        MarkdownElementTypes.UNORDERED_LIST,
        MarkdownElementTypes.ORDERED_LIST,
        MarkdownElementTypes.LIST_ITEM,
        MarkdownElementTypes.PARAGRAPH,
        MarkdownElementTypes.ATX_1,
        MarkdownElementTypes.ATX_2,
        MarkdownElementTypes.ATX_3,
        MarkdownElementTypes.ATX_4,
        MarkdownElementTypes.ATX_5,
        MarkdownElementTypes.ATX_6,
        MarkdownElementTypes.EMPH,
        MarkdownElementTypes.STRONG,
        MarkdownElementTypes.CODE_SPAN,
        GFMElementTypes.STRIKETHROUGH -> {
            visitChildren(node, configs, inlineTextContent)
        }
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
