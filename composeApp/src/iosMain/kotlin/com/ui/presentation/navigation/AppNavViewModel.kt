package com.ui.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.datastore.AppOptionPreferences
import com.data.media.mainsound.MainAudionPlayer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AppNavViewModel(
    private val appOptionPreferences: AppOptionPreferences,
    private val mainAudionPlayer: MainAudionPlayer,
    private val dispatcherIo: CoroutineDispatcher
) : ViewModel() {

    fun playSound() {
        viewModelScope.launch(dispatcherIo) {
            appOptionPreferences.isMusicEnabled().collect { isEnabled ->
                if (isEnabled) {
                    mainAudionPlayer.playSound()
                } else {
                    mainAudionPlayer.pauseSound()
                }
            }
        }
    }

    fun pauseSong() {
        viewModelScope.launch(dispatcherIo) {
            appOptionPreferences.isMusicEnabled().collect { isEnabled ->
                if (isEnabled) {
                    mainAudionPlayer.pauseSound()
                }
            }
        }
    }
}