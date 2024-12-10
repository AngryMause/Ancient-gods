package com.ui.presentation.option

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.datastore.AppOptionPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class OptionViewModel(
    private val appOptionPreferences: AppOptionPreferences
) : ViewModel() {

    val isSoundEnabled: Flow<Boolean> = appOptionPreferences.isSoundEnabled().flowOn(Dispatchers.IO)

    fun changeSoundEnabled(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) { appOptionPreferences.changeSoundEnabled(isEnabled) }
    }
}