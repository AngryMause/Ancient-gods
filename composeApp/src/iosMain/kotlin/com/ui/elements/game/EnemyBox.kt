package com.ui.elements.game


import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.data.gamemodel.EnemyModel
import org.jetbrains.compose.resources.painterResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.thunderbolt

@Composable
fun EnemyBox(
    modifier: Modifier,
    monsterModel: EnemyModel,
    onClicked: () -> Unit
) {
    var enemyLifeSection by remember { mutableStateOf<List<Int>>(emptyList()) }
    LaunchedEffect(monsterModel.health) {
        val list = mutableListOf<Int>()
        for (i in 0 until monsterModel.health) {
            list.add(i)
        }
        enemyLifeSection = list
    }
    var isThunderbolt by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (monsterModel.isThunderClicked) 1f else 0.3f,
        animationSpec = repeatable(
            3,
            animation = tween(100, 0, easing = EaseOutBounce)
        ),
        finishedListener = {
            isThunderbolt = false
        }
    )
    LaunchedEffect(monsterModel.isThunderClicked) {
        isThunderbolt = monsterModel.isThunderClicked
    }
    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                1.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            enemyLifeSection.forEach { _ ->
                Spacer(
                    modifier = Modifier.size(width = 10.dp, height = 6.dp)
                        .border(2.dp, color = Color.Black)
                        .background(Color.Red, shape = RoundedCornerShape(20.dp))
                )
            }
        }

        Image(
            painterResource(monsterModel.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.align(Alignment.Center).size(monsterModel.size.dp).clip(CircleShape)
                .clickable {
                    if (enemyLifeSection.size > 1) {
                        val newList = enemyLifeSection.toMutableList().apply {
                            removeAt(this.last())
                        }
                        enemyLifeSection = newList
                    }
                    onClicked()
                },
        )
        if (isThunderbolt) {
            Image(
                painterResource(Res.drawable.thunderbolt), contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.align(Alignment.Center).size(monsterModel.size.dp).rotate(-3f)
                    .graphicsLayer {
                        alpha = alphaAnimation.value
                    }
            )
        }
    }
}
