package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.em
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

fun AnnotatedString.Builder.parseDivider(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val dividerKey = node.toString()
    val nodeText = node.getTextInNode(content).toString()
    inlineTextContent[dividerKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 100.em,
            height = configs.dividerHeightSp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        configs.widget.Divider()
    }
    appendInlineContent(dividerKey, nodeText)
}
