package com.seiko.markdown.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.seiko.markdown.rememberParseMarkdown
import kotlinx.coroutines.launch

@Composable
fun App() {
    ComposeImageLoaderTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
        ) { innerPadding ->
            val scrollState = rememberScrollState()
            val markdown = rememberParseMarkdown(markdownCode)
            UrlText(
                text = markdown,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .fillMaxSize(),
                onUrlClicked = { url ->
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(url)
                    }
                },
            )
        }
    }
}
