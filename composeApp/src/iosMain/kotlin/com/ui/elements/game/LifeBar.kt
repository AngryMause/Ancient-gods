package com.ui.elements.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.them.typography.getTypography
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.back
import zeustheolympiandefender.composeapp.generated.resources.heart

@Composable
fun LifeBar(modifier: Modifier, lifeCount: Int) {
    Row(
        modifier = modifier.paint(
            painterResource(Res.drawable.back),
            contentScale = ContentScale.FillBounds
        ).padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painterResource(Res.drawable.heart),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = lifeCount.toString(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier,
            style = getTypography().h5
        )
    }

}