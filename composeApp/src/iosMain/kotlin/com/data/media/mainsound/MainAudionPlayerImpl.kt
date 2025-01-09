package com.data.media.mainsound

import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import zeustheolympiandefender.composeapp.generated.resources.Res

class MainAudionPlayerImpl(
) : MainAudionPlayer {
    @OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
    private var mainMusic: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/main_song.mp3"))!!,
        error = null
    )

    init {
        avPlayerSetting()
    }

    private fun avPlayerSetting() {
        mainMusic.prepareToPlay()
        mainMusic.setNumberOfLoops(-1)
        mainMusic.volume = 0.1f
    }

    override fun playSound() {
        if (!mainMusic.isPlaying()) {
            mainMusic.play()
        }
    }

    override fun pauseSound() {
        mainMusic.pause()
    }
}