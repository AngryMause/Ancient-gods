package com.ui.presentation.option

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.datastore.AppOptionPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class OptionViewModel(
    private val appOptionPreferences: AppOptionPreferences,
    private val dispatcherIo: CoroutineDispatcher
) : ViewModel() {

    val isSoundEnabled: Flow<Boolean> = appOptionPreferences.isSoundEnabled().flowOn(dispatcherIo)
    val isMusicEnabled: Flow<Boolean> = appOptionPreferences.isMusicEnabled().flowOn(dispatcherIo)

    fun changeSoundEnabled(isEnabled: Boolean) {
        viewModelScope.launch(dispatcherIo) { appOptionPreferences.changeSoundEnabled(isEnabled) }
    }

    fun changeMusicEnabled(isEnabled: Boolean) {
        viewModelScope.launch(dispatcherIo) { appOptionPreferences.changeMusicEnabled(isEnabled) }
    }

}