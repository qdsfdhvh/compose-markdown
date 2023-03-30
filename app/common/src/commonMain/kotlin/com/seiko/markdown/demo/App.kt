package com.seiko.markdown.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    ComposeImageLoaderTheme {
        Scaffold { innerPadding ->
            Box(Modifier.padding(innerPadding).fillMaxSize(), Alignment.Center) {
                Text("Markdown Demo.")
            }
        }
    }
}
