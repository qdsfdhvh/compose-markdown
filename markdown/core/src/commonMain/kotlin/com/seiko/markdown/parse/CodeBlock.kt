package com.seiko.markdown.parse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.MarkdownTokenTypes.Companion.EOL

internal fun AnnotatedString.Builder.parseCodeBlock(
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
    inlineTextContent[codeBlockKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = configs.calcTextHeight(codeBlockAnnotatedString),
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.LightGray.copy(alpha = 0.5f))
                .fillMaxSize(),
        ) {
            configs.Content(
                MarkdownWidget.Text(text = codeBlockAnnotatedString),
            )
        }
    }
    appendInlineContent(codeBlockKey, node.text)
}
