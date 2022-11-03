package antuere.data.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.entities.ToggleBtnStateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToggleBtnDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val myDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val CHECKED_BUTTON_HISTORY_KEY = intPreferencesKey("checked button in history")
        const val CHECKED_ALL_DAYS = 1
        const val CHECKED_CURRENT_MONTH = 2
        const val CHECKED_LAST_WEEK = 3
    }

    val toggleButtonState: Flow<ToggleBtnStateEntity>
        get() = myDataStore.data.map { preferences ->
            val intValue = preferences[CHECKED_BUTTON_HISTORY_KEY] ?: 1
            when (intValue) {
                CHECKED_ALL_DAYS -> ToggleBtnStateEntity.AllDays(intValue)
                CHECKED_CURRENT_MONTH -> ToggleBtnStateEntity.CurrentMonth(intValue)
                CHECKED_LAST_WEEK -> ToggleBtnStateEntity.LastWeek(intValue)
                else -> ToggleBtnStateEntity.AllDays(CHECKED_ALL_DAYS)
            }
        }


    suspend fun saveToggleButtonState(state: Int) {
        myDataStore.edit { preferences ->
            preferences[CHECKED_BUTTON_HISTORY_KEY] = state
        }
    }

}