package com.seiko.markdown.config

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Suppress("FunctionName")
fun Material3MarkdownTypography(typography: Typography) = MarkdownTypography(
    h1 = typography.displayMedium,
    h2 = typography.displaySmall,
    h3 = typography.headlineLarge,
    h4 = typography.headlineMedium,
    h5 = typography.headlineSmall,
    h6 = typography.titleLarge,
    text = typography.labelLarge,
    code = typography.labelLarge,
    url = TextStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
    ),
)

val MaterialTheme.markdownTypography: MarkdownTypography
    @Composable
    @ReadOnlyComposable
    get() = Material3MarkdownTypography(typography)
