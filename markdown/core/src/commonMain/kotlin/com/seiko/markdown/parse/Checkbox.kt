package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode

internal fun MarkdownContentBuilder.parseCheckbox(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    inlineTextContent.getOrPut(CHECKBOX_KEY) {
        InlineTextContent(
            placeholder = Placeholder(
                width = configs.checkboxWidthSp,
                height = configs.checkboxHeightSp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
            ),
        ) { nodeText ->
            configs.Content(
                MarkdownWidget.Checkbox(checked = nodeText.isChecked),
            )
        }
    }
    appendInlineContent(CHECKBOX_KEY, node.text)
    append(' ')
}

private const val CHECKBOX_KEY = "[Checkbox]"

private val String.isChecked: Boolean
    get() = "[x]" in this
