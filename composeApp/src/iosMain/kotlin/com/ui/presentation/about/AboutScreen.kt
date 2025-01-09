package com.ui.presentation.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.them.typography.getTypography
import com.ui.elements.BackButton
import com.ui.elements.OutlinedTextImageButton
import com.ui.elements.about.AboutDescription
import com.ui.elements.about.EnemyDescription
import com.ui.elements.about.SkillsDescription
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.about
import zeustheolympiandefender.composeapp.generated.resources.enemy
import zeustheolympiandefender.composeapp.generated.resources.menu_fon
import zeustheolympiandefender.composeapp.generated.resources.skills

sealed class AboutScreenEvent {
    data object Enemy : AboutScreenEvent()
    data object Skills : AboutScreenEvent()
    data object AboutGame : AboutScreenEvent()
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    var isEnemyShow by remember { mutableStateOf(false) }
    var isSkillShow by remember { mutableStateOf(false) }
    var isAboutShow by remember { mutableStateOf(false) }

    Box(
        Modifier.fillMaxSize()
            .paint(painterResource(Res.drawable.menu_fon), contentScale = ContentScale.FillBounds),
        contentAlignment = Alignment.Center
    ) {
        BackButton(modifier = Modifier.align(Alignment.TopStart).padding(20.dp), {
            onBack()
        })
        AboutScreenButtonNavigation(modifier = Modifier.graphicsLayer {
            scaleY = isScaleButton(isEnemyShow, isSkillShow, isAboutShow)
            scaleX = isScaleButton(isEnemyShow, isSkillShow, isAboutShow)
        }.align(isChangeAlignment(isAboutShow, isEnemyShow, isSkillShow)), onEvent = {
            when (it) {
                is AboutScreenEvent.AboutGame -> {
                    isAboutShow = true
                    isEnemyShow = false
                    isSkillShow = false
                }

                is AboutScreenEvent.Enemy -> {
                    isAboutShow = false
                    isEnemyShow = true
                    isSkillShow = false
                }

                is AboutScreenEvent.Skills -> {
                    isAboutShow = false
                    isEnemyShow = false
                    isSkillShow = true
                }
            }
        })
        AboutDescription(isAboutShow, modifier = Modifier.fillMaxHeight(0.8f)
            .fillMaxWidth(0.7f).align(Alignment.CenterEnd), onClick = { isAboutShow = false })
        EnemyDescription(Modifier.fillMaxHeight(0.8f)
            .fillMaxWidth(0.7f).align(Alignment.CenterEnd),
            isEnemyShow,
            onClick = { isEnemyShow = false })
        SkillsDescription(Modifier.fillMaxHeight(0.8f)
            .fillMaxWidth(0.7f).align(Alignment.CenterEnd),
            isSkillShow,
            onClick = { isSkillShow = false })

    }
}


@Composable
fun AboutScreenButtonNavigation(modifier: Modifier, onEvent: (AboutScreenEvent) -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        OutlinedTextImageButton(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.about),
            modifier = Modifier.size(140.dp, 80.dp).clickable() {
                onEvent(AboutScreenEvent.AboutGame)
            },
            style = getTypography().h3
        )
        OutlinedTextImageButton(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.enemy),
            modifier = Modifier.size(140.dp, 80.dp).clickable {
                onEvent(AboutScreenEvent.Enemy)
            },
            style = getTypography().h3
        )
        OutlinedTextImageButton(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.skills),
            modifier = Modifier.size(140.dp, 80.dp).clickable {
                onEvent(AboutScreenEvent.Skills)
            },
            style = getTypography().h3
        )
    }
}

private fun isChangeAlignment(
    isEnemyShow: Boolean,
    isSkillShow: Boolean,
    isAboutShow: Boolean
): Alignment {
    return if (isEnemyShow || isSkillShow || isAboutShow) {
        Alignment.CenterStart
    } else {
        Alignment.Center
    }
}

private fun isScaleButton(
    isEnemyShow: Boolean,
    isSkillShow: Boolean,
    isAboutShow: Boolean
): Float {
    return if (isEnemyShow || isSkillShow || isAboutShow) {
        0.5f
    } else {
        1f
    }
}