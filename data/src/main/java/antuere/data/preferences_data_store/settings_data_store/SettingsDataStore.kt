package antuere.data.preferences_data_store.settings_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val settingsDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val SETTINGS_BIOMETRIC_KEY = booleanPreferencesKey("biometric_auth")
    }

    val settings: Flow<SettingsEntity>
        get() = settingsDataStore.data.map { preferences ->
            val savedBiometricVal = preferences[SETTINGS_BIOMETRIC_KEY] ?: false
            SettingsEntity(savedBiometricVal)
        }

    suspend fun saveSettings(settings: SettingsEntity) {
        settingsDataStore.edit { preferences ->
            preferences[SETTINGS_BIOMETRIC_KEY] = settings.isBiometricEnabled
        }
    }
}