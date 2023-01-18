package antuere.data.preferences_data_store.toggle_btn_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.configurations.Configuration
import antuere.data.preferences_data_store.configurations.ToggleBtnConfiguration
import antuere.domain.dto.ToggleBtnState

class ToggleBtnDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val toggleBtnDataStore: DataStore<Preferences> = context.datastore

    val toggleBtnConfiguration: Configuration<ToggleBtnState> =
        ToggleBtnConfiguration(toggleBtnDataStore)
}