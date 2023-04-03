package com.seiko.markdown.parse

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.CELL

internal fun AnnotatedString.Builder.parseTable(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val tableContentKey = node.toString()

    val headers = mutableListOf<String>()
    val rows = mutableListOf<List<String>>()
    node.children.forEach { child ->
        when (child.type) {
            GFMElementTypes.HEADER -> {
                headers.addAll(
                    child.children.filter { it.type == CELL }.map { it.text },
                )
            }
            GFMTokenTypes.TABLE_SEPARATOR -> {
            }
            GFMElementTypes.ROW -> {
                rows.add(
                    child.children.filter { it.type == CELL }.map { it.text },
                )
            }
        }
    }
    val tableHeight = 40.sp * (1 + rows.size) // TODO calc row height
    inlineTextContent[tableContentKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = tableHeight,
            placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline,
        ),
    ) {
        configs.Content(
            MarkdownWidget.Table(
                headers = headers,
                rows = rows,
            ),
        )
    }
    appendInlineContent(tableContentKey, node.text)
}
