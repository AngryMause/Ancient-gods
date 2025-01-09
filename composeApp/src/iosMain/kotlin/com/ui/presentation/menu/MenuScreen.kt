package com.ui.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.them.typography.getTypography
import com.ui.elements.OutlinedTextImageButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.about
import zeustheolympiandefender.composeapp.generated.resources.menu_fon
import zeustheolympiandefender.composeapp.generated.resources.options
import zeustheolympiandefender.composeapp.generated.resources.start
import zeustheolympiandefender.composeapp.generated.resources.zeuse

sealed class MenuScreenEvent {
    data object StartGame : MenuScreenEvent()
    data object Option : MenuScreenEvent()
    data object About : MenuScreenEvent()
}

@Composable
fun MenuScreen(onScreenOpen: (MenuScreenEvent) -> Unit) {
    val screeOrientation = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize().paint(
            painterResource(Res.drawable.menu_fon),
            contentScale = ContentScale.FillBounds
        ).onSizeChanged { screenSize ->
            screeOrientation.value = screenSize.width > screenSize.height
        }
    ) {

        Column(
            modifier = Modifier.padding(bottom = 20.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(Res.drawable.zeuse),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 10.dp).align(Alignment.CenterHorizontally)
                    .size(200.dp)
                    .graphicsLayer {
                    },
                contentDescription = null
            )
            OutlinedTextImageButton(
                text = stringResource(Res.string.start),
                textAlign = TextAlign.Center,
                style = getTypography().h1,
                modifier = Modifier.fillMaxWidth(0.5f).height(80.dp)
                    .clickable { onScreenOpen(MenuScreenEvent.StartGame) }
            )

            OutlinedTextImageButton(
                text = stringResource(Res.string.about),
                textAlign = TextAlign.Center,
                style = getTypography().h1,
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.5f).height(80.dp)
                    .clickable { onScreenOpen(MenuScreenEvent.About) }
            )
            OutlinedTextImageButton(
                text = stringResource(Res.string.options),
                textAlign = TextAlign.Center,
                style = getTypography().h1,
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.5f).height(80.dp)
                    .clickable { onScreenOpen(MenuScreenEvent.Option) }
            )
        }

    }
}