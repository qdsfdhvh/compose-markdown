package com.seiko.markdown

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor

@Composable
fun rememberMaterialMarkdownTextContent(
    content: String,
    flavour: MarkdownFlavourDescriptor = remember { GFMFlavourDescriptor() },
): MarkdownTextContent {
    val density = LocalDensity.current
    val typography = MaterialTheme.typography
    val configs = remember(typography) {
        MarkdownConfigs(
            typography = MaterialMarkdownTypography(typography),
            widget = MaterialMarkdownWidget(),
            density = density,
        )
    }
    return rememberMarkdownTextContent(content, configs, flavour)
}
