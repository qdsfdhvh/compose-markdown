package com.seiko.markdown.parse

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.withStyle

internal fun <R : Any> AnnotatedString.Builder.withStyle(
    textStyle: TextStyle,
    block: AnnotatedString.Builder.() -> R
) {
    withStyle(textStyle.toParagraphStyle()) {
        withStyle(textStyle.toSpanStyle(), block)
    }
}

internal fun AnnotatedString.Builder.append(charSequence: CharSequence) {
    append(charSequence.toString())
}
