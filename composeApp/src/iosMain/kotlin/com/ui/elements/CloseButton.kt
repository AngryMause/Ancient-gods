package com.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.them.colors.BackButtonBackground

@Composable
fun CloseButton(modifier: Modifier, onClick: () -> Unit) {
    Icon(
        Icons.Default.Close,
        null,
        tint = Color.White,
        modifier = modifier.clip(CircleShape)
            .background(brush = BackButtonBackground)
            .clickable { onClick() }
    )
}