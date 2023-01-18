package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import antuere.domain.dto.ToggleBtnState
import antuere.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ToggleBtnConfiguration(
    dataStore: DataStore<Preferences>
) : Configuration<ToggleBtnState>(dataStore) {

    private val preferencesKey = stringPreferencesKey(Constants.CHECKED_BUTTON_HISTORY_KEY)

    override val flow: Flow<ToggleBtnState> = dataStore.data.map { preferences ->
        when (preferences[preferencesKey] ?: ToggleBtnState.ALL_DAYS.name) {
            ToggleBtnState.ALL_DAYS.name -> ToggleBtnState.ALL_DAYS
            ToggleBtnState.CURRENT_MONTH.name -> ToggleBtnState.CURRENT_MONTH
            ToggleBtnState.LAST_WEEK.name -> ToggleBtnState.LAST_WEEK
            else -> ToggleBtnState.ALL_DAYS
        }
    }

    override suspend fun load(): ToggleBtnState {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit {
            it.remove(preferencesKey)
        }
    }

    override suspend fun set(value: ToggleBtnState) {
        dataStore.edit {
            it[preferencesKey] = value.name
        }
    }
}