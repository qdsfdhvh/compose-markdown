package com.seiko.markdown.parse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

fun AnnotatedString.Builder.parseBlockQuote(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val blockQuoteKey = node.toString()
    val blockQuoteAnnotatedString = buildAnnotatedString {
        node.children.forEach { child ->
            withStyle(configs.typography.text.toSpanStyle()) {
                parseMarkdown(child, content, configs, inlineTextContent)
            }
        }
    }
    inlineTextContent[blockQuoteKey] = InlineTextContent(
        placeholder = Placeholder(
            width = configs.maxWidthSP,
            height = configs.calcTextHeight(blockQuoteAnnotatedString),
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Spacer(
                modifier = Modifier.fillMaxHeight()
                    .padding(start = 4.dp, end = 4.dp)
                    .width(3.dp)
                    .background(Color.LightGray, CircleShape),
            )
            configs.Content(
                MarkdownWidget.Text(text = blockQuoteAnnotatedString),
            )
        }
    }

    val blockQuoteContent = node.getTextInNode(content).toString()
    appendInlineContent(blockQuoteKey, blockQuoteContent)
}
