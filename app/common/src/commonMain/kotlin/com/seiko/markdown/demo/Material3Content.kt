package com.seiko.markdown.demo

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.seiko.markdown.rememberMaterial3MarkdownTextContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3Content(
    content: String,
    scope: CoroutineScope,
) {
    val scrollState = rememberScrollState()
    val (annotatedString, inlineContent) = rememberMaterial3MarkdownTextContent(content)
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {
        UrlText(
            annotatedString,
            inlineContent = inlineContent,
            onUrlClicked = { url ->
                scope.launch {
                    snackBarHostState.showSnackbar(url)
                }
            },
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun UrlText(
    text: AnnotatedString,
    onUrlClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    val textModifier = modifier.pointerInput(Unit) {
        forEachGesture {
            coroutineScope {
                val change = awaitPointerEventScope {
                    awaitFirstDown()
                }
                val annotation =
                    layoutResult.value?.getOffsetForPosition(change.position)?.let {
                        text.getUrlAnnotations(it, it)
                            .firstOrNull()
                    }
                if (annotation != null) {
                    if (change.pressed != change.previousPressed) change.consume()
                    val up = awaitPointerEventScope {
                        waitForUpOrCancellation()?.also {
                            if (it.pressed != it.previousPressed) it.consume()
                        }
                    }
                    if (up != null) {
                        onUrlClicked.invoke(annotation.item.url)
                    }
                }
            }
        }
    }

    Text(
        text = text,
        modifier = textModifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        },
        style = style,
    )
}
