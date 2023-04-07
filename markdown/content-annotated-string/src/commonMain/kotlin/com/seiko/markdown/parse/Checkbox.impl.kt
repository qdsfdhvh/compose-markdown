package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.MarkdownTextContentBuilder
import com.seiko.markdown.config.MarkdownWidget

internal fun MarkdownTextContentBuilder.appendCheckboxInternal(node: MarkdownNode) {
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
