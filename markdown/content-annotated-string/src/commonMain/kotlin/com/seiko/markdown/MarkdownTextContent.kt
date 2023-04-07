package com.seiko.markdown

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString

data class MarkdownTextContent internal constructor(
    val annotatedString: AnnotatedString,
    val inlineTextContent: Map<String, InlineTextContent>,
)
