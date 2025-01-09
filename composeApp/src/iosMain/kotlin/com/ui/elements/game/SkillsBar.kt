package com.ui.elements.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ui.elements.state.SkillsBarEvent
import com.them.colors.ColorSkillBarYellow
import com.them.colors.ColorSkillBarBackground
import com.them.typography.getTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.earthqueq
import zeustheolympiandefender.composeapp.generated.resources.tunder
import zeustheolympiandefender.composeapp.generated.resources.warht_of_good
import zeustheolympiandefender.composeapp.generated.resources.windstorm


@Composable
fun SkillsBar(
    modifier: Modifier,
    isButtonEnabled: Boolean,
    scrollCount: Int,
    onEvent: (SkillsBarEvent) -> Unit
) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(10.dp))
            .border(3.dp, color = ColorSkillBarYellow,RoundedCornerShape(10.dp))
            .background(ColorSkillBarBackground)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SkillImageAnim(
            modifier = Modifier.size(60.dp),
            image = Res.drawable.tunder,
            isButtonEnabled = isButtonEnabled,
            onClick = { onEvent(SkillsBarEvent.THUNDER) }
        )

        SkillImageAnim(
            modifier = Modifier.size(60.dp),
            isButtonEnabled = isButtonEnabled,
            image = Res.drawable.windstorm,
            onClick = { onEvent(SkillsBarEvent.WINDSTORM) }
        )
        SkillImageAnim(
            modifier = Modifier.size(60.dp),
            isButtonEnabled = isButtonEnabled,
            image = Res.drawable.earthqueq,
            onClick = { onEvent(SkillsBarEvent.EARTHQUAKE) }
        )
        SkillWrathOfFortune(
            modifier = Modifier.size(60.dp),
            isButtonEnabled = isButtonEnabled,
            useCount = scrollCount,
            onClick = { onEvent(SkillsBarEvent.WRATH_OF_FORTUNE) }
        )
    }
}

@Composable
fun SkillWrathOfFortune(
    modifier: Modifier,
    isButtonEnabled: Boolean,
    useCount: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable(enabled = isButtonEnabled) {
            onClick()
        }
    ) {
        Image(
            painterResource(Res.drawable.warht_of_good),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = useCount.toString(),
            color = Color.White,
            style = getTypography().h1,
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 1.dp, end = 4.dp)
        )
    }
}

@Composable
fun SkillImageAnim(
    modifier: Modifier,
    isButtonEnabled: Boolean,
    duration: Int = 15000,
    image: DrawableResource,
    onClick: () -> Unit
) {
    var targetPath by remember { mutableStateOf(0f) }
    val coldoun = animateFloatAsState(
        targetValue = targetPath,
        animationSpec = tween(
            durationMillis = duration
        ),
        finishedListener = {
            targetPath = 0f
        }
    )
    Box(
        modifier = modifier.clickable(enabled = isButtonEnabled && coldoun.value == 0f) {
            targetPath = 1f
            onClick()
        }
    ) {
        Image(
            painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(60.dp)
        )
        if (coldoun.value != 0f) {
            LinearProgressIndicator(
                progress = coldoun.value,
                backgroundColor = Color.Transparent,
                modifier = Modifier.size(60.dp).rotate(-90f),
                color = Color.Red.copy(alpha = 0.5f)
            )
        }
    }
}


