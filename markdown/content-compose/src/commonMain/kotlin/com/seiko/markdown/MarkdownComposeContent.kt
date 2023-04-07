package com.seiko.markdown

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class MarkdownComposeContent(
    val contents: List<ComposeUnit>,
)

internal typealias ComposeUnit = @Composable () -> Unit
internal typealias RowBuilder = @Composable RowScope.() -> Unit
