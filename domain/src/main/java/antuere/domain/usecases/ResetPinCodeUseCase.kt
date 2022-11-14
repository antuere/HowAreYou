package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository

class ResetPinCodeUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        settingsRepository.resetPinCode()
    }
}