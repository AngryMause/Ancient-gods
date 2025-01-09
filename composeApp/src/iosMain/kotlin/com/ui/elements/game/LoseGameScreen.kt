package com.ui.elements.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.them.typography.getTypography
import com.ui.elements.OutlinedTextImageButton
import com.ui.elements.state.EndAlertButtonState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.frame_settings
import zeustheolympiandefender.composeapp.generated.resources.game_over_text
import zeustheolympiandefender.composeapp.generated.resources.home
import zeustheolympiandefender.composeapp.generated.resources.restart
import zeustheolympiandefender.composeapp.generated.resources.round

@Composable
fun LoseGameScreen(modifier: Modifier, maxRound: Int, onEvent: (EndAlertButtonState) -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.Unspecified.copy(0.5f)),
    ) {
        Image(
            painter = painterResource(Res.drawable.game_over_text),
            contentDescription = null,
            modifier = Modifier.padding(top = 30.dp).fillMaxWidth(0.8f).fillMaxHeight(0.2f)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = modifier.fillMaxWidth(0.8f).fillMaxHeight(0.4f).paint(
                painterResource(Res.drawable.frame_settings),
                contentScale = ContentScale.FillBounds
            ).align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextImageButton(
                text = stringResource(Res.string.round, maxRound),
                style = getTypography().h1,
                isBackgriundSHow = false,
                textAlign = TextAlign.Center
            )
            OutlinedTextImageButton(
                text = stringResource(Res.string.home),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.2f)
                    .clickable { onEvent(EndAlertButtonState.HOME) },
                style = getTypography().h2,
            )
            OutlinedTextImageButton(
                text = stringResource(Res.string.restart),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.3f)
                    .clickable { onEvent(EndAlertButtonState.RESTART) },
                style = getTypography().h2,
            )
        }
    }
}
