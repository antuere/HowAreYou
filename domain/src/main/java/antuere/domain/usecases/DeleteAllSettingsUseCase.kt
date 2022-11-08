package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository

class DeleteAllSettingsUseCase(private val settingsRepository: SettingsRepository) :
    UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        settingsRepository.resetAllSettings()
    }
}
