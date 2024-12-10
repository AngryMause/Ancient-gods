package com.ui.elements.state

sealed class GameState {
    data object PLAYING : GameState()
    data object StartGame : GameState()
    data class GameOver(val maxRound: Int) : GameState()
    data class GameWin(val point: Int) : GameState()
}