package com.seiko.markdown.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    var isMaterial3 by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    ComposeImageLoaderTheme {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = isMaterial3, onCheckedChange = { isMaterial3 = it })
                Spacer(Modifier.width(16.dp))
                Text(if (isMaterial3) "Material3" else "Material")
            }
            if (isMaterial3) {
                Material3Content(markdownCode, scope)
            } else {
                MaterialContent(markdownCode, scope)
            }
        }
    }
}

@Composable
private fun ComposeImageLoaderTheme(
    content: @Composable () -> Unit
) {
    androidx.compose.material.MaterialTheme {
        androidx.compose.material3.MaterialTheme {
            content()
        }
    }
}
