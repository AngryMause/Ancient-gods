package com.data.gamemodel

import androidx.compose.ui.unit.IntOffset
import com.ui.elements.state.MonsterType
import org.jetbrains.compose.resources.DrawableResource

data class EnemyModel(
    val image: DrawableResource,
    val monsterType: MonsterType,
    val health: Int,
    var isThunderClicked: Boolean=false,
    val enemyVelocity: Int,
    val size: Int,
    var offset: IntOffset
) {
    companion object {
        fun updateHealth(health: MonsterType) = when (health) {
            MonsterType.MINOTAUR -> 1
            MonsterType.MEDUZA -> 2
            MonsterType.Hydra -> 3
        }
    }
}



