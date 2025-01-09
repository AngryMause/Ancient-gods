package com.ui.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.them.typography.getTypography
import com.ui.elements.OutlinedTextImageButton
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.icon_hydra
import zeustheolympiandefender.composeapp.generated.resources.icon_meduse
import zeustheolympiandefender.composeapp.generated.resources.icon_minotaure
import zeustheolympiandefender.composeapp.generated.resources.progree_bar_image
import zeustheolympiandefender.composeapp.generated.resources.splash_fon
import zeustheolympiandefender.composeapp.generated.resources.zeuse


@OptIn(KoinExperimentalAPI::class)
@Composable
fun SplashScreen(onScreenOpen: () -> Unit) {
    var isStart by remember { mutableStateOf(false) }
    var isShowMeduza by remember { mutableStateOf(false) }
    var isShowMinotaur by remember { mutableStateOf(false) }
    var isShowGidra by remember { mutableStateOf(false) }
    var isZeusShow by remember { mutableStateOf(false) }


    val progress = animateFloatAsState(
        if (isStart) 100f else 0f,
        animationSpec = tween(durationMillis = 3000, delayMillis = 500),
        finishedListener = {
            onScreenOpen()
//            isShowMinotaur = false
//            isShowMeduza = false
//            isShowGidra = false

        }
    )

    LaunchedEffect(progress.value) {
        isZeusShow = true
        if (progress.value == 0f) {
            isStart = true
        }
        if (progress.value == 100f) {
            isStart = false
        }
        if (progress.value in 29f..33f) {
            isShowMinotaur = true
        }
        if (progress.value in 50f..55f) {
            isShowMeduza = true
        }
        if (progress.value in 70f..73f) {
            isShowGidra = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().paint(
            painterResource(Res.drawable.splash_fon),
            contentScale = ContentScale.FillBounds
        ).windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        AnimatedVisibility(
            isShowMeduza,
            modifier = Modifier.padding(40.dp).fillMaxWidth(0.4f).aspectRatio(1f)
                .align(Alignment.TopStart)
        ) {
            Image(
                painterResource(Res.drawable.icon_meduse),
                null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        AnimatedVisibility(
            isShowGidra,
            modifier = Modifier.padding(40.dp).fillMaxWidth(0.4f).aspectRatio(1f)
                .align(Alignment.TopEnd)
        ) {
            Image(
                painterResource(Res.drawable.icon_hydra),
                null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        AnimatedVisibility(
            isShowMinotaur,
            modifier = Modifier.fillMaxWidth(0.4f).aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            Image(
                painterResource(Res.drawable.icon_minotaure),
                null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        AnimatedVisibility(
            isZeusShow,
            modifier = Modifier.padding(bottom = 40.dp).fillMaxWidth(0.6f).aspectRatio(1f)
                .align(Alignment.BottomCenter)
                .align(Alignment.Center),
            enter = slideInVertically(animationSpec = tween(2500)) {
                it
            },
        ) {
            Image(
                painterResource(Res.drawable.zeuse),
                null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }


        Column(
            modifier = Modifier.padding(bottom = 30.dp).fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextImageButton(
                text = "Loading",
                isBackgriundSHow = false,
                textAlign = TextAlign.Center,
                style = getTypography().h1.copy(fontSize = 60.sp)
            )
            LinearProgressIndicator(
                color = Color(0xFF2B05FF),
                modifier = Modifier.height(30.dp).paint(
                    painterResource(Res.drawable.progree_bar_image),
                    contentScale = ContentScale.FillBounds
                ).padding(horizontal = 10.dp, vertical = 6.dp),
                strokeCap = StrokeCap.Round
            )

        }
    }
}