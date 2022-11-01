package com.example.zeroapp.presentation.history

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class ToggleButtonDataStore(context: Context, name: String) {

        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
        private val myDataStore: DataStore<Preferences> = context.datastore

        companion object {
            val CHECKED_BUTTON_HISTORY_KEY = stringPreferencesKey("checked button in history")
            val CHECKED_ALL_DAYS_KEY = intPreferencesKey("all days")
            val CHECKED_CURRENT_MONTH_KEY = intPreferencesKey("month")
            val CHECKED_LAST_WEEK_KEY = intPreferencesKey("week")
        }


}