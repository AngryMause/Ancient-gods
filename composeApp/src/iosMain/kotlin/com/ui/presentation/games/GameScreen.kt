package com.ui.presentation.games

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.data.util.getCurrentTimeInMilliSeconds
import com.them.typography.getTypography
import com.ui.elements.BackButton
import com.ui.elements.OutlinedTextImageButton
import com.ui.elements.game.EnemyBox
import com.ui.elements.game.GameTopElements
import com.ui.elements.game.GoodsFortune
import com.ui.elements.game.LoseGameScreen
import com.ui.elements.game.ShopScreen
import com.ui.elements.game.SkillsBar
import com.ui.elements.game.WinningRoundScreenScreen
import com.ui.elements.state.EndAlertButtonState
import com.ui.elements.state.GameState
import com.ui.elements.state.SkillsBarEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.game_fon
import zeustheolympiandefender.composeapp.generated.resources.start_wave
import kotlin.math.roundToInt

@OptIn(KoinExperimentalAPI::class, ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(onBack: () -> Unit) {
    val viewModel = koinViewModel<GameViewModel>()
    val gameStateFLow = viewModel.gameState.collectAsState()
    val monsterListFLow = viewModel.monsterListFLow.collectAsState()
    val topBarFlow = viewModel.tobBarFlow.collectAsState()
    val pointFlow = viewModel.getPoint.collectAsState()
    val localWindowInfo = LocalWindowInfo.current
    val shake = remember { Animatable(0f) }
    var trigger by remember { mutableStateOf(0L) }
    var isShowWrathOfFortune by remember { mutableStateOf(false) }
    var onShowShopScreen by remember { mutableStateOf(true) }
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(trigger) {
        if (trigger != 0L) {
            for (i in 0..10) {
                when (i % 2) {
                    0 -> shake.animateTo(5f, spring(stiffness = 100_000f))
                    else -> shake.animateTo(-5f, spring(stiffness = 100_000f))
                }
            }
            shake.animateTo(0f)
        }
    }
    LaunchedEffect(localWindowInfo.containerSize.width) {
        if (localWindowInfo.containerSize.width < localWindowInfo.containerSize.height) {
            val screenSize =
                IntSize(localWindowInfo.containerSize.width, localWindowInfo.containerSize.height)
            viewModel.getScreenSize(screenSize)
        }
    }

    Box(
        Modifier.fillMaxSize()
            .paint(painterResource(Res.drawable.game_fon), contentScale = ContentScale.FillBounds),
    ) {
        Box(
            Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GameTopElements(
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    point = pointFlow.value,
                    left = topBarFlow.value.enemyLeft,
                    round = topBarFlow.value.currentRound,
                    lifeCount = topBarFlow.value.lifeCount
                )
            }

            when (gameStateFLow.value) {
                is GameState.StartGame -> {
                    OutlinedTextImageButton(
                        text = stringResource(Res.string.start_wave),
                        textAlign = TextAlign.Center,
                        style = getTypography().h2,
                        backgroundPadding = PaddingValues(
                            start = 14.dp,
                            end = 14.dp,
                            bottom = 4.dp
                        ),
                        modifier = Modifier.align(Alignment.Center)
                            .clickable {
                                viewModel.setGameState(GameState.PLAYING)
                                viewModel.startGame()
                            }
                    )
                }

                is GameState.PLAYING -> {
                    monsterListFLow.value.forEachIndexed { index, monster ->

                        EnemyBox(
                            modifier = Modifier.size((monster.size + 20).dp).offset {
                                if (trigger != 0L) IntOffset(
                                    monster.offset.x + shake.value.roundToInt(),
                                    y = monster.offset.y
                                ) else {
                                    monster.offset
                                }
                            },
                            monsterModel = monster,
                            onClicked = {
                                viewModel.onEnemyClicked(index)
                            }
                        )

                    }
                }

                is GameState.GameOver -> {
                    LoseGameScreen(
                        modifier = Modifier,
                        maxRound = (gameStateFLow.value as GameState.GameOver).maxRound,
                        onEvent = {
                            when (it) {
                                EndAlertButtonState.HOME -> {
                                    onBack()
                                }

                                EndAlertButtonState.RESTART -> {
                                    viewModel.restartEnemy()
                                }

                                else -> Unit
                            }
                        }
                    )
                }

                is GameState.GameWin -> {
                    viewModel.updatePoint()
                    WinningRoundScreenScreen(
                        modifier = Modifier,
                        onEvent = {
                            when (it) {
                                EndAlertButtonState.HOME -> {
                                    onBack()
                                }

                                EndAlertButtonState.RESTART -> {
                                    viewModel.nextRound()
                                }

                                EndAlertButtonState.Shop -> {
                                    viewModel.updatePoint()
                                    onShowShopScreen = true
                                }
                            }
                        },
                        point = (gameStateFLow.value as GameState.GameWin).point
                    )
                }
            }

            if (!isShowWrathOfFortune) {
                SkillsBar(
                    Modifier.fillMaxWidth(0.9f).align(Alignment.BottomCenter)
                        .padding(bottom = 14.dp),
                    scrollCount = topBarFlow.value.scrollCount,
                    isButtonEnabled = !isShowWrathOfFortune && gameStateFLow.value == GameState.PLAYING,
                    onEvent = {
                        if (it == SkillsBarEvent.EARTHQUAKE) {
                            trigger = getCurrentTimeInMilliSeconds()
                            viewModel.onSkillClicked(it) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        }
                        if (it == SkillsBarEvent.WRATH_OF_FORTUNE) {
                            if (topBarFlow.value.scrollCount != 0) {
                                topBarFlow.value.scrollCount -= 1
                                isShowWrathOfFortune = true
                            }
                        }
                        viewModel.onSkillClicked(it) {}
                    })
            }
            if (onShowShopScreen) {
                ShopScreen(
                    modifier = Modifier.fillMaxSize(),
                    point = pointFlow.value,
                    onClose = {
                        if (it == SkillsBarEvent.ARFA) {
                            onShowShopScreen = false
                        }
                        viewModel.onSkillBuy(it)
                        viewModel.updatePoint()
                    }
                )
            }
            if (isShowWrathOfFortune && gameStateFLow.value == GameState.PLAYING) {
                GoodsFortune(Modifier, onHide = {
                    viewModel.onWrathOfFortuneClicked()
                    viewModel.onSkillClicked(it) {}
                    isShowWrathOfFortune = false
                }, scrollCount = topBarFlow.value.scrollCount)
            }
        }
        if (gameStateFLow.value == GameState.PLAYING) {
            BackButton(
                modifier = Modifier.padding(
                    14.dp
                ).size(30.dp).align(Alignment.TopStart), onBack
            )
        }
    }

}






