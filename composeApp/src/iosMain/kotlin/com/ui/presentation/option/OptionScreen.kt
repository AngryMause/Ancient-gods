package com.ui.presentation.option

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.them.colors.ColorOff
import com.them.colors.ColorOn
import com.them.typography.getTypography
import com.ui.elements.BackButton
import com.ui.elements.OutlinedTextImageButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.frame_settings
import zeustheolympiandefender.composeapp.generated.resources.menu_fon
import zeustheolympiandefender.composeapp.generated.resources.music
import zeustheolympiandefender.composeapp.generated.resources.off
import zeustheolympiandefender.composeapp.generated.resources.on
import zeustheolympiandefender.composeapp.generated.resources.options
import zeustheolympiandefender.composeapp.generated.resources.sound

@OptIn(KoinExperimentalAPI::class)
@Composable
fun OptionScreen(
    onMove: () -> Unit
) {
    val viewModel = koinViewModel<OptionViewModel>()
    val isEnabled = viewModel.isSoundEnabled.collectAsState(true)
    val isMusicEnabled = viewModel.isMusicEnabled.collectAsState(true)

    Box(
        modifier = Modifier.fillMaxSize().paint(
            painter = painterResource(Res.drawable.menu_fon),
            contentScale = ContentScale.FillBounds
        ).background(color = Color.Unspecified.copy(0.6f))
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextImageButton(
                text = stringResource(Res.string.options),
                style = getTypography().h1.copy(fontSize = 70.sp),
                isBackgriundSHow = false
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
                    .paint(
                        painterResource(Res.drawable.frame_settings),
                        contentScale = ContentScale.FillBounds
                    ).padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedTextImageButton(
                        text = stringResource(Res.string.sound),
                        style = TextStyle(
                            fontSize = 30.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                        ),
                        isBackgriundSHow = false,
                    )
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isEnabled.value) ColorOn else ColorOff)
                            .width(60.dp)
                            .clickable {
                                viewModel.changeSoundEnabled(!isEnabled.value)
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(if (isEnabled.value) Res.string.on else Res.string.off),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }

                }
                Spacer(modifier = Modifier.size(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedTextImageButton(
                        text = stringResource(Res.string.music),
                        style = TextStyle(
                            fontSize = 30.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                        ),
                        isBackgriundSHow = false,
                    )
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isMusicEnabled.value) ColorOn else ColorOff)
                            .width(60.dp)
                            .clickable {
                                viewModel.changeMusicEnabled(!isMusicEnabled.value)
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(if (isMusicEnabled.value) Res.string.on else Res.string.off),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }

                }
            }

        }
        BackButton(modifier = Modifier.padding(20.dp), {
            onMove()
        })
    }
}