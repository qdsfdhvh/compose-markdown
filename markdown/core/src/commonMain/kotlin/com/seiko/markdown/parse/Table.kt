package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.em
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

fun AnnotatedString.Builder.parseTable(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val tableContentKey = node.toString()
    val tableContent = buildAnnotatedString {
        node.children.forEach { child ->
            parseMarkdown(child, content, configs, inlineTextContent)
        }
    }
    inlineTextContent[tableContentKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 80.em,
            height = configs.calcTextHeight(tableContent),
            placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline,
        ),
    ) {
        // TODO draw table here
        configs.widget.Text(
            text = AnnotatedString(node.getTextInNode(content).toString()),
            textStyle = configs.typography.text,
        )
    }
    appendInlineContent(tableContentKey, tableContent.text)
}
