package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import antuere.domain.dto.AppTheme
import antuere.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppThemeConfiguration(
    dataStore: DataStore<Preferences>,
) : Configuration<AppTheme>(dataStore) {

    private val preferencesKey = stringPreferencesKey(Constants.SETTINGS_APP_THEME_KEY)

    override val flow: Flow<AppTheme> = dataStore.data.map { preferences ->
        when (preferences[preferencesKey] ?: AppTheme.DEFAULT) {
            AppTheme.DEFAULT.name -> AppTheme.DEFAULT
            AppTheme.GREEN.name -> AppTheme.GREEN
            AppTheme.YELLOW.name -> AppTheme.YELLOW
            AppTheme.RED.name -> AppTheme.RED
            else -> AppTheme.DEFAULT
        }
    }

    override suspend fun load(): AppTheme {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit {
            it.remove(preferencesKey)
        }
    }

    override suspend fun set(value: AppTheme) {
        dataStore.edit {
            it[preferencesKey] = value.name
        }
    }
}