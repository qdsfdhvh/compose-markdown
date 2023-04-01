package com.seiko.markdown

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor

@OptIn(ExperimentalTextApi::class)
@Composable
fun rememberMaterialMarkdownTextContent(
    content: String,
    flavour: MarkdownFlavourDescriptor = remember { GFMFlavourDescriptor() },
): MarkdownTextContent {
    val typography = MaterialTheme.typography
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val configs = remember(typography) {
        MarkdownConfigs(
            typography = MaterialMarkdownTypography(typography),
            widget = MaterialMarkdownWidget(),
            density = density,
            textMeasurer = textMeasurer,
        )
    }
    return rememberMarkdownTextContent(content, configs, flavour)
}
