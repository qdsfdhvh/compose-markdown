package com.seiko.markdown.config

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Suppress("FunctionName")
fun MaterialMarkdownTypography(typography: Typography) = MarkdownTypography(
    h1 = typography.h3,
    h2 = typography.h4,
    h3 = typography.h5,
    h4 = typography.h6,
    h5 = typography.subtitle1,
    h6 = typography.subtitle2,
    text = typography.body1,
    code = typography.body1,
    url = TextStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
    ),
)

val MaterialTheme.markdownTypography: MarkdownTypography
    @Composable
    @ReadOnlyComposable
    get() = MaterialMarkdownTypography(typography)
