package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository

class SavePinCodeUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, String> {

    override suspend fun invoke(param: String) {
        settingsRepository.savePinCode(param)
    }
}
