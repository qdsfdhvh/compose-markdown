package com.seiko.markdown.parse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.ast.ASTNode

fun AnnotatedString.Builder.parseBlockQuote(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val blockQuoteKey = node.toString()
    val blockQuoteContent = buildAnnotatedString {
        node.children.forEach { child ->
            parseMarkdown(child, content, configs, inlineTextContent)
        }
    }
    inlineTextContent[blockQuoteKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 100.em,
            height = 70.sp, // auto height
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Spacer(
                modifier = Modifier.fillMaxHeight()
                    .padding(start = 4.dp, end = 4.dp)
                    .width(3.dp)
                    .background(Color.LightGray, RoundedCornerShape(50)),
            )
            configs.widget.Text(
                text = blockQuoteContent,
                textStyle = configs.typography.text,
            )
        }
    }
    appendInlineContent(blockQuoteKey, blockQuoteContent.text)
}
