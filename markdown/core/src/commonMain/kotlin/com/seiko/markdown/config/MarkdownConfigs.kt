package com.seiko.markdown.config

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
class MarkdownConfigs internal constructor(
    val typography: MarkdownTypography,
    private val density: Density,
    private val textMeasurer: TextMeasurer,
    private val widgetPlugin: MarkdownWidgetPlugin,
    val dividerHeight: Dp = 1.dp,
) : MarkdownWidgetPlugin by widgetPlugin {
    val dividerHeightSp: TextUnit
        get() = with(density) { dividerHeight.toSp() }

    fun calcTextWidth(text: AnnotatedString): TextUnit {
        return with(density) {
            textMeasurer.measure(text).size.width.toSp()
        }
    }

    fun calcTextHeight(text: AnnotatedString): TextUnit {
        return with(density) {
            textMeasurer.measure(text).size.height.toSp()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
class MarkdownConfigsBuilder internal constructor(
    private val typography: MarkdownTypography,
    private val density: Density,
    private val textMeasurer: TextMeasurer,
) {
    var dividerHeight: Dp = 1.dp

    private val widgetPluginProxy = MarkdownWidgetPluginProxy()

    fun plugin(plugin: MarkdownWidgetPlugin) {
        widgetPluginProxy.add(plugin)
    }

    internal fun build() = MarkdownConfigs(
        typography = typography,
        density = density,
        textMeasurer = textMeasurer,
        widgetPlugin = widgetPluginProxy,
        dividerHeight = dividerHeight,
    )
}

@OptIn(ExperimentalTextApi::class)
fun MarkdownConfigs(
    typography: MarkdownTypography,
    density: Density,
    textMeasurer: TextMeasurer,
    builder: MarkdownConfigsBuilder.() -> Unit
) = MarkdownConfigsBuilder(
    typography = typography,
    density = density,
    textMeasurer = textMeasurer,
).apply(builder).build()
