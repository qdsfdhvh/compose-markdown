package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownTypography
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.ASTNode

fun AnnotatedString.Builder.parseHeader(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    withStyle(node.toTextStyle(configs.typography)) {
        node.children.forEach { child ->
            parseMarkdown(child, content, configs, inlineTextContent)
        }
    }
}

private fun ASTNode.toTextStyle(typography: MarkdownTypography): TextStyle {
    return with(typography) {
        when (type) {
            MarkdownElementTypes.SETEXT_1,
            MarkdownElementTypes.ATX_1 -> h1
            MarkdownElementTypes.SETEXT_2,
            MarkdownElementTypes.ATX_2 -> h2
            MarkdownElementTypes.ATX_3 -> h3
            MarkdownElementTypes.ATX_4 -> h4
            MarkdownElementTypes.ATX_5 -> h5
            MarkdownElementTypes.ATX_6 -> h6
            else -> text
        }
    }
}
