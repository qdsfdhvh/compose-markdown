package com.seiko.markdown.parse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.MarkdownTextContentBuilder

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownTextContentBuilder.appendBlockQuoteInternal(node: MarkdownNode) {
    val blockQuoteKey = node.toString()
    val blockQuoteAnnotatedString = buildAnnotatedString {
        node.children.forEach { child ->
            withStyle(configs.typography.text) {
                append(child)
            }
        }
    }

    val textLayoutResult = configs.textMeasurer.measure(blockQuoteAnnotatedString)
    inlineTextContent[blockQuoteKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = with(configs.density) { textLayoutResult.size.height.toSp() },
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            var offsetX = 2.dp.toPx()
            drawRect(Color.LightGray, size = Size(2.dp.toPx(), size.height), topLeft = Offset(offsetX, 0f))
            offsetX += 2.dp.toPx()
            drawText(textLayoutResult, topLeft = Offset(offsetX, 0f))
        }
    }
    appendInlineContent(BLOCK_QUOTE_KEY, "[BlockQuote]")
}

private const val BLOCK_QUOTE_KEY = "[BlockQuote]"
