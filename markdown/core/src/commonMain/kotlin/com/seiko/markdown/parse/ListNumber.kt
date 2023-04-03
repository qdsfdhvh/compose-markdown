package com.seiko.markdown.parse

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.model.MarkdownNode

internal fun AnnotatedString.Builder.parseListNumber(node: MarkdownNode) {
    withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
        append("    ")
        val text = node.text.trim()
        if (text.endsWith(ORDER_SUFFIX)) {
            append(text)
        } else {
            append(text.dropLast(1))
            append(ORDER_SUFFIX)
        }
        append(' ')
    }
}

private const val ORDER_SUFFIX = '.'
