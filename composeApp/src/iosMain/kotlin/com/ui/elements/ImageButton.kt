package com.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.button

@Composable
fun ImageTextButton(
    text: String, modifier: Modifier, style: TextStyle, textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        modifier = modifier.size(140.dp, 80.dp).paint(
            painterResource(Res.drawable.button),
            contentScale = ContentScale.FillBounds
        ).padding(10.dp),
        style = style,
        color = Color.White,
        textAlign = textAlign
    )
}