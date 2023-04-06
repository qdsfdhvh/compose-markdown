package com.seiko.markdown

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownConfigsBuilder
import com.seiko.markdown.config.MarkdownTypography
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

@OptIn(ExperimentalTextApi::class)
@Composable
fun rememberMarkdownConfigs(
    typography: MarkdownTypography,
    density: Density = LocalDensity.current,
    textMeasurer: TextMeasurer = rememberTextMeasurer(),
    builder: MarkdownConfigsBuilder.() -> Unit,
): MarkdownConfigs {
    val updateBuilder by rememberUpdatedState(builder)
    return remember(typography, density, textMeasurer, updateBuilder) {
        MarkdownConfigs(
            typography = typography,
            density = density,
            textMeasurer = textMeasurer,
            builder = updateBuilder,
        )
    }
}

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
    val rootMarkdownNode = MarkdownNode(rootNode, null, content)
    return buildMarkdownContent(configs) {
        append(rootMarkdownNode)
    }
}

data class MarkdownTextContent(
    val annotatedString: AnnotatedString,
    val inlineTextContent: Map<String, InlineTextContent>,
)
