package com.data.media


import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import zeustheolympiandefender.composeapp.generated.resources.Res


@OptIn(ExperimentalResourceApi::class)
class GameAudioPlayerImpl : GameAudioPlayer {


    @OptIn(ExperimentalForeignApi::class)
    private var winRoundPlayer: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/win_round.mp3"))!!,
        error = null
    )


    init {
        setVolume()
    }

    private fun setVolume() {
        winRoundPlayer.volume = 0.1f
    }

    override fun playWinSound() {
        if (!winRoundPlayer.isPlaying()) {
            winRoundPlayer.play()
        }
    }



}