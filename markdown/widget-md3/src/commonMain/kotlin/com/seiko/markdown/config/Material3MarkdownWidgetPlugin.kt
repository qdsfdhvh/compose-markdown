package com.seiko.markdown.config

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Material3MarkdownWidgetPlugin : MarkdownWidgetPlugin {

    @Composable
    override fun Content(enum: MarkdownWidget): Boolean {
        return when (enum) {
            is MarkdownWidget.Checkbox -> {
                androidx.compose.material3.Checkbox(enum.checked, {})
                true
            }
            MarkdownWidget.Divider -> {
                androidx.compose.material3.Divider()
                true
            }
            is MarkdownWidget.Table -> {
                Column(Modifier.fillMaxWidth()) {
                    val weight = 1f
                    Row(Modifier.background(Color.Gray)) {
                        enum.headers.forEach { header ->
                            TableCell(header, weight)
                        }
                    }
                    enum.rows.forEach { row ->
                        Row(Modifier.background(Color.Gray)) {
                            row.take(enum.headers.size).forEach { content ->
                                TableCell(content, weight)
                            }
                        }
                    }
                }
                true
            }
            else -> false
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    androidx.compose.material3.Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
    )
}
