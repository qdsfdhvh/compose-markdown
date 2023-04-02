package com.seiko.markdown.config

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import com.seiko.markdown.config.MarkdownTypography

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
)
