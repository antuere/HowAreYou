package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StringConfiguration(
    key: String,
    private val default: String,
    dataStore: DataStore<Preferences>
) : Configuration<String>(dataStore) {

    private val preferenceKey = stringPreferencesKey(key)
    override val flow: Flow<String> = dataStore.data.map {
        it[preferenceKey] ?: default
    }

    override suspend fun load(): String {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(preferenceKey)
        }
    }

    override suspend fun set(value: String) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }
}