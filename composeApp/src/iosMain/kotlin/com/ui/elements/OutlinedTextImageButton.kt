package com.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.button

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedTextImageButton(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = Color.White,
    outlineColor: Color = Color.Black,
    textAlign: TextAlign? = null,
    isBackgriundSHow: Boolean = true,
    backgroundPadding: PaddingValues = PaddingValues(),
    contentAlignment: Alignment = Alignment.Center,
    style: TextStyle = LocalTextStyle.current,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    strokeWith: Float = 12f,
    isColorFilterEnabled: Boolean = false,
) {
    Box(
        modifier = modifier.then(
            if (isBackgriundSHow) {
                Modifier.paint(
                    painterResource(Res.drawable.button),
                    contentScale = ContentScale.FillBounds,
                    colorFilter = if (isColorFilterEnabled) ColorFilter.tint(Color.Black.copy(alpha = 0.5f)) else null
                ).padding(backgroundPadding)
            } else {
                Modifier
            }
        ), contentAlignment = contentAlignment
    ) {
        Text(
            text = text,
            modifier = Modifier.semantics { invisibleToUser() },
            color = outlineColor,
            textAlign = textAlign,
            letterSpacing = letterSpacing,
            style = style.copy(drawStyle = Stroke(width = strokeWith)),
        )
        Text(
            text = text,
            color = fillColor,
            textAlign = textAlign,
            letterSpacing = letterSpacing,
            style = style,
        )
    }
}
