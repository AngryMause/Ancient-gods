package com.ui.elements.about

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.them.colors.BackButtonBackground
import com.them.colors.GradientSkillYellowOrange
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.earthqueq
import zeustheolympiandefender.composeapp.generated.resources.label_wrath_of_fortune
import zeustheolympiandefender.composeapp.generated.resources.skill_earthquake
import zeustheolympiandefender.composeapp.generated.resources.skill_earthquake_description
import zeustheolympiandefender.composeapp.generated.resources.skill_thunder
import zeustheolympiandefender.composeapp.generated.resources.skill_thunder_description
import zeustheolympiandefender.composeapp.generated.resources.skill_windstorm
import zeustheolympiandefender.composeapp.generated.resources.skill_windstorm_description
import zeustheolympiandefender.composeapp.generated.resources.skill_wrath_of_fortune_description
import zeustheolympiandefender.composeapp.generated.resources.tunder
import zeustheolympiandefender.composeapp.generated.resources.warht_of_good
import zeustheolympiandefender.composeapp.generated.resources.windstorm

@Composable
fun SkillsDescription(modifier: Modifier, isSkillsShow: Boolean, onClick: () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        isSkillsShow,
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
        Column {
            Icon(
                Icons.Default.Close,
                null,
                tint = Color.White,
                modifier = Modifier.size(30.dp).align(Alignment.End).clip(CircleShape)
                    .background(brush = BackButtonBackground)
                    .clickable { onClick() }
            )
            EnemyScreen(
                modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),

                list = skillList
            )
        }
    }
}


private val skillList = listOf(
    EnemyDescriptionModel(
        image = Res.drawable.tunder,
        title = Res.string.skill_thunder,
        description = Res.string.skill_thunder_description
    ),
    EnemyDescriptionModel(
        image = Res.drawable.windstorm,
        title = Res.string.skill_windstorm,
        description = Res.string.skill_windstorm_description
    ),
    EnemyDescriptionModel(
        image = Res.drawable.earthqueq,
        title = Res.string.skill_earthquake,
        description = Res.string.skill_earthquake_description
    ),
    EnemyDescriptionModel(
        image = Res.drawable.warht_of_good,
        title = Res.string.label_wrath_of_fortune,
        description = Res.string.skill_wrath_of_fortune_description
    ),
)