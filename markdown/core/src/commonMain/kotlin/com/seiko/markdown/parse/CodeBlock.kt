package com.seiko.markdown.parse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.MarkdownTokenTypes.Companion.EOL

@OptIn(ExperimentalTextApi::class)
internal fun MarkdownContentBuilder.parseCodeBlock(
    node: MarkdownNode,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val codeBlockKey = node.toString()
    val codeBlockAnnotatedString = buildAnnotatedString {
        var dropFirstEOL = false
        node.children.forEach { child ->
            if (!dropFirstEOL && child.type === EOL) {
                dropFirstEOL = true
                return@forEach
            }
            parseMarkdown(child, configs, inlineTextContent)
        }
    }

    val textLayoutResult = configs.textMeasurer.measure(codeBlockAnnotatedString)

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
