package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

internal fun AnnotatedString.Builder.parseCheckbox(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val checkboxKey = node.toString()
    val nodeText = node.text
    inlineTextContent[checkboxKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.checkboxWidthSp,
            height = configs.checkboxHeightSp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        configs.Content(
            MarkdownWidget.Checkbox(checked = node.isChecked(nodeText)),
        )
    }
    appendInlineContent(checkboxKey, nodeText)
    append(' ')
}

private fun MarkdownNode.isChecked(nodeText: String): Boolean =
    type == GFMTokenTypes.CHECK_BOX && "[x]" in nodeText
