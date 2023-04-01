package com.seiko.markdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
data class MarkdownConfigs(
    val typography: MarkdownTypography,
    val widget: MarkdownWidget,
    private val density: Density,
    private val textMeasurer: TextMeasurer,
    val dividerHeight: Dp = 1.dp,
) {
    val dividerHeightSp: TextUnit
        get() = with(density) { dividerHeight.toSp() }

    fun calcTextHeight(text: AnnotatedString): TextUnit {
        return with(density) {
            textMeasurer.measure(text).size.height.toSp()
        }
    }
}

data class MarkdownTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val text: TextStyle,
    val code: TextStyle,
)

interface MarkdownWidget {
    @Composable
    fun Text(
        text: AnnotatedString,
        textStyle: TextStyle,
    )

    @Composable
    fun Image(
        url: String,
    )

    @Composable
    fun Checkbox(
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
    )

    @Composable
    fun Divider()
}
