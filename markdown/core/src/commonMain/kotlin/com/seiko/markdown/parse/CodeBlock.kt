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
import androidx.compose.ui.unit.em
import com.seiko.markdown.MarkdownConfigs
import org.intellij.markdown.ast.ASTNode

fun AnnotatedString.Builder.parseCodeBlock(
    node: ASTNode,
    content: String,
    configs: MarkdownConfigs,
    inlineTextContent: MutableMap<String, InlineTextContent>,
) {
    val codeBlockKey = node.toString()
    val codeBlockContent = buildAnnotatedString {
        // drop CODE_FENCE_START EOL
        node.children.asSequence().drop(2).forEach { child ->
            parseMarkdown(child, content, configs, inlineTextContent)
        }
    }
    inlineTextContent[codeBlockKey] = InlineTextContent(
        placeholder = Placeholder(
            width = 100.em,
            height = configs.calcTextHeight(codeBlockContent),
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.LightGray.copy(alpha = 0.5f))
                .fillMaxSize(),
        ) {
            configs.widget.Text(
                text = codeBlockContent,
                textStyle = configs.typography.text,
            )
        }
    }
    appendInlineContent(codeBlockKey, codeBlockContent.text)
}
