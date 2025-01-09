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

    @OptIn(ExperimentalForeignApi::class)
    private var thunderStrike: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/thunderStrike.mp3"))!!,
        error = null
    )

    @OptIn(ExperimentalForeignApi::class)
    private var windStorm: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/windstorm_skill.mp3"))!!,
        error = null
    )

    @OptIn(ExperimentalForeignApi::class)
    private var earthquake: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/earthquake.mp3"))!!,
        error = null
    )

    @OptIn(ExperimentalForeignApi::class)
    private var coinsDrop: AVAudioPlayer = AVAudioPlayer(
        NSURL.URLWithString(URLString = Res.getUri("files/coins_sound.mp3"))!!,
        error = null
    )

    init {
        setVolume()
    }

    private fun setVolume() {
        winRoundPlayer.volume = 0.1f
        thunderStrike.volume = 0.1f
        windStorm.volume = 0.1f
        earthquake.volume = 0.1f
        coinsDrop.volume = 0.1f
    }

    override fun playWinSound(isEnabled: Boolean) {
        if (!isEnabled) return
        if (!winRoundPlayer.isPlaying()) {
            winRoundPlayer.play()
        }

    }

    override fun windStormSkill(isEnabled: Boolean) {
        if (!isEnabled) return
        if (!windStorm.isPlaying()) {
            windStorm.play()
        }
    }

    override fun earthquakeSkill(isEnabled: Boolean) {
        if (!isEnabled) return
        if (!earthquake.isPlaying()) {
            earthquake.play()
        }
    }

    override fun coinsSound(isEnabled: Boolean) {
        if (!isEnabled) return
        if (!coinsDrop.isPlaying()) {
            coinsDrop.play()
        }
    }

    override fun thunderStrike(isEnabled: Boolean) {
        if (!isEnabled) return
        if (!thunderStrike.isPlaying()) {
            thunderStrike.play()
        }
    }


}