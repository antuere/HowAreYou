package antuere.domain.usecases.user_settings

import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault

class SavePinCodeUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, String> {

    override suspend fun invoke(param: String) {
        settingsRepository.savePinCode(param)
    }
}
