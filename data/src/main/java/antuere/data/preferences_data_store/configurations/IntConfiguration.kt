package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class IntConfiguration(
    key: String,
    private val default: Int,
    dataStore: DataStore<Preferences>
) : Configuration<Int>(dataStore) {

    private val preferenceKey = intPreferencesKey(key)

    override val flow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[preferenceKey] ?: default
    }

    override suspend fun load(): Int {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(preferenceKey)
        }
    }

    override suspend fun set(value: Int) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }
}