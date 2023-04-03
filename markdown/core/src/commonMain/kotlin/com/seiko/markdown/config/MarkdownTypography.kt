package com.seiko.markdown.config

import androidx.compose.ui.text.TextStyle

data class MarkdownTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val text: TextStyle,
    val code: TextStyle,
    val url: TextStyle,
)
