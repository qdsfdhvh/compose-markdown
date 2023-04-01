package com.seiko.markdown.parse

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

fun AnnotatedString.Builder.parseListBullet(node: ASTNode) {
    val isCheckboxItem = node.parent?.children
        ?.any { it.type == GFMTokenTypes.CHECK_BOX }
        ?: false
    // Only append bullet if this isn't a Checkbox item
    if (!isCheckboxItem) {
        withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append("    $BULLET_CHAR ")
        }
    }
}

private const val BULLET_CHAR = '•'
