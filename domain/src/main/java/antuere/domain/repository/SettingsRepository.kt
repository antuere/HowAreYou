package antuere.domain.repository

import antuere.domain.dto.Settings
import antuere.domain.dto.helplines.SupportedCountry
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getAllSettings(): Flow<Settings>

    suspend fun getWorriedDialogSetting(): Flow<Boolean>

    suspend fun getPinSetting(): Flow<Boolean>

    suspend fun getBiomAuthSetting(): Flow<Boolean>

    suspend fun getSelectedCountryId(): Int

    suspend fun saveSettings(settings: Settings)

    suspend fun saveWorriedDialogSetting(isEnable: Boolean)

    suspend fun savePinSetting(isEnable: Boolean)

    suspend fun saveBiomAuthSetting(isEnable: Boolean)

    suspend fun saveSelectedCountryId(supportedCountry: SupportedCountry)

    suspend fun saveUserNickname(nickname: String)

    suspend fun getUserNickname(): Flow<String>

    suspend fun resetUserNickname()

    suspend fun savePinCode(pinCode: String)

    suspend fun getPinCode(): Flow<String>

    suspend fun resetPinCode()

    suspend fun resetAllSettings()

}