package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

fun AnnotatedString.Builder.parseCheckbox(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val checkboxKey = node.toString()
    val nodeText = node.getTextInNode(content).toString()
    inlineTextContent[checkboxKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 24.sp,
            height = 24.sp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        configs.widget.Checkbox(
            checked = node.isChecked(nodeText),
            onCheckedChange = {},
        )
    }
    appendInlineContent(checkboxKey, nodeText)
    append(' ')
}

private fun ASTNode.isChecked(nodeText: String): Boolean =
    type == GFMTokenTypes.CHECK_BOX && "[x]" in nodeText
