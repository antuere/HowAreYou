package antuere.domain.repository

import antuere.domain.dto.ImageSource
import kotlinx.coroutines.flow.Flow

interface ImageSourceRepository {

    suspend fun getCurrentImageSource(): Flow<ImageSource>

    suspend fun saveImageSource(imageSource: ImageSource)

    suspend fun resetImageSource()

}