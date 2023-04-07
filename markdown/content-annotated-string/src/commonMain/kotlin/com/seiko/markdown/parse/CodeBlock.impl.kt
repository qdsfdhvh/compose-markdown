package com.seiko.markdown.parse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.drawText
import com.seiko.markdown.MarkdownNode
import com.seiko.markdown.MarkdownTextContentBuilder
import com.seiko.markdown.buildMarkdownTextContent
import org.intellij.markdown.MarkdownTokenTypes.Companion.EOL

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownTextContentBuilder.appendCodeBlockInternal(
    node: MarkdownNode,
) {
    val codeBlockKey = node.toString()
    val codeBlockAnnotatedString = buildMarkdownTextContent(configs) {
        var dropFirstEOL = false
        node.children.forEach { child ->
            if (!dropFirstEOL && child.type === EOL) {
                dropFirstEOL = true
                return@forEach
            }
            append(child)
        }
    }

    val textLayoutResult = configs.textMeasurer.measure(codeBlockAnnotatedString.annotatedString)

    inlineTextContent[codeBlockKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = with(configs.density) { textLayoutResult.size.height.toSp() },
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawRect(Color.LightGray.copy(alpha = 0.5f))
            drawText(textLayoutResult)
        }
    }
    appendInlineContent(codeBlockKey, node.text)
}
