package com.seiko.markdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class MarkdownConfigs(
    val typography: MarkdownTypography = MarkdownTypography.Default,
    val padding: MarkdownPadding = MarkdownPadding.Default,
) {
    companion object {
        internal val Default = MarkdownConfigs()
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
    val paragraph: TextStyle,
    val code: TextStyle,
    val url: TextStyle,
) {
    companion object {
        internal val Default = MarkdownTypography(
            h1 = TextStyle(
                fontSize = 28.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
            ),
            h2 = TextStyle(
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
            ),
            h3 = TextStyle(
                fontSize = 20.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
            ),
            h4 = TextStyle(
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
            ),
            h5 = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp,
            ),
            h6 = TextStyle(
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.2.sp,
            ),
            text = TextStyle(
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.2.sp,
            ),
            paragraph = TextStyle(
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.2.sp,
            ),
            code = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.2.sp,
            ),
            url = TextStyle(
                color = Color(0xFF8E96FF),
            ),
        )
    }
}

data class MarkdownPadding(
    val block: TextUnit,
    val list: TextUnit,
    val indentList: TextUnit,
) {
    companion object {
        internal val Default = MarkdownPadding(
            block = 2.sp,
            list = 2.sp,
            indentList = 8.sp,
        )
    }
}
