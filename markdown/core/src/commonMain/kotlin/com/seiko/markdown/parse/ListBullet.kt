package com.seiko.markdown.parse

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.seiko.markdown.MarkdownContentBuilder
import com.seiko.markdown.model.MarkdownNode
import org.intellij.markdown.flavours.gfm.GFMTokenTypes

internal fun MarkdownContentBuilder.parseListBullet(node: MarkdownNode) {
    val isCheckboxItem = node.parent?.children
        ?.any { it.type == GFMTokenTypes.CHECK_BOX }
        ?: false
    // Only append bullet if this isn't a Checkbox item
    if (!isCheckboxItem) {
        withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append("    ")
            append(BULLET_CHAR)
            append(' ')
        }
    }
}

private const val BULLET_CHAR = '•'
