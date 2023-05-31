package antuere.data.repository

import antuere.data.preferences_data_store.image_source_data_store.ImageSourceDataStore
import antuere.domain.dto.ImageSource
import antuere.domain.repository.ImageSourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageSourceRepositoryImpl @Inject constructor(
    private val imageSourceDataStore: ImageSourceDataStore
) : ImageSourceRepository {
    override suspend fun getCurrentImageSource(): Flow<ImageSource> {
        return imageSourceDataStore.imageSourceConfiguration.flow
    }

    override suspend fun saveImageSource(imageSource: ImageSource) {
        imageSourceDataStore.imageSourceConfiguration.set(imageSource)
    }

    override suspend fun resetImageSource() {
        imageSourceDataStore.imageSourceConfiguration.reset()
    }
}