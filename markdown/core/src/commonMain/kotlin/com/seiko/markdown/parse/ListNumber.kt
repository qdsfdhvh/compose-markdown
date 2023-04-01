package com.seiko.markdown.parse

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

fun AnnotatedString.Builder.parseListNumber(
    node: ASTNode,
    content: String,
) {
    withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
        val rawListMarker = node.getTextInNode(content).trim()
        val formattedListMarker = if (!rawListMarker.endsWith(ORDER_SUFFIX)) {
            "${rawListMarker.dropLast(1)}$ORDER_SUFFIX" // Handle 1) case
        } else {
            rawListMarker
        }
        append("    $formattedListMarker ")
    }
}

private const val ORDER_SUFFIX = '.'
