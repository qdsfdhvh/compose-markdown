package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.em
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import org.intellij.markdown.ast.ASTNode

fun AnnotatedString.Builder.parseTable(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val tableContentKey = node.toString()
    val tableContent = buildAnnotatedString {
        node.children.forEach { child ->
            withStyle(configs.typography.text.toSpanStyle()) {
                parseMarkdown(child, content, configs, inlineTextContent)
            }
        }
    }
    inlineTextContent[tableContentKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 80.em,
            height = configs.calcTextHeight(tableContent),
            placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline,
        ),
    ) {
        configs.Content(
            MarkdownWidget.Text(text = tableContent),
        )
    }
    appendInlineContent(tableContentKey, tableContent.text)
}
