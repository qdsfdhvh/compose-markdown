package com.seiko.markdown

import androidx.compose.ui.text.TextStyle
import com.seiko.markdown.config.MarkdownConfigs
import com.seiko.markdown.config.MarkdownWidget

class MarkdownComposeContentBuilder internal constructor(
    override val configs: MarkdownConfigs,
) : MarkdownContentBuilder {

    // private val widget = configs.

    private val contents = mutableListOf<ComposeUnit>()

    private var currentRow = mutableListOf<RowBuilder>()

    override fun append(text: String) {
        currentRow.add {
            configs.Content(
                MarkdownWidget.Text(text),
            )
        }
    }

    override fun append(char: Char) {
        append(char.toString())
    }

    override fun pushStyle(style: TextStyle) {
        TODO("Not yet implemented")
    }

    override fun pop() {
        TODO("Not yet implemented")
    }

    override fun <R : Any> withStyle(style: TextStyle, builder: MarkdownContentBuilder.() -> R) {
        TODO("Not yet implemented")
    }

    override fun withClick(url: String, builder: MarkdownContentBuilder.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun appendBlockQuote(node: MarkdownNode) {
        TODO("Not yet implemented")
    }

    override fun appendCheckbox(node: MarkdownNode) {
        TODO("Not yet implemented")
    }

    override fun appendCodeBlock(node: MarkdownNode) {
        TODO("Not yet implemented")
    }

    override fun appendDivider(node: MarkdownNode) {
        currentRow.add {
            configs.Content(
                MarkdownWidget.Divider,
            )
        }
    }

    override fun appendImage(imageUrl: String) {
        currentRow.add {
            configs.Content(
                MarkdownWidget.Image(imageUrl),
            )
        }
    }

    override fun appendTable(node: MarkdownNode) {
        TODO("Not yet implemented")
    }
}
