package antuere.domain.usecases.user_settings

import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault

class SaveSettingsUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, Settings> {

    override suspend fun invoke(param: Settings) {
        settingsRepository.saveSettings(param)
    }
}