package com.data.gamemodel


data class TopBarModel(
    var points: Int = 0,
    var enemyLeft: Int,
    val currentRound: Int,
    var lifeCount: Int,
    var scrollCount: Int = 4
) {
    companion object {
        fun resetTopBarModel() = TopBarModel(
            points = 100,
            enemyLeft = 30,
            currentRound = 1,
            lifeCount = 50
        )
    }
}