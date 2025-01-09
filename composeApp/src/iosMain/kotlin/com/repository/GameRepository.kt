package com.repository

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.data.datastore.AppOptionPreferences
import com.data.gamemodel.EnemyModel
import com.data.gamemodel.TopBarModel
import com.data.media.GameAudioPlayer
import com.ui.elements.state.GameState
import com.ui.elements.state.MonsterType
import com.ui.elements.state.SkillsBarEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.icon_hydra
import zeustheolympiandefender.composeapp.generated.resources.icon_meduse
import zeustheolympiandefender.composeapp.generated.resources.icon_minotaure
import kotlin.random.Random

private const val MOVE_DOWN_STEP = 14
private const val START_ENEMY_COUNT = 30

class GameRepository(
    private val audioPlayer: GameAudioPlayer,
    private val appOptionPreferences: AppOptionPreferences,
    private val dispatcher: CoroutineDispatcher
) {
    private val _gameState = MutableStateFlow<GameState>(GameState.StartGame)
    val gameState = _gameState.asStateFlow()
    private val _topBarModelFlow = MutableStateFlow(TopBarModel.resetTopBarModel())
    val topBarModelFlow = _topBarModelFlow.asStateFlow()
    private val _monsterListFlow = MutableStateFlow(monsterList)
    val monsterListFlow = _monsterListFlow.asStateFlow()
    private var screenSize: IntSize = IntSize.Zero
    private var isSetSize: Boolean = false
    private var isEarthquake = false
    private var isWrathOfFortuneClicked = false


    private var updateVelocity: Long = 30
    suspend fun startGame() {
        setGameState(GameState.PLAYING)
        updateGameElement()
    }

    private suspend fun isSoundEnabled() = appOptionPreferences.isSoundEnabled().first()
    suspend fun setGameState(gameState: GameState) {
        _gameState.emit(gameState)
    }


    private suspend fun updateGameElement() {
        coroutineScope {
            launch(dispatcher) {
                while (_topBarModelFlow.value.lifeCount >= 1 && _topBarModelFlow.value.enemyLeft >= 1 && gameState.value == GameState.PLAYING) {
                    delay(updateVelocity)
                    if (!isEarthquake) {
                        if (!isWrathOfFortuneClicked) {
                            updateMonsterListIntOffset()
                        }
                    }
                }
                if (_topBarModelFlow.value.lifeCount <= 0) {
                    _gameState.emit(GameState.GameOver(_topBarModelFlow.value.currentRound))
                } else {
                    winGame()
                }
            }
        }
    }

    private suspend fun winGame() {
        val point =
            _topBarModelFlow.value.points + (_topBarModelFlow.value.currentRound * 10)
        _topBarModelFlow.update {
            it.copy(
                points = point,
            )
        }
        audioPlayer.playWinSound(isSoundEnabled())
        _gameState.emit(GameState.GameWin(point))
    }

    suspend fun restartEnemy() {
        _monsterListFlow.emit(monsterList)
        _topBarModelFlow.emit(TopBarModel.resetTopBarModel())
        startGame()
    }

    suspend fun nextRound() {
        if (topBarModelFlow.value.currentRound <= 15) {
            _monsterListFlow.emit(monsterList)
        } else {
            _monsterListFlow.value.forEachIndexed { index, monsterModel ->
                resetsEnemyIntOffset(index, monsterModel)
            }
        }
        _topBarModelFlow.update {
            it.copy(
                currentRound = it.currentRound + 1,
                enemyLeft = START_ENEMY_COUNT + (_topBarModelFlow.value.currentRound * 3)
            )
        }
        startGame()
    }

    suspend fun onEnemyClicked(index: Int) {
        val monsterModel = monsterListFlow.value[index]
        val new = monsterModel.copy(health = monsterModel.health - 1)
        if (new.health == 0) {
            _topBarModelFlow.emit(
                _topBarModelFlow.value.copy(
                    points = _topBarModelFlow.value.points + 2,
                    enemyLeft = _topBarModelFlow.value.enemyLeft - 1
                )
            )
            resetsEnemyIntOffset(index, monsterModel)
        } else {
            _monsterListFlow.emit(
                monsterListFlow.value.toMutableList().apply {
                    this[index] = new
                }
            )
        }
    }


    private suspend fun updateMonsterListIntOffset() {
        val droppedItemModelList = monsterListFlow.value
        for (i in droppedItemModelList.indices) {
            val singleDroppedItemModel = droppedItemModelList[i]
            if (droppedItemModelList[i].offset.y >= 100) {
                moveEnemyByY(i, singleDroppedItemModel)
            } else {
                resetMonsterPositionAfterGate(
                    index = i, singleDroppedItemModel = singleDroppedItemModel
                )
            }
        }
    }

    private suspend fun moveEnemyByY(
        index: Int,
        monsterModel: EnemyModel
    ) {
        _monsterListFlow.emit(
            monsterListFlow.value.toMutableList().apply {
                this[index] = updateItemByYUp(monsterModel)
            }
        )
    }

    private fun updateItemByYUp(monsterModel: EnemyModel): EnemyModel {
        val singleDroppedItem = monsterModel.copy(
            offset = monsterModel.offset.copy(y = monsterModel.offset.y - monsterModel.enemyVelocity)
        )
        return singleDroppedItem
    }

    private fun updateItemByYDown(monsterModel: EnemyModel): EnemyModel {
        val singleDroppedItem = monsterModel.copy(
            offset = monsterModel.offset.copy(y = monsterModel.offset.y + MOVE_DOWN_STEP)
        )
        return singleDroppedItem
    }

    private suspend fun resetMonsterPositionAfterGate(
        index: Int,
        singleDroppedItemModel: EnemyModel
    ) {
        _topBarModelFlow.value.lifeCount -= 1
        val update = singleDroppedItemModel.offset.copy(
            x = Random.nextInt(screenSize.width - (singleDroppedItemModel.size + 20)),
            y = screenSize.height
        )
        _monsterListFlow.emit(
            monsterListFlow.value.toMutableList().apply {
                this[index] = monsterListFlow.value[index].copy(
                    offset = update,
                )
            },
        )
    }


    fun getScreenSize(screenSize: IntSize) {
        if (!isSetSize) {
            this.screenSize = screenSize
            setEnemyOffSet()
            isSetSize = true
        }
    }


    private fun setEnemyOffSet() {
        if (screenSize == IntSize.Zero) return
        monsterList.forEach { monster ->
            val random = Random.nextInt(screenSize.width - (monster.size + 60))
            monster.offset = IntOffset(random, screenSize.height - 400)
        }
    }


    private suspend fun resetsEnemyIntOffset(index: Int, monsterModel: EnemyModel) {
        val update = monsterModel.offset.copy(
            x = Random.nextInt(screenSize.width - (monsterModel.size + 60)),
            y = screenSize.height - 400
        )
        _monsterListFlow.emit(
            monsterListFlow.value.toMutableList().apply {
                this[index] = monsterListFlow.value[index].copy(
                    health = EnemyModel.updateHealth(monsterModel.monsterType),
                    offset = update,
                    isThunderClicked = false
                )
            },
        )
        addNewEnemy()
    }

    private suspend fun addNewEnemy() {
        when (topBarModelFlow.value.enemyLeft) {
            25 -> {
                updateVelocity -= 1
                _monsterListFlow.emit(
                    monsterListFlow.value.toMutableList().apply {
                        add(updateEnemyList())
                    }
                )
            }

            20 -> {
                updateVelocity -= 1
                _monsterListFlow.emit(
                    monsterListFlow.value.toMutableList().apply {
                        add(updateEnemyList())
                    }
                )
            }

            16 -> {
                updateVelocity -= 1
                _monsterListFlow.emit(
                    monsterListFlow.value.toMutableList().apply {
                        add(
                            EnemyModel(
                                image = Res.drawable.icon_hydra,
                                monsterType = MonsterType.Hydra,
                                health = 3,
                                size = 60,
                                enemyVelocity = 4,
                                offset = IntOffset(
                                    x = Random.nextInt(screenSize.width - 100),
                                    y = screenSize.height + Random.nextInt(100)
                                )
                            )
                        )
                    }
                )
            }

            12 -> {
                updateVelocity -= 1
                _monsterListFlow.emit(
                    monsterListFlow.value.toMutableList().apply {
                        add(updateEnemyList())
                    }
                )
            }

            5 -> {
                updateVelocity -= 1
                _monsterListFlow.emit(
                    monsterListFlow.value.toMutableList().apply {
                        add(updateEnemyList())
                    }
                )
            }
        }

    }

    fun onWrathOfFortuneClicked(isWrathOfFortuneClicked: Boolean = false) {
        this.isWrathOfFortuneClicked = isWrathOfFortuneClicked
    }

    fun onSkillBuy(skillsBarEvent: SkillsBarEvent) {
        val currentPoint = _topBarModelFlow.value.points
        when (skillsBarEvent) {
            SkillsBarEvent.THUNDER -> {
                if (currentPoint >= 100) {
                    _topBarModelFlow.value.points -= 100
                }
            }

            SkillsBarEvent.WINDSTORM -> {
                if (currentPoint >= 100) {
                    _topBarModelFlow.value.points -= 100
                }
            }

            SkillsBarEvent.EARTHQUAKE -> {
                if (currentPoint >= 100) {
                    _topBarModelFlow.value.points -= 100
                }
            }

            SkillsBarEvent.WRATH_OF_FORTUNE -> {
                if (currentPoint >= 50) {
                    _topBarModelFlow.value.points -= 50
                    _topBarModelFlow.value.scrollCount += 1
                }
            }

            else -> Unit
        }
    }


    private suspend fun setEnemyDamageByOne(index: Int) {
        val monsterModel = monsterListFlow.value[index]

        val new = monsterModel.copy(health = monsterModel.health - 1)
        if (new.health == 0) {
            _topBarModelFlow.update {
                it.copy(
                    points = _topBarModelFlow.value.points + 2,
                    enemyLeft = _topBarModelFlow.value.enemyLeft - 1
                )
            }
            resetsEnemyIntOffset(index, monsterModel)
        } else {
            _monsterListFlow.emit(
                monsterListFlow.value.toMutableList().apply {
                    this[index] = new
                }
            )
        }
    }

    suspend fun onSkillClicked(skillsBarEvent: SkillsBarEvent,onDrag:()->Unit) {
        when (skillsBarEvent) {
            SkillsBarEvent.THUNDER -> {
                audioPlayer.thunderStrike(isSoundEnabled())
                _monsterListFlow.update { enemyList ->
                    val tempList = enemyList.toMutableList()
                    tempList.forEachIndexed { index, monsterModel ->
                        val new = monsterModel.copy(isThunderClicked = true)
                        tempList[index] = new
                    }
                    tempList
                }
                delay(1000)
                _monsterListFlow.value.forEachIndexed { index, monsterModel ->
                    if (monsterModel.health > 0 && monsterModel.offset.y < screenSize.height - 300) {
                        val new = monsterModel.copy(health = 0, isThunderClicked = true)
                        if (new.health == 0) {
                            _topBarModelFlow.emit(
                                _topBarModelFlow.value.copy(
                                    points = _topBarModelFlow.value.points + 2,
                                    enemyLeft = _topBarModelFlow.value.enemyLeft - 1
                                )
                            )
                            resetsEnemyIntOffset(index, monsterModel)
                        } else {
                            _monsterListFlow.emit(
                                monsterListFlow.value.toMutableList().apply {
                                    this[index] = new
                                }
                            )
                        }
                    }
                }
            }

            SkillsBarEvent.WINDSTORM -> {
                audioPlayer.windStormSkill(isSoundEnabled())
                var windstorm = 100
                while (windstorm >= 0) {
                    delay(30)
                    coroutineScope {
                        _monsterListFlow.value.forEachIndexed { index, monsterModel ->
                            moveEnemyByY(index = index, updateItemByYDown(monsterModel))
                        }
                    }
                    windstorm--
                }
            }

            SkillsBarEvent.EARTHQUAKE -> {
                onDrag()
                audioPlayer.earthquakeSkill(isSoundEnabled())
                isEarthquake = true
                var windstorm = 100
                while (windstorm >= 0) {
                    delay(30)
                    coroutineScope {
                        _monsterListFlow.value.forEachIndexed { index, monsterModel ->
                            if (monsterModel.offset.y < screenSize.height - 600) {
                                moveEnemyByY(index = index, updateItemByYDown(monsterModel))
                            }
                            if (windstorm == 0) {
                                setEnemyDamageByOne(index)

                            }
                        }
                    }
                    windstorm--

                }
                isEarthquake = false
            }

            SkillsBarEvent.GOLD_COIN -> {
                audioPlayer.coinsSound(isSoundEnabled())
                _topBarModelFlow.emit(
                    _topBarModelFlow.value.copy(
                        points = _topBarModelFlow.value.points + 100,
                    )
                )
            }

            SkillsBarEvent.ADD_ROLL -> {
                _topBarModelFlow.value.scrollCount += 1
            }

            SkillsBarEvent.SILVER_COIN -> {
                audioPlayer.coinsSound(isSoundEnabled())
                _topBarModelFlow.emit(
                    _topBarModelFlow.value.copy(
                        points = _topBarModelFlow.value.points + 50,
                    )
                )
            }

            SkillsBarEvent.WRATH_OF_FORTUNE -> {
                isWrathOfFortuneClicked = true
            }

            else -> {
                isWrathOfFortuneClicked = false
            }
        }
    }

    private fun updateEnemyList(): EnemyModel {
        val monsterModel = Random.nextInt(2)
        return when (monsterModel) {
            0 -> {
                EnemyModel(
                    image = Res.drawable.icon_minotaure,
                    health = 1,
                    monsterType = MonsterType.MINOTAUR,
                    enemyVelocity = 5,
                    size = 40,
                    offset = IntOffset(
                        x = Random.nextInt(screenSize.width - 100),
                        y = (screenSize.height - 400)
                    )
                )
            }

            1 -> {
                EnemyModel(
                    image = Res.drawable.icon_meduse,
                    monsterType = MonsterType.MEDUZA,
                    health = 2,
                    size = 50,
                    enemyVelocity = 7,
                    offset = IntOffset(
                        x = Random.nextInt(screenSize.width - 100),
                        y = (screenSize.height - 400)
                    )
                )
            }

            2 -> {
                EnemyModel(
                    image = Res.drawable.icon_hydra,
                    monsterType = MonsterType.Hydra,
                    health = 3,
                    size = 60,
                    enemyVelocity = 4,
                    offset = IntOffset(
                        x = Random.nextInt(screenSize.width - 100),
                        y = (screenSize.height - 400)
                    )
                )
            }

            else -> {
                EnemyModel(
                    image = Res.drawable.icon_minotaure,
                    health = 1,
                    monsterType = MonsterType.MINOTAUR,
                    size = 40,
                    enemyVelocity = 5,
                    offset = IntOffset(
                        x = Random.nextInt(screenSize.width - 100),
                        y = (screenSize.height - 400)
                    )
                )
            }
        }
    }

}


private val monsterList = mutableListOf(
    EnemyModel(
        image = Res.drawable.icon_minotaure,
        health = 1,
        monsterType = MonsterType.MINOTAUR,
        size = 40,
        enemyVelocity = 5,
        offset = IntOffset.Zero
    ),
    EnemyModel(
        image = Res.drawable.icon_minotaure,
        health = 1,
        monsterType = MonsterType.MINOTAUR,
        size = 40,
        enemyVelocity = 5,
        offset = IntOffset.Zero
    ),
    EnemyModel(
        image = Res.drawable.icon_minotaure,
        health = 1,
        monsterType = MonsterType.MINOTAUR,
        size = 40,
        enemyVelocity = 5,
        offset = IntOffset.Zero
    ),
    EnemyModel(
        image = Res.drawable.icon_meduse,
        monsterType = MonsterType.MEDUZA,
        health = 2,
        enemyVelocity = 7,
        size = 50,
        offset = IntOffset.Zero
    ),
    EnemyModel(
        image = Res.drawable.icon_hydra,
        monsterType = MonsterType.Hydra,
        health = 3,
        enemyVelocity = 4,
        size = 60,
        offset = IntOffset.Zero
    )
)


