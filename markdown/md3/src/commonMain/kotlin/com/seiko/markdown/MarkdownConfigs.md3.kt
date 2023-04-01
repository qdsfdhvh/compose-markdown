package com.seiko.markdown

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Suppress("FunctionName")
fun Material3MarkdownTypography(typography: Typography) = MarkdownTypography(
    h1 = typography.displayMedium,
    h2 = typography.displaySmall,
    h3 = typography.headlineLarge,
    h4 = typography.headlineMedium,
    h5 = typography.headlineSmall,
    h6 = typography.titleLarge,
    text = typography.labelLarge,
    code = typography.labelLarge.copy(
        background = Color.LightGray.copy(alpha = 0.5f)
    ),
)

@Suppress("FunctionName")
fun Material3MarkdownWidget() = object : MarkdownWidget {
    @Composable
    override fun Text(
        text: AnnotatedString,
        textStyle: TextStyle
    ) {
        androidx.compose.material3.Text(text, style = textStyle)
    }

    @Composable
    override fun Image(url: String) {
    }

    @Composable
    override fun Checkbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        androidx.compose.material3.Checkbox(checked, onCheckedChange)
    }

    @Composable
    override fun Divider() {
        androidx.compose.material3.Divider()
    }
}
