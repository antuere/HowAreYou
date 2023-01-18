package antuere.data.repository

import antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
import antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
import antuere.domain.dto.Settings
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val settingsEntityMapper: SettingsEntityMapper
) : SettingsRepository {

    override suspend fun getSettings(): Flow<Settings> {
        return settingsDataStore.settingsConfiguration.flow.map {
            settingsEntityMapper.mapToDomainModel(it)
        }
    }

    override suspend fun saveSettings(settings: Settings) {
        val settingsEntity = settingsEntityMapper.mapFromDomainModel(settings)
        settingsDataStore.settingsConfiguration.set(settingsEntity)
    }

    override suspend fun saveSelectedCountryId(supportedCountry: SupportedCountry) {
        settingsDataStore.countryIdConfiguration.set(supportedCountry.id)
    }

    override suspend fun getSelectedCountryId(): Int {
        return settingsDataStore.countryIdConfiguration.load()
    }

    override suspend fun saveUserNickname(nickname: String) {
        settingsDataStore.nicknameConfiguration.set(nickname)
    }

    override suspend fun getUserNickname(): Flow<String> {
        return settingsDataStore.nicknameConfiguration.flow
    }

    override suspend fun resetUserNickname() {
        settingsDataStore.nicknameConfiguration.reset()
    }

    override suspend fun savePinCode(pinCode: String) {
        settingsDataStore.pinCodeConfiguration.set(pinCode)
    }

    override suspend fun getPinCode(): Flow<String> {
        return settingsDataStore.pinCodeConfiguration.flow
    }

    override suspend fun resetPinCode() {
        settingsDataStore.pinCodeConfiguration.reset()
    }

    override suspend fun resetAllSettings() {
        settingsDataStore.pinCodeConfiguration.reset()
        settingsDataStore.nicknameConfiguration.reset()
        settingsDataStore.settingsConfiguration.reset()
        settingsDataStore.countryIdConfiguration.reset()
    }
}