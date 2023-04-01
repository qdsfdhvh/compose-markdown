package com.seiko.markdown.parse

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.dp
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
            width = 32.sp,
            height = 24.sp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Row {
            configs.widget.Checkbox(
                checked = node.isChecked(nodeText),
                onCheckedChange = {},
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
    appendInlineContent(checkboxKey, nodeText)
}

private fun ASTNode.isChecked(nodeText: String): Boolean =
    type == GFMTokenTypes.CHECK_BOX && "[x]" in nodeText
