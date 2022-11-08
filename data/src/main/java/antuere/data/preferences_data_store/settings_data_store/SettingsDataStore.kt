package antuere.data.preferences_data_store.settings_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val settingsDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val SETTINGS_BIOMETRIC_KEY = booleanPreferencesKey("biometric_auth")
        val SETTINGS_PIN_CODE_KEY = booleanPreferencesKey("pin_code_auth")
        val SETTINGS_PIN_CODE_SAVED_KEY = stringPreferencesKey("password_pin_code")
    }

    val settings: Flow<SettingsEntity>
        get() = settingsDataStore.data.map { preferences ->
            val savedBiometricVal = preferences[SETTINGS_BIOMETRIC_KEY] ?: false
            val savedPinCodeVal = preferences[SETTINGS_PIN_CODE_KEY] ?: false
            SettingsEntity(savedBiometricVal, savedPinCodeVal)
        }

    val pinCode: Flow<String>
        get() = settingsDataStore.data.map { preferences ->
            preferences[SETTINGS_PIN_CODE_SAVED_KEY] ?: "Password not set"
        }

    suspend fun saveSettings(settings: SettingsEntity) {
        settingsDataStore.edit { preferences ->
            preferences[SETTINGS_BIOMETRIC_KEY] = settings.isBiometricEnabled
            preferences[SETTINGS_PIN_CODE_KEY] = settings.isPinCodeEnabled
        }
    }

    suspend fun savePinCode(pinCode: String) {
        settingsDataStore.edit { preferences ->
            preferences[SETTINGS_PIN_CODE_SAVED_KEY] = pinCode
        }
    }

    suspend fun resetPinCode() {
        settingsDataStore.edit { preferences ->
            preferences.remove(SETTINGS_PIN_CODE_SAVED_KEY)
        }
    }

    suspend fun resetAllSettings() {
        settingsDataStore.edit { preferences ->
            preferences.remove(SETTINGS_PIN_CODE_SAVED_KEY)
            preferences.remove(SETTINGS_BIOMETRIC_KEY)
            preferences.remove(SETTINGS_PIN_CODE_KEY)
        }
    }
}