package com.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface AppOptionPreferences {
    fun isSoundEnabled(): Flow<Boolean>
    suspend fun changeSoundEnabled(isEnabled: Boolean): Preferences
    fun isMusicEnabled(): Flow<Boolean>
    suspend fun changeMusicEnabled(isEnabled: Boolean): Preferences

}

internal class AppOptionPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppOptionPreferences {
    private companion object {
        private const val PREFS_TAG_KEY = "AppPreferences"
        private const val IS_DARK_MODE_ENABLED = "prefsBoolean"
        private const val IS_MUSIC_ENABLED = "musicEnabled"
    }

    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE_ENABLED")
    private val musicEnabledKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_MUSIC_ENABLED")


    override fun isSoundEnabled() = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: true
    }

    override suspend fun changeSoundEnabled(isEnabled: Boolean) = dataStore.edit { preferences ->
        preferences[darkModeKey] = isEnabled
    }

    override fun isMusicEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[musicEnabledKey] ?: false
        }
    }

    override suspend fun changeMusicEnabled(isEnabled: Boolean): Preferences {
        return dataStore.edit { preferences ->
            preferences[musicEnabledKey] = isEnabled
        }
    }

}