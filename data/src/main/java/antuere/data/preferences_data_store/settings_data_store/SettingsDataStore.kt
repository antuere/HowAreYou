package antuere.data.preferences_data_store.settings_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.configurations.BooleanConfiguration
import antuere.data.preferences_data_store.configurations.Configuration
import antuere.data.preferences_data_store.configurations.IntConfiguration
import antuere.data.preferences_data_store.configurations.SettingsConfiguration
import antuere.data.preferences_data_store.configurations.StringConfiguration
import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import antuere.domain.util.Constants

class SettingsDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val settingsDataStore: DataStore<Preferences> = context.datastore


    val isFirstLaunchConfiguration: Configuration<Boolean> =
        BooleanConfiguration(
            key = Constants.SETTINGS_FIRST_LAUNCH_KEY,
            default = true,
            dataStore = settingsDataStore
        )

    val nicknameConfiguration: Configuration<String> =
        StringConfiguration(
            key = Constants.USER_NICKNAME_KEY,
            default = Constants.USER_NOT_AUTH,
            dataStore = settingsDataStore
        )

    val pinCodeConfiguration: Configuration<String> =
        StringConfiguration(
            key = Constants.PIN_CODE_SAVED_KEY,
            default = Constants.PIN_NOT_SET,
            dataStore = settingsDataStore
        )

    val allSettingsConfiguration: Configuration<SettingsEntity> =
        SettingsConfiguration(dataStore = settingsDataStore)

    val isEnableWorriedDialogConfiguration: Configuration<Boolean> =
        BooleanConfiguration(
            key = Constants.SETTINGS_WORRIED_DIALOG_KEY,
            default = true,
            dataStore = settingsDataStore
        )

    val isEnablePinConfiguration: Configuration<Boolean> =
        BooleanConfiguration(
            key = Constants.SETTINGS_PIN_CODE_KEY,
            default = false,
            dataStore = settingsDataStore
        )

    val isEnableBiomAuthConfiguration: Configuration<Boolean> =
        BooleanConfiguration(
            key = Constants.SETTINGS_BIOMETRIC_KEY,
            default = false,
            dataStore = settingsDataStore
        )

    val countryIdConfiguration: Configuration<Int> =
        IntConfiguration(
            key = Constants.SELECTED_COUNTRY_ID,
            default = 1,
            dataStore = settingsDataStore
        )
}