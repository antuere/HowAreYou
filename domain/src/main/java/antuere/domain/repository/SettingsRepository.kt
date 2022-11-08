package antuere.domain.repository

import antuere.domain.dto.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getSettings(): Flow<Settings>

    suspend fun saveSettings(settings: Settings)

    suspend fun savePinCode(pinCode: String)

    suspend fun resetPinCode()

    suspend fun resetAllSettings()

    suspend fun getPinCode(): Flow<String>

}