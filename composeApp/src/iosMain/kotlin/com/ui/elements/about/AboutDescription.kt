package com.ui.elements.about

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ui.elements.CloseButton
import com.them.colors.GradientSkillYellowOrange
import com.them.typography.getTypography
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.about_description

@Composable
fun AboutDescription(isAboutShow: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        isAboutShow,
        modifier = modifier.background(brush = GradientSkillYellowOrange),
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CloseButton(
                modifier = Modifier.size(30.dp).align(Alignment.End),
                onClick = onClick
            )
            Text(
                text = stringResource(Res.string.about_description),
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = getTypography().h4,
                modifier = Modifier.padding(6.dp),
            )
        }
    }
}