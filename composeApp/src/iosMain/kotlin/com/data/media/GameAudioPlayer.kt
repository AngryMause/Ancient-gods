package com.data.media

interface GameAudioPlayer {
    fun playWinSound(isEnabled: Boolean)
    fun thunderStrike(isEnabled: Boolean)
    fun windStormSkill(isEnabled: Boolean)
    fun earthquakeSkill(isEnabled: Boolean)
    fun coinsSound(isEnabled: Boolean)
}
