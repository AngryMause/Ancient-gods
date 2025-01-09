package com.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.them.colors.BackButtonBackground
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.button_back

@Composable
fun BackButton(modifier: Modifier, onCLick: () -> Unit) {
    Image(painterResource(Res.drawable.button_back),
        contentDescription = "Back button",
        modifier = modifier.size(30.dp).clip(CircleShape)
            .background(brush = BackButtonBackground, shape = CircleShape)
            .clickable { onCLick() }
    )
}