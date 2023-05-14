package antuere.data.preferences_data_store.image_source_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.configurations.Configuration
import antuere.data.preferences_data_store.configurations.ImageSourceConfiguration
import antuere.domain.dto.ImageSource

class ImageSourceDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val imageSourceDataStore: DataStore<Preferences> = context.datastore

    val imageSourceConfiguration: Configuration<ImageSource> =
        ImageSourceConfiguration(imageSourceDataStore)
}