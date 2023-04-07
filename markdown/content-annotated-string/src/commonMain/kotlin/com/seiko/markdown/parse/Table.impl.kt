package com.seiko.markdown.parse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.MarkdownTextContentBuilder
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.CELL

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownTextContentBuilder.appendTableInternal(node: MarkdownNode) {
    val tableContentKey = node.toString()

    val headers = mutableListOf<AnnotatedString>()
    val rows = mutableListOf<List<AnnotatedString>>()
    node.children.forEach { child ->
        when (child.type) {
            GFMElementTypes.HEADER -> {
                headers.addAll(
                    child.children.filter { it.type == CELL }.map {
                        AnnotatedString(it.text)
                    },
                )
            }
            GFMTokenTypes.TABLE_SEPARATOR -> {
            }
            GFMElementTypes.ROW -> {
                rows.add(
                    child.children.filter { it.type == CELL }.map {
                        AnnotatedString(it.text)
                    },
                )
            }
        }
    }

    val cellHeight = 40.dp
    val tableHeight = with(configs.density) {
        cellHeight.toSp()
    } * (1 + rows.size) // TODO calc row height

    val borderColor = Color.Black
    val cellSpacing = 2.dp

    inlineTextContent[tableContentKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = tableHeight,
            placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline,
        ),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val cellWidthPx = size.width / headers.size
            val cellHeightPx = cellHeight.toPx()

            var x = 0f
            var y = 0f
            headers.forEach { header ->
                drawRect(
                    color = borderColor,
                    topLeft = Offset(x, y),
                    size = Size(cellWidthPx, cellHeightPx),
                    style = Stroke(1f),
                )
                val textLayoutResult = configs.textMeasurer.measure(header)
                drawText(
                    textLayoutResult,
                    topLeft = Offset(
                        x = x + cellSpacing.toPx(),
                        y = y + (cellHeightPx - textLayoutResult.size.height) / 2,
                    ),
                )
                x += cellWidthPx
            }
            rows.forEach { row ->
                x = 0f
                y += cellHeightPx
                row.forEach { cell ->
                    drawRect(
                        color = borderColor,
                        topLeft = Offset(x, y),
                        size = Size(cellWidthPx, cellHeightPx),
                        style = Stroke(1f),
                    )
                    val textLayoutResult = configs.textMeasurer.measure(cell)
                    drawText(
                        textLayoutResult,
                        topLeft = Offset(
                            x = x + cellSpacing.toPx(),
                            y = y + (cellHeightPx - textLayoutResult.size.height) / 2,
                        ),
                    )
                    x += cellWidthPx
                }
            }
        }
    }
    appendInlineContent(tableContentKey, node.text)
}
