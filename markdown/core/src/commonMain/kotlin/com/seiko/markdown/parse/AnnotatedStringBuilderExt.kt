package com.seiko.markdown.parse

import androidx.compose.ui.text.AnnotatedString

internal fun AnnotatedString.Builder.append(charSequence: CharSequence) {
    append(charSequence.toString())
}
