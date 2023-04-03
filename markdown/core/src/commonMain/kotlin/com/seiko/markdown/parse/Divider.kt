package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

fun AnnotatedString.Builder.parseDivider(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val dividerKey = node.toString()
    inlineTextContent[dividerKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = configs.dividerHeightSp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        configs.Content(
            MarkdownWidget.Divider,
        )
    }

    val dividerContent = node.getTextInNode(content).toString()
    appendInlineContent(dividerKey, dividerContent)
}
