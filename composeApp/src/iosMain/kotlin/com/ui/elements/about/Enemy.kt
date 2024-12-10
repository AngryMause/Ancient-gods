package com.ui.elements.about

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ui.elements.CloseButton
import com.them.colors.GradientSkillYellowOrange
import com.them.typography.getTypography
import com.ui.elements.OutlinedTextImageButton
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.icon_hydra
import zeustheolympiandefender.composeapp.generated.resources.icon_meduse
import zeustheolympiandefender.composeapp.generated.resources.icon_minotaure
import zeustheolympiandefender.composeapp.generated.resources.enemy_hydra
import zeustheolympiandefender.composeapp.generated.resources.enemy_meduza
import zeustheolympiandefender.composeapp.generated.resources.enemy_minotaur
import zeustheolympiandefender.composeapp.generated.resources.hydra_description
import zeustheolympiandefender.composeapp.generated.resources.meduza_description
import zeustheolympiandefender.composeapp.generated.resources.minotaur_description


@Composable
fun EnemyDescription(modifier: Modifier, isEnemyShow: Boolean, onClick: () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        isEnemyShow,
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
            CloseButton(
                modifier = Modifier.size(30.dp).align(Alignment.End),
                onClick = onClick
            )
            EnemyScreen(
                modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
fun EnemyScreen(modifier: Modifier, list: List<EnemyDescriptionModel> = enemyList) {
    Box(
        modifier = modifier.background(brush = GradientSkillYellowOrange),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn() {
            itemsIndexed(list) { _, item ->
                EnemyScreen(
                    enemyImage = item.image,
                    text = stringResource(item.description),
                    name = stringResource(item.title),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}


val enemyList = listOf(
    EnemyDescriptionModel(
        Res.drawable.icon_minotaure,
        Res.string.enemy_minotaur,
        Res.string.minotaur_description
    ),
    EnemyDescriptionModel(
        Res.drawable.icon_meduse,
        Res.string.enemy_meduza,
        Res.string.meduza_description
    ),
    EnemyDescriptionModel(
        Res.drawable.icon_hydra,
        Res.string.enemy_hydra,
        Res.string.hydra_description
    )
)

data class EnemyDescriptionModel(
    val image: DrawableResource,
    val title: StringResource,
    val description: StringResource
)


@Composable
fun EnemyScreen(
    enemyImage: DrawableResource,
    modifier: Modifier,
    name: String,
    text: String,
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)

            ) {
                OutlinedTextImageButton(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = getTypography().h4,
                    isBackgriundSHow = false,
                    modifier = Modifier.padding(6.dp),
                )
                Image(
                    painterResource(enemyImage),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = getTypography().h5,
                color = Color.Black,
                modifier = Modifier.weight(1f).height(200.dp).verticalScroll(rememberScrollState())
                    .padding(10.dp),
            )
        }
        Spacer(
            modifier = Modifier.background(Color.Gray).align(Alignment.BottomCenter).fillMaxWidth()
                .size(8.dp)
        )

    }
}