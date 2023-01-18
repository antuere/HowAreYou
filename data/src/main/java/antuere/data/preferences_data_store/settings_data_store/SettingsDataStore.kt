package antuere.data.preferences_data_store.settings_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.configurations.*
import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import antuere.domain.util.Constants

class SettingsDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val settingsDataStore: DataStore<Preferences> = context.datastore

    val nicknameConfiguration: Configuration<String> =
        StringConfiguration(Constants.USER_NICKNAME_KEY, Constants.USER_NOT_AUTH, settingsDataStore)

    val pinCodeConfiguration: Configuration<String> =
        StringConfiguration(Constants.PIN_CODE_SAVED_KEY, "NOT_SET", settingsDataStore)

    val settingsConfiguration: Configuration<SettingsEntity> =
        SettingsConfiguration(settingsDataStore)

    val countryIdConfiguration: Configuration<Int> =
        IntConfiguration(Constants.SELECTED_COUNTRY_ID, 1, settingsDataStore)
}