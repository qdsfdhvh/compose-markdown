package com.seiko.markdown

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

internal fun AnnotatedString.Builder.append(node: ASTNode, content: String) {
    append(node.getTextInNode(content).toString())
}

internal fun AnnotatedString.Builder.appendUrl(
    url: String,
    textStyle: TextStyle,
    label: String? = null,
) {
    withUrlStyle(url, textStyle) {
        append(label ?: url)
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun <R : Any> AnnotatedString.Builder.withUrlStyle(
    url: String,
    textStyle: TextStyle,
    block: AnnotatedString.Builder.() -> R
) {
    withAnnotation(UrlAnnotation(url)) {
        withStyle(textStyle.toSpanStyle(), block)
    }
}

internal fun <R : Any> AnnotatedString.Builder.withStyle(
    textStyle: TextStyle,
    block: AnnotatedString.Builder.() -> R
) {
    withStyle(textStyle.toParagraphStyle()) {
        withStyle(textStyle.toSpanStyle(), block)
    }
}
