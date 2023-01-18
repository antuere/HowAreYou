package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import antuere.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsConfiguration(
    dataStore: DataStore<Preferences>
) : Configuration<SettingsEntity>(dataStore) {

    private val pinPreferencesKey = booleanPreferencesKey(Constants.SETTINGS_PIN_CODE_KEY)

    private val biometricPreferencesKey =
        booleanPreferencesKey(Constants.SETTINGS_BIOMETRIC_KEY)

    private val worriedDialogPreferencesKey =
        booleanPreferencesKey(Constants.SETTINGS_WORRIED_DIALOG_KEY)

    override val flow: Flow<SettingsEntity> = dataStore.data.map { preferences ->
        val isEnableWorriedDialog = preferences[worriedDialogPreferencesKey] ?: true
        val isEnablePinCode = preferences[pinPreferencesKey] ?: false
        val isEnableBiometricAuth = preferences[biometricPreferencesKey] ?: false

        SettingsEntity(
            isBiometricEnabled = isEnableBiometricAuth,
            isPinCodeEnabled = isEnablePinCode,
            isShowWorriedDialog = isEnableWorriedDialog
        )
    }

    override suspend fun load(): SettingsEntity {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(pinPreferencesKey)
            preferences.remove(biometricPreferencesKey)
            preferences.remove(worriedDialogPreferencesKey)
        }
    }

    override suspend fun set(value: SettingsEntity) {
        dataStore.edit { preferences ->
            preferences[biometricPreferencesKey] = value.isBiometricEnabled
            preferences[pinPreferencesKey] = value.isPinCodeEnabled
            preferences[worriedDialogPreferencesKey] = value.isShowWorriedDialog
        }
    }
}