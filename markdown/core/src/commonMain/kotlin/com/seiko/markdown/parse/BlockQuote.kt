package com.seiko.markdown.parse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownContentBuilder.parseBlockQuote(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val blockQuoteKey = node.toString()
    val blockQuoteAnnotatedString = buildAnnotatedString {
        node.children.forEach { child ->
            withStyle(configs.typography.text.toSpanStyle()) {
                parseMarkdown(child, configs, inlineTextContent)
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
