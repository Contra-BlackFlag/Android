package com.example.browser.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.userDataStore by preferencesDataStore(name = "user_settings")

class UserPreferences(private val context: Context) {

    companion object {
        val HAPTICS = booleanPreferencesKey("haptics")
        val SEARCH_ENGINE = stringPreferencesKey("search_engine")
    }

    // Save Dark Mode setting
    suspend fun saveHaptics(enabled: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[HAPTICS] = enabled
        }
    }

    // Read Dark Mode setting
    val hapticsFlow: Flow<Boolean> = context.userDataStore.data.map { prefs ->
        prefs[HAPTICS] ?: false
    }

    // Save Default Homepage
    suspend fun saveSearchEngine(engine : String) {
        context.userDataStore.edit { prefs ->
            prefs[SEARCH_ENGINE] = engine
        }
    }

    // Read Default Homepage
    val searchFlow: Flow<String> = context.userDataStore.data.map { prefs ->
        prefs[SEARCH_ENGINE] ?: "google"
    }
}
