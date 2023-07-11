package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import antuere.domain.dto.ImageSource
import antuere.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ImageSourceConfiguration(
    dataStore: DataStore<Preferences>
) : Configuration<ImageSource>(dataStore) {

    private val preferencesKey = stringPreferencesKey(Constants.CHECKED_IMAGE_SOURCE_KEY)

    override val flow: Flow<ImageSource> = dataStore.data.map { preferences ->
        when (preferences[preferencesKey] ?: ImageSource.Cataas.name) {
            ImageSource.Cataas.name -> ImageSource.Cataas
            ImageSource.LoremFlickr.name -> ImageSource.LoremFlickr
            ImageSource.TheCatApi.name -> ImageSource.TheCatApi
            else -> ImageSource.Cataas
        }
    }

    override suspend fun load(): ImageSource {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit {
            it.remove(preferencesKey)
        }
    }

    override suspend fun set(value: ImageSource) {
        dataStore.edit {
            it[preferencesKey] = value.name
        }
    }
}