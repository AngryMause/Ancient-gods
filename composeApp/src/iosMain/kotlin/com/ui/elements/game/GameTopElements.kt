package com.ui.elements.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.data.util.getCurrentTimeInMilliSeconds
import com.them.typography.getTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.back
import zeustheolympiandefender.composeapp.generated.resources.goal_left
import zeustheolympiandefender.composeapp.generated.resources.goal_reigth
import zeustheolympiandefender.composeapp.generated.resources.icon_zeuse
import zeustheolympiandefender.composeapp.generated.resources.left
import zeustheolympiandefender.composeapp.generated.resources.points
import zeustheolympiandefender.composeapp.generated.resources.round
import kotlin.math.roundToInt

@Composable
fun GameTopElements(modifier: Modifier, point: Int, left: Int, round: Int, lifeCount: Int) {
    val shake = remember { Animatable(0f) }
    var trigger by remember { mutableStateOf(0L) }
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(lifeCount) {
        if (lifeCount != 50) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            trigger = getCurrentTimeInMilliSeconds()
        }
    }
    LaunchedEffect(trigger) {
        if (trigger != 0L) {
            for (i in 0..10) {
                when (i % 2) {
                    0 -> shake.animateTo(5f, spring(stiffness = 100_000f))
                    else -> shake.animateTo(-5f, spring(stiffness = 100_000f))
                }
            }
            shake.animateTo(0f)
        }
    }
    Row(
        modifier.fillMaxHeight(0.1f).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(Res.string.points, point),
            color = Color.White,
            modifier = Modifier.paint(
                painterResource(Res.drawable.back),
                contentScale = ContentScale.FillBounds
            ).padding(horizontal = 5.dp),
            style = getTypography().body2
        )
        Text(
            stringResource(Res.string.left, left),
            style = getTypography().body2,
            modifier = Modifier.paint(
                painterResource(Res.drawable.back),
                contentScale = ContentScale.FillBounds
            ).padding(horizontal = 5.dp),
            color = Color.White
        )
        Text(
            stringResource(Res.string.round, round),
            style = getTypography().body2,
            modifier = Modifier.paint(
                painterResource(Res.drawable.back),
                contentScale = ContentScale.FillBounds
            ).padding(horizontal = 5.dp),
            color = Color.White
        )
        LifeBar(
            modifier = Modifier,
            lifeCount = lifeCount
        )
    }
    Row(
        Modifier.fillMaxWidth().padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painterResource(Res.drawable.goal_left),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(width = 100.dp, height = 100.dp)
        )
        Image(
            painterResource(Res.drawable.icon_zeuse),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(100.dp).graphicsLayer {
                translationX = shake.value.roundToInt().toFloat()
            }
        )
        Image(
            painterResource(Res.drawable.goal_reigth),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(width = 100.dp, height = 100.dp)
        )
    }
}