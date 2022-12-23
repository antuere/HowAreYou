package antuere.data.preferences_data_store.toggle_btn_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import antuere.domain.dto.ToggleBtnState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToggleBtnDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val toggleBtnDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val CHECKED_BUTTON_HISTORY_KEY = stringPreferencesKey("checked button in history")
    }

    val toggleButtonState: Flow<ToggleBtnState>
        get() = toggleBtnDataStore.data.map { preferences ->
            when (preferences[CHECKED_BUTTON_HISTORY_KEY] ?: "ALL_DAYS") {
                ToggleBtnState.ALL_DAYS.name -> ToggleBtnState.ALL_DAYS
                ToggleBtnState.CURRENT_MONTH.name -> ToggleBtnState.CURRENT_MONTH
                ToggleBtnState.LAST_WEEK.name -> ToggleBtnState.LAST_WEEK
                ToggleBtnState.FILTER_SELECTED.name -> ToggleBtnState.FILTER_SELECTED
                else -> ToggleBtnState.ALL_DAYS
            }
        }

    suspend fun saveToggleButtonState(state: String) {
        toggleBtnDataStore.edit { preferences ->
            preferences[CHECKED_BUTTON_HISTORY_KEY] = state
        }
    }

    suspend fun resetToggleButtonState() {
        toggleBtnDataStore.edit { preferences ->
            preferences.remove(CHECKED_BUTTON_HISTORY_KEY)
        }
    }

}