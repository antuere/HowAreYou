package antuere.data.repository

import antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
import antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val settingsEntityMapper: SettingsEntityMapper
) :
    SettingsRepository {

    override suspend fun getSettings(): Flow<Settings> {
        return settingsDataStore.settings.map {
            settingsEntityMapper.mapToDomainModel(it)
        }
    }

    override suspend fun saveSettings(settings: Settings) {
        Timber.i("pin code error: settings in rep = $settings")
        val settingsEntity = settingsEntityMapper.mapFromDomainModel(settings)
        settingsDataStore.saveSettings(settingsEntity)
    }
}