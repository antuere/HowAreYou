package com.example.zeroapp.presentation.history

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ToggleButtonDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val myDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val CHECKED_BUTTON_HISTORY_KEY = intPreferencesKey("checked button in history")
        const val CHECKED_ALL_DAYS = 1
        const val CHECKED_CURRENT_MONTH = 2
        const val CHECKED_LAST_WEEK = 3
    }

    suspend fun saveToggleButtonState(state: Int) {
        myDataStore.edit { preferences ->
            preferences[CHECKED_BUTTON_HISTORY_KEY] = state
        }
    }

    suspend fun getToggleButtonState(): ToggleButtonState {
        val stateValue: Int = myDataStore.data.map {
            it[CHECKED_BUTTON_HISTORY_KEY] ?: 1
        }.first()

        return when (stateValue) {
            CHECKED_ALL_DAYS -> ToggleButtonState.AllDays
            CHECKED_CURRENT_MONTH -> ToggleButtonState.CurrentMonth
            CHECKED_LAST_WEEK -> ToggleButtonState.LastWeek
            else -> ToggleButtonState.AllDays
        }

    }

}