package com.ui.elements.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ui.elements.state.SkillsBarEvent
import com.them.typography.getTypography
import com.ui.elements.CloseButton
import com.ui.elements.OutlinedTextImageButton
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.earthqueq
import zeustheolympiandefender.composeapp.generated.resources.tunder
import zeustheolympiandefender.composeapp.generated.resources.warht_of_good
import zeustheolympiandefender.composeapp.generated.resources.windstorm
import zeustheolympiandefender.composeapp.generated.resources.label_wrath_of_fortune
import zeustheolympiandefender.composeapp.generated.resources.restore_time
import zeustheolympiandefender.composeapp.generated.resources.shop_back
import zeustheolympiandefender.composeapp.generated.resources.skill_earthquake
import zeustheolympiandefender.composeapp.generated.resources.skill_thunder
import zeustheolympiandefender.composeapp.generated.resources.skill_windstorm
import zeustheolympiandefender.composeapp.generated.resources.wrath_of_fortune_plus_one

@Composable
fun ShopScreen(modifier: Modifier, point: Int, onClose: (SkillsBarEvent) -> Unit) {
    val verticalScrollState = rememberScrollState()
    Box(
        modifier = modifier.background(color = Color.Unspecified.copy(alpha = 0.6f)),
    )
    {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(verticalScrollState),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(254.dp).padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ShopScreenItem(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    image = Res.drawable.tunder,
                    skillname = Res.string.skill_thunder,
                    byDescription = Res.string.restore_time,
                    isBuy = point >= 100,
                    price = 100, onClose = {
                        onClose(SkillsBarEvent.THUNDER)
                    }
                )
                ShopScreenItem(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    image = Res.drawable.windstorm,
                    isBuy = point >= 100,
                    skillname = Res.string.skill_windstorm,
                    byDescription = Res.string.restore_time,
                    price = 100, onClose = {
                        onClose(SkillsBarEvent.WINDSTORM)
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().height(254.dp).padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ShopScreenItem(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    image = Res.drawable.earthqueq,
                    skillname = Res.string.skill_earthquake,
                    isBuy = point >= 100,
                    byDescription = Res.string.restore_time,
                    price = 100, onClose = {
                        onClose(SkillsBarEvent.EARTHQUAKE)
                    }
                )
                ShopScreenItem(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    image = Res.drawable.warht_of_good,
                    byDescription = Res.string.wrath_of_fortune_plus_one,
                    isBuy = point >= 50,
                    skillname = Res.string.label_wrath_of_fortune,
                    price = 50, onClose = {
                        onClose(SkillsBarEvent.WRATH_OF_FORTUNE)
                    }
                )
            }

        }
        CloseButton(
            Modifier.padding(top = 30.dp, start = 16.dp).align(Alignment.TopStart)
        ) {
            onClose(SkillsBarEvent.ARFA)
        }
    }
}


@Composable
fun ShopScreenItem(
    modifier: Modifier,
    image: DrawableResource,
    skillname: StringResource,
    byDescription: StringResource,
    price: Int,
    isBuy: Boolean,
    onClose: (Int) -> Unit
) {
    Column(
        modifier = modifier.paint(
            painterResource(Res.drawable.shop_back),
            contentScale = ContentScale.FillBounds
        ).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(stringResource(skillname), style = getTypography().h4, color = Color.White)
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(stringResource(byDescription), style = getTypography().h5, color = Color.White)
        OutlinedTextImageButton(
            text = price.toString(),
            textAlign = TextAlign.Center,
            style = getTypography().h5,
            isColorFilterEnabled = !isBuy,
            modifier = modifier.size(80.dp, 40.dp).then(
                Modifier.clickable(
                    enabled = isBuy
                ) { onClose(price) }
            )
        )
    }
}






