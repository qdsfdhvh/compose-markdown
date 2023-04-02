package com.seiko.markdown.config

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

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
)

val MaterialTheme.markdownTypography: MarkdownTypography
    @Composable
    @ReadOnlyComposable
    get() = MaterialMarkdownTypography(typography)
