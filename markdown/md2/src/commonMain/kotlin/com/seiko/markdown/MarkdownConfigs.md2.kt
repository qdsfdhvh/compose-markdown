package com.seiko.markdown

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Suppress("FunctionName")
fun MaterialMarkdownTypography(typography: Typography) = MarkdownTypography(
    h1 = typography.h3,
    h2 = typography.h4,
    h3 = typography.h5,
    h4 = typography.h6,
    h5 = typography.subtitle1,
    h6 = typography.subtitle2,
    text = typography.body1,
    code = typography.body1.copy(
        background = Color.LightGray.copy(alpha = 0.5f)
    ),
)

@Suppress("FunctionName")
fun MaterialMarkdownWidget() = object : MarkdownWidget {
    @Composable
    override fun Text(
        text: AnnotatedString,
        textStyle: TextStyle,
    ) {
        androidx.compose.material.Text(text, style = textStyle)
    }

    @Composable
    override fun Image(url: String) {
    }

    @Composable
    override fun Checkbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        androidx.compose.material.Checkbox(checked, onCheckedChange)
    }

    @Composable
    override fun Divider() {
        androidx.compose.material.Divider()
    }
}
