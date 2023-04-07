package com.seiko.markdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.seiko.markdown.config.MarkdownConfigs
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor

@Composable
fun rememberMarkdownTextContent(
    content: String,
    configs: MarkdownConfigs,
    flavour: MarkdownFlavourDescriptor = remember { GFMFlavourDescriptor() },
): MarkdownTextContent {
    return remember(content, configs, flavour) {
        buildMarkdownTextContent(configs) {
            val rootNode = MarkdownNode(content, flavour)
            append(rootNode)
        }
    }
}
