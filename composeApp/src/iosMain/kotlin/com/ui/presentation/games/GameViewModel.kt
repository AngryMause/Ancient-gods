package com.ui.presentation.games

import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repository.GameRepository
import com.ui.elements.state.GameState
import com.ui.elements.state.SkillsBarEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameRepository: GameRepository,
    private val dispatcherIo: CoroutineDispatcher,
) : ViewModel() {

    val gameState = gameRepository.gameState
    val monsterListFLow = gameRepository.monsterListFlow
    val tobBarFlow = gameRepository.topBarModelFlow
    private val _getPoint = MutableStateFlow(0)
    val getPoint = _getPoint.asStateFlow()

    init {
        updatePoint()
    }

    fun updatePoint() {
        viewModelScope.launch(dispatcherIo) {
            delay(100)
            _getPoint.emit(tobBarFlow.value.points)
        }
    }

    fun getScreenSize(screenSize: IntSize) {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.getScreenSize(screenSize)
        }
    }

    fun onEnemyClicked(index: Int) {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.onEnemyClicked(index)
        }
    }


    fun setGameState(gameState: GameState) {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.setGameState(gameState)
        }
    }

    fun startGame() {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.startGame()
        }
    }

    fun restartEnemy() {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.restartEnemy()
            startGame()
        }
    }

    fun onWrathOfFortuneClicked() {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.onWrathOfFortuneClicked()
        }
    }

    fun nextRound() {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.nextRound()
        }
    }

    fun onSkillBuy(skillsBarEvent: SkillsBarEvent) {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.onSkillBuy(skillsBarEvent)
        }
    }

    fun onSkillClicked(skillsBarEvent: SkillsBarEvent,onDrag:()->Unit) {
        viewModelScope.launch(dispatcherIo) {
            gameRepository.onSkillClicked(skillsBarEvent,onDrag)
        }
    }
}