package antuere.domain.usecases

import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository

class SaveSettingsUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, Settings> {

    override suspend fun invoke(param: Settings) {
        settingsRepository.saveSettings(param)
    }
}