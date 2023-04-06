package com.seiko.markdown.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

data class MarkdownTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val text: TextStyle,
    val code: TextStyle,
    val url: TextStyle = TextStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
    ),
    val strong: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
    ),
    val em: TextStyle = TextStyle(
        fontStyle = FontStyle.Italic,
    ),
    val del: TextStyle = TextStyle(
        textDecoration = TextDecoration.LineThrough,
    ),
    val codeSpan: TextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        background = Color.LightGray.copy(alpha = 0.5f),
    ),
    val listNumber: TextStyle = TextStyle(
        fontWeight = FontWeight.ExtraBold,
    ),
    val listBullet: TextStyle = TextStyle(
        fontWeight = FontWeight.ExtraBold,
    ),
)
