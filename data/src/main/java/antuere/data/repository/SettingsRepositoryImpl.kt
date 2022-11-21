package antuere.data.repository

import antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
import antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val settingsEntityMapper: SettingsEntityMapper
) :
    SettingsRepository {

    override suspend fun getSettings(): Flow<Settings> = withContext(Dispatchers.IO) {
        settingsDataStore.settings.map {
            settingsEntityMapper.mapToDomainModel(it)
        }
    }

    override suspend fun saveSettings(settings: Settings) = withContext(Dispatchers.IO) {
        val settingsEntity = settingsEntityMapper.mapFromDomainModel(settings)
        settingsDataStore.saveSettings(settingsEntity)
    }

    override suspend fun saveUserNickname(nickname: String) = withContext(Dispatchers.IO) {
        settingsDataStore.saveUserNickName(nickname)
    }

    override suspend fun getUserNickname(): Flow<String> = withContext(Dispatchers.IO) {
        settingsDataStore.userNickname
    }

    override suspend fun resetUserNickname() = withContext(Dispatchers.IO) {
        settingsDataStore.resetUserNickname()
    }

    override suspend fun savePinCode(pinCode: String) = withContext(Dispatchers.IO) {
        settingsDataStore.savePinCode(pinCode)
    }

    override suspend fun resetPinCode() = withContext(Dispatchers.IO) {
        settingsDataStore.resetPinCode()
    }

    override suspend fun resetAllSettings() = withContext(Dispatchers.IO) {
        settingsDataStore.resetAllSettings()
    }

    override suspend fun getPinCode(): Flow<String> = withContext(Dispatchers.IO) {
        settingsDataStore.pinCode
    }
}