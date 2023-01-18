package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BooleanConfiguration(
    key: String,
    private val default: Boolean,
    dataStore: DataStore<Preferences>
) : Configuration<Boolean>(dataStore) {

    private val preferenceKey = booleanPreferencesKey(key)

    override val flow: Flow<Boolean> = dataStore.data.map {
        it[preferenceKey] ?: default
    }

    override suspend fun load(): Boolean {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(preferenceKey)
        }
    }

    override suspend fun set(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

}