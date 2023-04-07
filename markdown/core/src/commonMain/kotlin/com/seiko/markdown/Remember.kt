package com.seiko.markdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownConfigsBuilder
import com.seiko.markdown.config.MarkdownTypography

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
