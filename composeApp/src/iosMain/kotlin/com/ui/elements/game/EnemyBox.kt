package com.ui.elements.game


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.data.gamemodel.EnemyModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnemyBox(modifier: Modifier, monsterModel: EnemyModel, onClicked: () -> Unit) {
    var enemyLifeSection by remember { mutableStateOf<List<Int>>(emptyList()) }
    LaunchedEffect(monsterModel.health) {
        val list = mutableListOf<Int>()
        for (i in 0 until monsterModel.health) {
            list.add(i)
        }
        enemyLifeSection = list
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.size(monsterModel.size.dp).clip(CircleShape).clickable {
                if (enemyLifeSection.size > 1) {
                    val newList = enemyLifeSection.toMutableList().apply {
                        removeAt(this.last())
                    }
                    enemyLifeSection = newList
                }
                onClicked()
            },
        )
    }
}

 //   EnemyBox(
//                        modifier = Modifier.size((monster.size + 20).dp).offset {
//                            if (trigger != 0L) IntOffset(
//                                monster.offset.x + shake.value.roundToInt(),
//                                y = monster.offset.y
//                            ) else {
//                                monster.offset
//                            }
//
//                        },
//                        monsterModel = monster,
//                        onClicked = {
//                            viewModel.onEnemyClicked(index)
//                        }
//                    )

