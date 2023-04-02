package com.seiko.markdown

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.parse.parseMarkdown
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

@Composable
fun rememberMarkdownTextContent(
    content: String,
    configs: MarkdownConfigs,
    flavour: MarkdownFlavourDescriptor = remember { GFMFlavourDescriptor() },
): MarkdownTextContent {
    return remember(content, configs, flavour) {
        parseMarkdownTextContent(
            content = content,
            configs = configs,
            flavour = flavour,
        )
    }
}

fun parseMarkdownTextContent(
    content: String,
    configs: MarkdownConfigs,
    flavour: MarkdownFlavourDescriptor = GFMFlavourDescriptor()
): MarkdownTextContent {
    val rootNode = MarkdownParser(flavour).buildMarkdownTreeFromString(content)

    val inlineTextContent = mutableMapOf<String, InlineTextContent>()
    val annotatedString = buildAnnotatedString {
        parseMarkdown(rootNode, content, configs, inlineTextContent)
    }

    return MarkdownTextContent(
        annotatedString = annotatedString,
        inlineTextContent = inlineTextContent,
    )
}

data class MarkdownTextContent(
    val annotatedString: AnnotatedString,
    val inlineTextContent: Map<String, InlineTextContent>,
)
