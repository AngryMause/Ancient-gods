package com.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface AppOptionPreferences {
    fun isSoundEnabled(): Flow<Boolean>
    suspend fun changeSoundEnabled(isEnabled: Boolean): Preferences

}

internal class AppOptionPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppOptionPreferences {
    private companion object {
        private const val PREFS_TAG_KEY = "AppPreferences"
        private const val IS_DARK_MODE_ENABLED = "prefsBoolean"
    }

    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE_ENABLED")

    override fun isSoundEnabled() = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: true
    }

    override suspend fun changeSoundEnabled(isEnabled: Boolean) = dataStore.edit { preferences ->
        preferences[darkModeKey] = isEnabled
    }

}