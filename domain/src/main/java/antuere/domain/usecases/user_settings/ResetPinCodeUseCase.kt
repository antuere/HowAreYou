package antuere.domain.usecases.user_settings

import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault

class ResetPinCodeUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        settingsRepository.resetPinCode()
    }
}