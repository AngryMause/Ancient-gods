package com.ui.elements.game

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ui.elements.CloseButton
import com.ui.elements.state.SkillsBarEvent
import com.them.typography.getTypography
import com.ui.elements.OutlinedTextImageButton
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zeustheolympiandefender.composeapp.generated.resources.Res
import zeustheolympiandefender.composeapp.generated.resources.arfa
import zeustheolympiandefender.composeapp.generated.resources.coin
import zeustheolympiandefender.composeapp.generated.resources.coin_iron
import zeustheolympiandefender.composeapp.generated.resources.earthqueq
import zeustheolympiandefender.composeapp.generated.resources.helm
import zeustheolympiandefender.composeapp.generated.resources.label_wrath_of_fortune
import zeustheolympiandefender.composeapp.generated.resources.left
import zeustheolympiandefender.composeapp.generated.resources.row_left
import zeustheolympiandefender.composeapp.generated.resources.row_reigth
import zeustheolympiandefender.composeapp.generated.resources.tunder
import zeustheolympiandefender.composeapp.generated.resources.warht_of_good
import zeustheolympiandefender.composeapp.generated.resources.warth_of_good_frame
import zeustheolympiandefender.composeapp.generated.resources.windstorm

@Composable
fun GoodsFortune(modifier: Modifier, scrollCount: Int, onHide: (SkillsBarEvent) -> Unit) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val largList = remember { mutableStateOf<List<WrathOfFortuneModel>>(emptyList()) }

    LaunchedEffect(largList) {
        if (largList.value.isEmpty()) {
            largList.value = largeFortuneList.shuffled()
        }
    }
    var isScrolling by remember { mutableStateOf(true) }
    var goodOfFortune by remember { mutableStateOf(SkillsBarEvent.WRATH_OF_FORTUNE) }
    var selectedIndex by remember { mutableStateOf(0) }
    var scaleAnimationValue by remember { mutableStateOf(1f) }
    val scaleAnimation = animateFloatAsState(
        targetValue = scaleAnimationValue,
        animationSpec = repeatable(
            3,
            animation = tween(durationMillis = 400),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            onHide(goodOfFortune)
        }
    )
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = Color.Unspecified.copy(alpha = 0.6f))
    ) {

        CloseButton(Modifier.size(30.dp).align(Alignment.TopEnd).padding(3.dp)) {
            onHide(SkillsBarEvent.WRATH_OF_FORTUNE)
        }

        OutlinedTextImageButton(
            text = stringResource(Res.string.label_wrath_of_fortune),
            style = getTypography().h1,
            isBackgriundSHow = false,
            modifier = Modifier.align(Alignment.TopCenter).padding(20.dp)
        )
        OutlinedTextImageButton(
            text = stringResource(Res.string.left, scrollCount),
            style = getTypography().h4,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 70.dp, end = 20.dp)
                .size(width = 80.dp, height = 40.dp)
        )
        Text(
            text = "",
            modifier = Modifier.background(color = Color.Black).fillMaxWidth(0.5f)
                .fillMaxHeight(0.45f).align(Alignment.Center)
        )
        Image(
            painter = painterResource(Res.drawable.warth_of_good_frame),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight(0.55f)
                .align(Alignment.Center)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(0.4f).align(Alignment.Center),
            state = scrollState,
            userScrollEnabled = false,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            itemsIndexed(largList.value) { index, item ->
                Image(
                    painter = painterResource(item.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(90.dp).align(Alignment.Center).graphicsLayer {
                        if (index == selectedIndex) {
                            scaleX = scaleAnimation.value
                            scaleY = scaleAnimation.value
                        }
                    }
                )
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(9.dp)
                )
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(2.dp).background(color = Color.Gray)
                )
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(9.dp)
                )
            }
        }
        Image(
            painter = painterResource(Res.drawable.row_reigth),
            contentDescription = null,
            modifier = Modifier.size(50.dp).align(Alignment.CenterStart)
        )
        Image(
            painter = painterResource(Res.drawable.row_left),
            contentDescription = null,
            modifier = Modifier.size(50.dp).align(Alignment.CenterEnd)
        )
        OutlinedTextImageButton(
            modifier = Modifier.padding(bottom = 50.dp).align(Alignment.BottomCenter)
                .size(width = 100.dp, height = 50.dp)
                .clickable(
                    enabled = isScrolling,
                ) {
                    isScrolling = false
                    val random = (0..<largList.value.size).random()
                    scope.launch {
                        with(scrollState.layoutInfo) {
                            val itemSize = visibleItemsInfo.first().size
                            scrollState.animateScrollToItem(random, -(itemSize))
                        }
                        scaleAnimationValue = 1.2f
                        goodOfFortune = largList.value[random].skillName
                        selectedIndex = random
                    }
                },
            style = getTypography().h1,
            text = "Start",
        )
    }
}

private val fortuneList = mutableListOf(
    WrathOfFortuneModel(Res.drawable.coin_iron, SkillsBarEvent.SILVER_COIN),
    WrathOfFortuneModel(Res.drawable.arfa, SkillsBarEvent.ARFA),
    WrathOfFortuneModel(Res.drawable.warht_of_good, SkillsBarEvent.ADD_ROLL),
    WrathOfFortuneModel(Res.drawable.arfa, SkillsBarEvent.ARFA),
    WrathOfFortuneModel(Res.drawable.tunder, SkillsBarEvent.THUNDER),
    WrathOfFortuneModel(Res.drawable.helm, SkillsBarEvent.HELM),
    WrathOfFortuneModel(Res.drawable.coin_iron, SkillsBarEvent.SILVER_COIN),
    WrathOfFortuneModel(Res.drawable.arfa, SkillsBarEvent.ARFA),
    WrathOfFortuneModel(Res.drawable.coin_iron, SkillsBarEvent.SILVER_COIN),
    WrathOfFortuneModel(Res.drawable.helm, SkillsBarEvent.HELM),
    WrathOfFortuneModel(Res.drawable.earthqueq, SkillsBarEvent.EARTHQUAKE),
    WrathOfFortuneModel(Res.drawable.arfa, SkillsBarEvent.ARFA),
    WrathOfFortuneModel(Res.drawable.windstorm, SkillsBarEvent.WINDSTORM),
    WrathOfFortuneModel(Res.drawable.helm, SkillsBarEvent.HELM),
    WrathOfFortuneModel(Res.drawable.coin, SkillsBarEvent.GOLD_COIN),
    WrathOfFortuneModel(Res.drawable.helm, SkillsBarEvent.HELM),
)
private val largeFortuneList = List(300) { index ->
    val original = fortuneList[index % fortuneList.size]
    WrathOfFortuneModel(
        image = original.image,
        skillName = original.skillName
    )
}

data class WrathOfFortuneModel(
    val image: DrawableResource,
    val skillName: SkillsBarEvent
)

